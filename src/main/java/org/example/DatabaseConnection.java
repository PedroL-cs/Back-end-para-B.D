package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/projetos";
    private static final String USER = "root";
    private static final String PASSWORD = "pedroluis3003";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            if (e instanceof java.sql.SQLSyntaxErrorException) {
                System.out.println("Erro de sintaxe SQL: " + e.getMessage());
                System.out.println("Verifique se o nome do banco de dados ou informações de usuário estão corretas!");
            } else {
                e.printStackTrace();
            }
        }
        return connection;
    }
}