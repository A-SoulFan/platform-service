FROM openjdk:8-jdk-alpine

ARG PROJECT_NAME=default

RUN addgroup -S spring && adduser -S spring -G spring

RUN mkdir -p /home/admin/${PROJECT_NAME}/logs \
    && chown -R spring:spring /home/admin/${PROJECT_NAME}/logs \
    && chmod -R 0775 /home/admin/${PROJECT_NAME}/logs
USER spring:spring

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]