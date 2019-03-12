#!/bin/bash

JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
JBOSS_MODE=${1:-"standalone"}
JBOSS_CONFIG=${2:-"$JBOSS_MODE.xml"}

function wait_for_server() {
  until `$JBOSS_CLI -c "ls /deployment" &> /dev/null`; do
    sleep 1
  done
}

echo "=> Starting WildFly server"
$JBOSS_HOME/bin/$JBOSS_MODE.sh -c $JBOSS_CONFIG > /dev/null &

echo "=> Waiting for the server to boot"
wait_for_server

echo "=> Server started...."

#reduce security for now for active mq
`$JBOSS_CLI -c "/subsystem=messaging-activemq/server=default:write-attribute(name=security-enabled,value=false)"`

#add mysql database connection
`$JBOSS_CLI -c "deploy mysql-connector-java-8.0.15.jar" > out.txt`
`$JBOSS_CLI -c "data-source add --name=userSource --jndi-name=java:/jdbc/users-database --driver-name=mysql-connector-java-8.0.15.jar --driver-class=com.mysql.jdbc.Driver --connection-url=jdbc:mysql://db:3306/users --user-name=users_user --password=users_secret" > out.txt`
cat out.txt


echo "=> Shutting down WildFly"
if [ "$JBOSS_MODE" = "standalone" ]; then
  $JBOSS_CLI -c ":shutdown"
else
  $JBOSS_CLI -c "/host=*:shutdown"
fi
