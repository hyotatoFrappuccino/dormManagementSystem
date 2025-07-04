FROM openjdk:17.0.1-jdk-slim
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod", "-Duser.timezone=Asia/Seoul"]