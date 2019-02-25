# convenience file for building simpleservice container
#remove named container from docker cache
docker rm simple-service

#build fresh instance
gradle clean build && docker build -t simple-service .

#run the freshly-build container
docker run --name simple-service -p 8080:8080 simple-service

