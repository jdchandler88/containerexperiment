# dockerfile to build image for JBoss EAP 7.1

#start from eap71-openshift
FROM jboss/wildfly

# file author / maintainer
MAINTAINER "FirstName LastName" "emailaddress@gmail.com"

#copy service
ADD build/libs/SimpleService.war .
#deploy service
RUN mv SimpleService.war $JBOSS_HOME/standalone/deployments

## copy configuration to appropriate space
#ARG configdir=$JBOSS_HOME/modules/com/mycompany/configuration/main
#RUN mkdir -p $configdir
#ADD module.xml $configdir
#ADD config/$DEV_ENV/app.properties $configdir

ENTRYPOINT $JBOSS_HOME/bin/standalone.sh -c standalone-full.xml -b $HOSTNAME -bmanagement $HOSTNAME
