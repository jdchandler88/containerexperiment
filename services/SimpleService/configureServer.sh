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
$JBOSS_HOME/bin/$JBOSS_MODE.sh -c $JBOSS_CONFIG  &

echo "=> Waiting for the server to boot"
wait_for_server

echo "=> Server started..."
`$JBOSS_CLI -c "/subsystem=messaging-activemq/server=default/jms-topic=EventTopic:add(entries=[jms/topic/EventTopic])"`
`$JBOSS_CLI -c "/subsystem=messaging-activemq/jms-bridge=jmsbridge:add(quality-of-service=AT_MOST_ONCE, failure-retry-interval=500, max-retries=1, max-batch-size=10, max-batch-time=100, source-connection-factory=ConnectionFactory, source-destination=jms/topic/EventTopic, target-connection-factory=jms/RemoteConnectionFactory, target-destination=jms/topic/EventTopic, target-context={javax.naming.factory.initial=org.jboss.naming.remote.client.InitialContextFactory, java.naming.provider=http-remoting://jms:8080})"`


echo "=> Adding configuration module"
CONFIG_DIR=$JBOSS_HOME/modules/com/mycompany/configuration/main
mkdir $CONFIG_DIR
mv module.xml $CONFIG_DIR

echo "=> Shutting down WildFly"
if [ "$JBOSS_MODE" = "standalone" ]; then
  $JBOSS_CLI -c ":shutdown"
else
  $JBOSS_CLI -c "/host=*:shutdown"
fi
