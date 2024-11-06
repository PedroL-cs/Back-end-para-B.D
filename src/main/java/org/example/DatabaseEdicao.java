package org.example;

import java.sql.*;

public class DatabaseEdicao {
    public static void editarNomeRegistro(Connection connection, String tabela, String novoNome,int id) {
        StringBuilder query = new StringBuilder("UPDATE " + tabela + " SET nome" + tabela + " = '" + novoNome + "' WHERE " + tabela + "Id = " + id);

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query.toString());

            System.out.println("O nome do registro foi alterado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao tentar editar nome da tabela: " + e.getMessage());
        }
    }

    public static void editarDescricaoRegistro(Connection connection, String tabela, String novaDesc, int id) {
        StringBuilder query = new StringBuilder("UPDATE " + tabela + " SET descricao" + tabela + " = '" + novaDesc + "' WHERE " + tabela + "Id = " + id);

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query.toString());

            System.out.println("A descrição do registro foi alterada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao tentar editar descrição da tabela: " + e.getMessage());
        }
    }

    public static void editarStatusRegistro(Connection connection, String tabela, Boolean status, int id) {
        StringBuilder query = new StringBuilder("UPDATE " + tabela + " SET status = ");

        if (status) {
            query.append("'Concluído' WHERE " + tabela + "Id = " + id);
        } else {
            query.append("'Pendente' WHERE " + tabela + "Id = " + id);
        }

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query.toString());

            System.out.println("O status do registro foi alterado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao tentar alterar status da tabela: " + e.getMessage());
        }
    }

    public static void editarNumPessoasRegistro(Connection connection, String tabela, int novoNumPessoas, int id) {
        StringBuilder query = new StringBuilder("UPDATE " + tabela + " SET numPessoas = " + novoNumPessoas + " WHERE " + tabela + "Id = " + id);

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query.toString());

            System.out.println("O número de pessoas envolvidas foi alterado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao tentar alterar número de pessoas da tabela: " + e.getMessage());
        }
    }

    public static void editarNumTarefasProjeto(Connection connection, int novoNumTarefas, int id) {
        StringBuilder query = new StringBuilder("UPDATE Projeto SET numTarefas = " + novoNumTarefas + " WHERE projetoId = " + id);

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query.toString());

            System.out.println("O número de tarefas do projeto foi alterado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao tentar alterar número de tarefas da tabela: " + e.getMessage());
        }
    }

    public static void editarDataProjeto(Connection connection, String date, String novaData, int id) {
        StringBuilder query = new StringBuilder("UPDATE Projeto SET " + date + " = '" + novaData + "' WHERE projetoId = " + id);

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query.toString());

            if (date.equalsIgnoreCase("dataInicio")) {
                System.out.println("A data de início foi alterada com sucesso!");
            } else if (date.equalsIgnoreCase("dataFim")) {
                System.out.println("A data de conclusão foi alterada com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao tentar alterar número de tarefas da tabela: " + e.getMessage());
        }
    }
}
