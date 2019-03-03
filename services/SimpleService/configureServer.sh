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
#reduce security for now
`$JBOSS_CLI -c "/subsystem=messaging-activemq/server=default:write-attribute(name=security-enabled,value=false)"`
#add local topic to which messages are published. should reach remote server
`$JBOSS_CLI -c "/subsystem=messaging-activemq/server=default/jms-topic=EventTopic:add(entries=[java:/jms/topic/EventTopic])"`
#add bridge that forwards messsages from local topic to remote topic
`$JBOSS_CLI -c "/subsystem=messaging-activemq/jms-bridge=jmsbridge:add(quality-of-service=AT_MOST_ONCE, failure-retry-interval=500, max-retries=1, max-batch-size=10, max-batch-time=100, source-connection-factory=ConnectionFactory, source-destination=jms/topic/EventTopic, target-connection-factory=jms/RemoteConnectionFactory, target-destination=jms/topic/EventTopic, target-context={javax.naming.factory.initial=org.jboss.naming.remote.client.InitialContextFactory, java.naming.provider=http-remoting://jms:8080})"`


echo "=> Shutting down WildFly"
if [ "$JBOSS_MODE" = "standalone" ]; then
  $JBOSS_CLI -c ":shutdown"
else
  $JBOSS_CLI -c "/host=*:shutdown"
fi
