FROM maven:3.9.9-amazoncorretto-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests
FROM amazoncorretto:21-alpine
COPY --from=build target/rpgsaude-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8427
CMD ["java", "-jar", "/app.jar"]