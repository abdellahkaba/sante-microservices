FROM openjdk:17-jdk-slim

LABEL maintainer="Abdoulaye Kaba abdallahkaba98@gmail.com"

COPY target/patient-service.jar patient-service.jar

ENTRYPOINT ["java","-jar","patient-service.jar"]

EXPOSE 8081