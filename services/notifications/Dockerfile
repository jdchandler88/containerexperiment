#dockerfile that builds the software and then deploys it to jboss

#build the software
#FROM gradle:5.2.1-jdk8 as builder
#USER root
#RUN mkdir /app
#RUN chown -R gradle /app 
#USER gradle
#ADD . /app
#WORKDIR /app
#RUN gradle clean build --info

#start from eap71-openshift
FROM jboss/wildfly

# file author / maintainer
MAINTAINER "FirstName LastName" "emailaddress@gmail.com"

#copy service
#COPY --from=builder /app/build/libs/Notifications.war $JBOSS_HOME/standalone/deployments
COPY build/libs/Notifications.war $JBOSS_HOME/standalone/deployments

ENTRYPOINT $JBOSS_HOME/bin/standalone.sh -c standalone-full.xml -b $HOSTNAME -bmanagement $HOSTNAME
