# Fase de construção usando Maven
FROM maven:3.9.0-openjdk-21 AS build

# Definir diretório de trabalho
WORKDIR /app

# Copiar pom.xml e baixar as dependências
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Fase de execução usando OpenJDK
FROM openjdk:21-jdk-slim

# Definir diretório de trabalho
WORKDIR /app

# Copiar o JAR gerado da fase de construção
COPY --from=build /app/target/listproduct-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta que a aplicação irá usar
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]