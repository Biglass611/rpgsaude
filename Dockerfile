# 1) Estágio de Build do Java
FROM maven:3.9.9-amazoncorretto-21-alpine AS build
WORKDIR /app

# Copia todo o projeto para dentro do Docker
COPY . .


RUN mvn clean package -DskipTests


FROM amazoncorretto:21-alpine
WORKDIR /app

RUN mkdir -p /app/apk

# Copia o JAR gerado
COPY --from=build /app/target/rpgsaude-0.0.1-SNAPSHOT.jar app.jar

# --- O PULO DO GATO ---
# Em vez de compilar o Android, copiamos o arquivo que você já colocou na pasta do projeto
COPY --from=build /app/src/main/resources/static/rpgsaude.apk /app/apk/rpgsaude.apk

# Expõe a porta
EXPOSE 8427

# Roda a aplicação
CMD ["java", "-jar", "app.jar"]