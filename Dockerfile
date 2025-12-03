# 1) Estágio de Build
FROM maven:3.9.9-amazoncorretto-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 2) Estágio Final
FROM amazoncorretto:21-alpine
WORKDIR /app

# Copia o JAR (O APK já está dentro dele, na pasta static!)
COPY --from=build /app/target/rpgsaude-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta correta (TEM QUE SER IGUAL AO PROPERTIES)
EXPOSE 8427

CMD ["java", "-jar", "app.jar"]