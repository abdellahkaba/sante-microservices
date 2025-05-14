FROM openjdk:17-jdk-slim

LABEL maintainer="Abdoulaye Kaba abdallahkaba98@gmail.com"

COPY target/rdv-service.jar rdv-service.jar

ENTRYPOINT ["java","-jar","rdv-service.jar"]

EXPOSE 8082