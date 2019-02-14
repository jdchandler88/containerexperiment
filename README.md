# containerexperiment

## Overview
This is an experiment/overview of a microservice app. It will be managed by Docker-Compose. It will consist of some simple data services implemented in Java as well as some off-the-shelf infrastructure services.

## Architecture
![Architecture](https://github.com/jdchandler88/containerexperiment/blob/master/ContainerExperiment.jpg)

## Repository Structure

* root: repo root
 ** docker-compose.yml: describes microservice app
 ** services: contains data service source and Dockerfile
  *** folder: contains Dockerfile and Maven JavaEE project
