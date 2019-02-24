# containerexperiment

## Overview
This is an experiment/overview of a microservice app. It will be managed by Docker-Compose. It will consist of some simple data services implemented in Java as well as some off-the-shelf infrastructure services.

## Architecture
![Architecture](https://github.com/jdchandler88/containerexperiment/blob/master/ContainerExperiment.jpg)

## Repository Structure

* root: repo root
  * docker-compose.yml: describes entire microservice app
  * services: contains data service source and Dockerfile
    * folder: contains Dockerfile and Gradle War project

## Notes
* Wildfly Docker
  * https://github.com/jboss-dockerfiles/wildfly/blob/master/Dockerfile - Can use "docker run ..." and, as the last argument, pass executable commands to the container. Therefore, in order to start WildFly with specific options, we can use "docker run --name ass -p 8080:8080 -p 9990:9990 svc /opt/jboss/wildfly/bin/standalone.sh -c standalone-full.xml -b 0.0.0.0 -bmanagement 0.0.0.0"
* Active MQ (No longer using. Still might find notes useful for future use)
  * Resources:
    * https://hub.docker.com/r/rmohr/activemq/ - Docker activemq
    * https://maven.repository.redhat.com/ga/org/apache/activemq/activemq-rar/5.11.0.redhat-630377/ - ActiveMQ JBOSS resource adapter
    * http://activemq.apache.org/using-activemq-with-ejb3-jboss-example.html - ActiveMQ JBOSS Message-Driven Bean example
    * https://developer.jboss.org/thread/229884 - JBOSS Resource Adapter import; compile dependency containing this annotation
  * -b 0.0.0.0 does not work inside the Docker container for an http listener. The http listener needs a concrete bind point. Therefore, in the jms service, the container is built to listen to its external network.


