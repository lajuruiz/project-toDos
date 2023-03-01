FROM openjdk:11.0.10-jdk-slim
COPY "./target/todos-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "app.jar"]


