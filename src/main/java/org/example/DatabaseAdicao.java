package org.example;

import java.sql.*;
import java.util.Scanner;

public class DatabaseAdicao {
    public static void adicionarProjeto(Connection connection, Scanner dado) {
        System.out.println("Informe o nome do novo projeto (no máximo 50 caracteres): ");
        String nomeProjeto = dado.nextLine();

        System.out.println("Informe agora a descrição do projeto:");
        String descricao = dado.nextLine();

        System.out.println("Informe a data de início do projeto (YYYY-MM-DD)");
        String dataInicio = dado.nextLine();

        System.out.println("Agora informe o número de pessoas que estão neste projeto");
        int numPessoas = dado.nextInt();

        StringBuilder query = new StringBuilder("INSERT INTO Projeto (nomeProjeto, descricao, dataInicio, numPessoas) VALUES (");
        query.append("'" + nomeProjeto + "', ");
        query.append("'" + descricao + "', ");
        query.append("'" + dataInicio + "', ");
        query.append(numPessoas + ")");

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query.toString());

            System.out.println("Novo registro na tabela 'Projeto' foi realizado! Para visualiza-lo, consulte a respectiva tabela");
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar o registro: " + e.getMessage());
        }
    }

    public static void adicionarTarefa(Connection connection, Scanner dado) {
        System.out.println("Informe o nome da nova tarefa (no máximo 50 caracteres)");
        String nomeTarefa = dado.nextLine();

        System.out.println("Informe a descrição da tarefa");
        String descricao = dado.nextLine();

        System.out.println("Agora informe o id do projeto que estará associado à está tarefa");
        DatabaseConsulta.consultarColuna(connection, "Projeto", "nomeProjeto");
        int projetoId = dado.nextInt();

        StringBuilder query = new StringBuilder("INSERT INTO Tarefa (nomeTarefa, descricaoTarefa, projetoId) VALUES (");
        query.append("'" + nomeTarefa + "', ");
        query.append("'" + descricao + "', ");
        query.append(projetoId + ")");

        StringBuilder incrementaTarefa = new StringBuilder("UPDATE Projeto SET numTarefas = numTarefas + 1 WHERE projetoId = " + projetoId);

        try {
            Statement statement = connection.createStatement();

            statement.executeUpdate(query.toString());
            System.out.println("Novo registro na tabela 'Tarefa' foi realizado!");

            statement.executeUpdate(incrementaTarefa.toString());
            System.out.println("Número de tarefas do projeto atualizado!");

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar o registro: " + e.getMessage());
        }

    }

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
