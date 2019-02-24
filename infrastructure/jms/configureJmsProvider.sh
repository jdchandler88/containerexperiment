#!/bin/bash

# environment setup. store jboss cli location. set default values for mode and configuration
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
JBOSS_MODE=${1:-"standalone"}
JBOSS_CONFIG=${2:-"$JBOSS_MODE.xml"}

# waits for server to start so that jboss cli can connect
function wait_for_server() {
  until `$JBOSS_CLI -c "ls /deployment" &> /dev/null`; do
    sleep 1
  done
}

#start the server
echo "=> Starting WildFly server"
$JBOSS_HOME/bin/$JBOSS_MODE.sh -c $JBOSS_CONFIG > /dev/null  &
wait_for_server

echo "=> Server started..."

#configure JMS
echo "=> Configuring JBOSS as JMS provider"
#add a topic called jms/topic/EventTopic that is visible externally. TODO: read in file or something that is a list of topics. This should then add all topics.
`$JBOSS_CLI -c "/subsystem=messaging-activemq/server=default/jms-topic=EventTopic:add(entries=[java:jboss/exported/jms/topic/EventTopic])"`
#remove security realm. this unprotects the remoting system. TODO: configure security
`$JBOSS_CLI -c "/subsystem=remoting/http-connector=http-remoting-connector:write-attribute(name=security-realm)"`
#remove security from jms TODO: configure security
`$JBOSS_CLI -c "/subsystem=messaging-activemq/server=default:write-attribute(name=security-enabled,value=false)"`

#gracefully shut down the JBOSS server to close out the container build
echo "=> Shutting down WildFly"
if [ "$JBOSS_MODE" = "standalone" ]; then
  $JBOSS_CLI -c ":shutdown"
else
  $JBOSS_CLI -c "/host=*:shutdown"
fi
