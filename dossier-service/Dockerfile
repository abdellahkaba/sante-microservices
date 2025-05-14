FROM openjdk:17-jdk-slim

LABEL maintainer="Abdoulaye Kaba abdallahkaba98@gmail.com"

COPY target/dossier-service.jar dossier-service.jar

ENTRYPOINT ["java","-jar","dossier-service.jar"]

EXPOSE 8083