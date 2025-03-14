FROM openjdk:22-jdk-slim

WORKDIR /app

EXPOSE 8080

COPY target/one-media-test-task-0.0.1-SNAPSHOT.jar one-media-test-task-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "one-media-test-task-0.0.1-SNAPSHOT.jar"]