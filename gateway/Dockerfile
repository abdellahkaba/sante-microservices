FROM openjdk:17-jdk-slim

LABEL maintainer="Abdoulaye Kaba abdallahkaba98@gmail.com"

COPY target/gateway.jar gateway.jar

ENTRYPOINT ["java","-jar","gateway.jar"]

EXPOSE 9999