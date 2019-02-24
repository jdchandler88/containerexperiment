#convenience script for developing the container. removes old named container from cache, builds fresh, and launches new named container

#build the jms provider container and give it a tag "jms"
docker build -t jms .

#remove cached container called "myjms".
docker rm myjms

#create a new version of the container in the cache. call it "myjms". Give it a hostname of "jms-server". make the container launch jboss in standalone mode with configuration "standalone-full.xml" and tell the server to bind/listen on its external network.
docker run --name myjms -p 8081:8080
