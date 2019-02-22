# dockerfile to build image for JBoss EAP 7.1

#start from eap71-openshift
FROM jboss/wildfly

# file author / maintainer
MAINTAINER "FirstName LastName" "emailaddress@gmail.com"

#copy service
ADD build/libs/SimpleService.war .

# start server so that we can configure it
ADD startJboss.sh .

# User root to modify war owners
USER root
RUN chmod +x ./startJboss.sh
USER jboss
RUN ./startJboss.sh

# Important, use jboss user to run image
USER jboss
