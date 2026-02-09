FROM eclipse-temurin:21-jre-alpine

LABEL maintainer="Boby"
LABEL version="1.0"
LABEL description="Backend API service"

ENV APP_NAME=Basilisk
ENV APP_DESCRIPTION="Simple logistic application for training"
ENV APP_PORT=7070
ENV APP_VERSION=1.0.0
ENV DB_USERNAME=rootadmin
ENV DB_PASSWORD=nodecomplete
ENV DB_NAME=BasiliskNext
ENV DB_CONTAINER=sqlserver2022
ENV DB_PORT=1433

WORKDIR /app

#COPY target/basiliskSB-5.0.0.jar /app/basiliskSB-5.0.0.jar
COPY target/basiliskSB-5.0.0.jar app.jar

EXPOSE 7070

#USER Boby

ENTRYPOINT [ "java", "-jar", "app.jar" ]

#CMD ["/app/basiliskSB-5.0.0.jar"]