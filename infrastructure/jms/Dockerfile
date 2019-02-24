# Builds a JBOSS JMS provider. This image is based on the latest jboss/wildfly container

#start from eap71-openshift
FROM jboss/wildfly

# file author / maintainer
MAINTAINER "James Chandler" "jdchandler88@gmail.com"

#move configuration script, run it, and clean up
ADD configureJmsProvider.sh .
USER root
RUN chmod +x configureJmsProvider.sh
USER jboss
RUN ./configureJmsProvider.sh standalone standalone-full.xml
RUN rm ./configureJmsProvider.sh

#launch jboss, tell it to use standalone-full configuration, tell it to bind to external ip
ENTRYPOINT $JBOSS_HOME/bin/standalone.sh -c standalone-full.xml -b $HOSTNAME
