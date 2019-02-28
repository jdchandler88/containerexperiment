#dockerfile to build and deploy UI
FROM node:10.1-alpine as builder
USER root
RUN mkdir /app
RUN chmod -R node /app
USER node
ADD . /app
WORKDIR /app
RUN npm install
RUN npm build

FROM httpd:2.4
COPY --from=builder /app/dist/ /usr/local/apache2/htdocs/
