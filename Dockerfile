FROM openjdk:17-jdk-alpine
ARG JAR_FILE=./build/libs/homepage-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
//docker build -t mhy5413/bayclip-docker ./