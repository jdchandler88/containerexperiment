docker rm service
docker run --name service -p 127.0.0.1:8080:8080 -p 127.0.0.1:9990:9990 svc /opt/jboss/wildfly/bin/standalone.sh -c standalone-full.xml -b 0.0.0.0 -bmanagement 0.0.0.0

