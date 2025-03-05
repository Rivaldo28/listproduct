FROM openjdk:21-slim AS build

# Instalar Maven
RUN apt-get update && apt-get install -y maven

# Definir diretório de trabalho
WORKDIR /app

# Copiar pom.xml e baixar as dependências
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Fase de execução usando OpenJDK 21
FROM openjdk:21-slim

# Definir diretório de trabalho
WORKDIR /app

# Copiar o JAR gerado da fase de construção
COPY --from=build /app/target/listproduct-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta que a aplicação irá usar
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]