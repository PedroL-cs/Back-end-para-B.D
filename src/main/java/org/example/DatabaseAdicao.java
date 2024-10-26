package org.example;

import java.sql.*;

public class DatabaseAdicao {
    public static void adicionarColuna(Connection connection, String tabelaNome, String nomeColuna, String tipoColuna, int tamanhoColuna, boolean autoIncremento, String chaveTipo, String tabelaReferencia, String colunaReferencia) {
        StringBuilder query = new StringBuilder("ALTER TABLE " + tabelaNome + " ADD " + nomeColuna + " " + tipoColuna);

        try {
            if (tipoColuna.equals("VARCHAR"))
                query.append("(" + tamanhoColuna + ")");

            if (tipoColuna.equals("INT") && autoIncremento)
                query.append(" AUTO_INCREMENT");

            Statement statement = connection.createStatement();
            statement.executeUpdate(query.toString());
            System.out.println("Coluna adicionada com sucesso!");

            if (!chaveTipo.isEmpty() && !chaveTipo.isBlank()) {
                StringBuilder constraintQuery = new StringBuilder("ALTER TABLE " + tabelaNome + " ADD CONSTRAINT ");
                if (chaveTipo.equals("PRIMARY")) {
                    constraintQuery.append("PRIMARY KEY (" + nomeColuna + ")");
                } else if (chaveTipo.equals("UNIQUE")) {
                    constraintQuery.append("UNIQUE (" + nomeColuna + ")");
                } else if (chaveTipo.equals("FOREIGN")) {
                    constraintQuery.append("fk_" + nomeColuna + " FOREIGN KEY (" + nomeColuna + ") REFERENCES " + tabelaReferencia + "(" + colunaReferencia + ")");
                }

                statement.executeUpdate(constraintQuery.toString());
                System.out.println(chaveTipo + " adicionada com sucesso!");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar a coluna: " + e.getMessage());
        }
    }

    public static void adicionarTabela(Connection connection, String tabelaNome) {
        StringBuilder query = new StringBuilder("CREATE TABLE " + tabelaNome + "(" + tabelaNome + "Id INT PRIMARY KEY AUTO_INCREMENT)");

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query.toString());
            System.out.println("Tabela " + tabelaNome + " criada com sucesso! Lembre-se que a primary key desta tabela é o nome dela junto à 'Id'");
        } catch (SQLException e) {
            System.err.println("Erro ao criar a tabela: " + e.getMessage());
        }
    }
}
