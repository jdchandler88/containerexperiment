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
* JMS Wildfly
  * No security configured. This is true of both the JMS server and the remoting interface.
* UI
  * Built using Vue
  * Served using HTTPD Docker image
* Simple Service: receives messages from front-end to publish manual messages.
  * Resources
    * https://developer.jboss.org/thread/267699. This is EXACTLY what helped solve my problem. Before this, the MDB was timing out when trying to connect to remote server. the property that was used was "http-upgrade-enabled"; switched to "httpUpgradeEnabled"...voila
    *  www.mastertheboss.com/jboss-server/jboss-jms/connecting-to-an-external-wildfly-jms-server. This is great information and should help move the apps in the right direction in terms of externalizing configuration. For now, configuration is hard-coded. A level of indirection should be added such that the code points to a resource which is configured externally.
* General Workflow
  * Developing with docker-compose is actually pretty nice. You can re-build and deploy a container/service without taking down the entire application. The way to do so: docker-compose up -d --build {{service_name}}. Note that for these services, when the compilation takes place outside of the container (gradle clean build, npm run build) that must be run before the docker-compose command. 
* Proxy
  * Configuration is not too difficult. Basic reverse-proxy config can be found on NGINX's website.
  * Reverse proxying websockets requires a little more configuration. This resource was helpful: https://www.nginx.com/blog/websocket-nginx/



