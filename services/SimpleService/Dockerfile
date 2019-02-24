# dockerfile to build image for JBoss EAP 7.1

#start from eap71-openshift
FROM jboss/wildfly

# file author / maintainer
MAINTAINER "FirstName LastName" "emailaddress@gmail.com"

#copy service
ADD build/libs/SimpleService.war .

# start server so that we can configure it
ADD configureServer.sh .

# User root to modify war owners
USER root
RUN chmod +x ./configureServer.sh 
USER jboss
RUN ./configureServer.sh standalone standalone-full.xml

# Important, use jboss user to run image
USER jboss
