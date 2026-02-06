FROM eclipse-temurin:21-jdk

ENV APP_NAME=Basilisk
ENV APP_DESCRIPTION="Simple logistic application for training"
ENV APP_PORT=7070
ENV APP_VERSION=1.0.0
ENV DB_USERNAME=rootadmin
ENV DB_PASSWORD=nodecomplete
ENV DB_NAME=BasiliskNext
ENV DB_PORT=1433

COPY target/basiliskSB-5.0.0.jar /app/basiliskSB-5.0.0.jar

CMD ["java", "-jar", "/app/basiliskSB-5.0.0.jar"]