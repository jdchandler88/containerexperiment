# dockerfile to build image for JBoss EAP 7.1

#start from eap71-openshift
FROM jboss/wildfly

# file author / maintainer
MAINTAINER "FirstName LastName" "emailaddress@gmail.com"



# start server so that we can configure it
ADD configureServer.sh .

# User root to modify war owners
USER root
RUN chmod +x ./configureServer.sh 
USER jboss
RUN ./configureServer.sh standalone standalone-full.xml

#copy service
ADD build/libs/SimpleService.war .
#deploy service
RUN mv SimpleService.war $JBOSS_HOME/standalone/deployments

ENTRYPOINT $JBOSS_HOME/bin/standalone.sh -c standalone-full.xml -b $HOSTNAME -bmanagement $HOSTNAME
