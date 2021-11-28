FROM openjdk:8-jdk-alpine

ENV TZ=Asia/Shanghai

ARG PROJECT_NAME=default
RUN apk add --update ttf-dejavu fontconfig

RUN mkdir -p /home/admin/${PROJECT_NAME}/logs \
#    && chown -R spring:spring /home/admin/${PROJECT_NAME}/logs \
    && chmod -R 0775 /home/admin/${PROJECT_NAME}/logs

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]