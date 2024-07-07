FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY pom.xml /app
COPY src /app/src
RUN ./mvnw clean package -DskipTests
COPY target/HNGAuth.jar app.jar

EXPOSE 8080

ENTRYPOINT["java","-jar","app.jar"]