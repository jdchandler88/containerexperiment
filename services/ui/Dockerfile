#dockerfile to build and deploy UI

#build the ui. this way, we don't need to install nodejs on the docker machine
FROM node:10-alpine as builder
RUN mkdir /app
COPY . /app
WORKDIR /app
RUN npm install
RUN npm run build

#final build stage
FROM httpd:2.4
COPY --from=builder /app/dist/ /usr/local/apache2/htdocs/
