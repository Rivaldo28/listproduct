package com.mvc.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Component
public class DatabaseInitializer {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    // Método para inicializar o banco de dados
    @PostConstruct
    public void initDatabase() {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            // Verificar se o banco de dados "technology" já existe
            String checkDbQuery = "SELECT 1 FROM pg_database WHERE datname = 'technology'";

            try (Statement stmt = connection.createStatement()) {
                var resultSet = stmt.executeQuery(checkDbQuery);
                if (!resultSet.next()) {
                    // Se não existir, cria o banco de dados
                    String createDbQuery = "CREATE DATABASE technology";
                    stmt.execute(createDbQuery);
                    System.out.println("Banco de dados 'technology' criado com sucesso!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
