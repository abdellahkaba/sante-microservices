FROM openjdk:17-jdk-slim

LABEL maintainer="Abdoulaye Kaba abdallahkaba98@gmail.com"

COPY target/medecin-service.jar medecin-service.jar

ENTRYPOINT ["java","-jar","medecin-service.jar"]

EXPOSE 7070