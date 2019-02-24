# JMS container
This container is used as a service provider for the entire containerexperiment application.

## Running the container
To run the container by itself, follow normal docker build and run procedures. See cleanBuildRun.sh for how it was built and tested for configuration in development.

This container will be used, in production, as a part of the whole application. Therefore, the app-wide docker-compose.yml will launch the container as appropriate (port mapping, network config, etc.)
