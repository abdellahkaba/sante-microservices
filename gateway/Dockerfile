FROM openjdk:17-jdk-slim

LABEL maintainer="Abdoulaye Kaba abdallahkaba98@gmail.com"

COPY target/gateway-service.jar gateway-service.jar

ENTRYPOINT ["java","-jar","gateway-service.jar"]

EXPOSE 8080