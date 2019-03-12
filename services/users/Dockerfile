#dockerfile that builds the software and then deploys it to jboss

#build the software
#FROM gradle:5.2.1-jdk8 as builder
#USER root
#RUN mkdir /app
#ADD . /app
#RUN chown -R gradle /app 
#USER gradle
#WORKDIR /app
#RUN gradle clean build --stacktrace



#start from eap71-openshift
FROM jboss/wildfly

# file author / maintainer
MAINTAINER "FirstName LastName" "emailaddress@gmail.com"

#configure server to use JMS bridge TODO: pass in parameters (remote host, remote topic, etc.)
COPY configureServer.sh .
USER root
RUN chmod +x configureServer.sh
USER jboss
RUN ./configureServer.sh standalone standalone-full.xml

#copy service
#COPY --from=builder /app/build/libs/SimpleService.war $JBOSS_HOME/standalone/deployments
COPY build/libs/SimpleService.war $JBOSS_HOME/standalone/deployments

ENTRYPOINT $JBOSS_HOME/bin/standalone.sh -c standalone-full.xml -b $HOSTNAME -bmanagement $HOSTNAME
