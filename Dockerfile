# Stage 1: Build
FROM maven:3.9.5-eclipse-temurin-21 AS build

# Copiar os arquivos do projeto para o contêiner
COPY pom.xml .
COPY src/ ./src/

# Empacotar a aplicação
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:21-jdk-jammy

# Defina o diretório de trabalho
WORKDIR /app

# Expor a porta 8080
EXPOSE 8080

# Copiar o JAR construído da etapa de build
COPY --from=build /target/listproduct-0.0.1-SNAPSHOT.jar app.jar

# Executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]