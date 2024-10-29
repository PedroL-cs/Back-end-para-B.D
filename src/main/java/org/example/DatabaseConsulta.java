package org.example;

import java.sql.*;

public class DatabaseConsulta {
    // Consultar coluna específica de uma tabela
    public static void consultarColuna(Connection connection, String tabela, String colunas) {
        String idColuna = tabela + "Id";
        StringBuilder query = new StringBuilder("SELECT " + idColuna);
        String[] colunasArr;

        if (colunas != null && !colunas.isEmpty()) {
            colunasArr = colunas.split("\\s*,\\s*");

            for (String coluna : colunasArr) {
                query.append(", " + coluna.trim());
            }
        } else {
            System.out.println("Nenhuma coluna foi fornecida.");
            return;
        }

        query.append(" FROM " + tabela);

        try {
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query.toString());

            int j = 0;
            while (resultset.next()) {
                j++;
                System.out.println("Item n° " + j + ":");
                int id = resultset.getInt(idColuna);
                System.out.println("Id: " + id);

                for (String coluna : colunasArr){
                    String resultado = resultset.getString(coluna);
                    System.out.println(coluna + ": " + resultado);
                }
                System.out.println();
            }

            resultset.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Consultar tabela inteira
    public static void consultarTabela(Connection connection, String tabela) {
        StringBuilder query = new StringBuilder("SELECT*FROM " + tabela);

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            int numColuna = resultSet.getMetaData().getColumnCount();

            int j = 0;
            while (resultSet.next()) {
                j++;
                System.out.println("Item n° " + j + ":");
                for (int i = 1; i <= numColuna; i++) {
                    System.out.print(rsMetaData.getColumnName(i) + ": "); // Resgata nome da coluna
                    System.out.println(resultSet.getString(i) + " ; "); // Resgata conteúdo da coluna
                }
                System.out.println();
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            if (e.getMessage().contains("doesn't exist")) {
                System.out.println("Parece que você digitou uma tabela que não existe");
                System.out.println("Tente novamente e insira um nome válido para a consulta");
            } else {
                System.err.println("Erro ao consultar a tabela: " + e.getMessage());
            }
        }
    }

    public static void consultarTabelaPorStatus(Connection connection, String tabela, int condicao) {
        StringBuilder query = new StringBuilder("SELECT*FROM " + tabela);

        if (condicao == 2) {
            query.append(" WHERE status = 'Concluido'");
        } else if (condicao == 3) {
            query.append(" WHERE status = 'Pendente'");
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            int numColuna = resultSet.getMetaData().getColumnCount();

            int j = 0;
            while (resultSet.next()) {
                j++;
                System.out.println("Item n° " + j + ":");
                for (int i = 1; i <= numColuna; i++) {
                    System.out.print(rsMetaData.getColumnName(i) + ": "); // Resgata nome da coluna
                    System.out.println(resultSet.getString(i) + " ; "); // Resgata conteúdo da coluna
                }
                System.out.println();
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            if (e.getMessage().contains("doesn't exist")) {
                System.out.println("Parece que você digitou uma tabela que não existe");
                System.out.println("Tente novamente e insira um nome válido para a consulta");
            } else {
                System.err.println("Erro ao consultar a tabela: " + e.getMessage());
            }
        }
    }

    public static void consultarTabelaPorData(Connection connection, String tabela, int condicao, String data) {
        StringBuilder query = new StringBuilder("SELECT*FROM " + tabela);

        if (condicao == 4) {
            query.append(" WHERE dataInicio = '" + data + "'");
        } else if (condicao == 5) {
            query.append(" WHERE dataFim = '" + data + "'");
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            int numColuna = resultSet.getMetaData().getColumnCount();

            int j = 0;
            while (resultSet.next()) {
                j++;
                System.out.println("Item n° " + j + ":");
                for (int i = 1; i <= numColuna; i++) {
                    System.out.print(rsMetaData.getColumnName(i) + ": "); // Resgata nome da coluna
                    System.out.println(resultSet.getString(i) + " ; "); // Resgata conteúdo da coluna
                }
                System.out.println();
            }

        } catch (SQLException e) {
            if (e.getMessage().contains("doesn't exist")) {
                System.out.println("Parece que você digitou uma tabela que não existe");
                System.out.println("Tente novamente e insira um nome válido para a consulta");
            } else {
                System.err.println("Erro ao consultar a tabela: " + e.getMessage());
            }
        }
    }

    public static void consultarTabelaPorNumPessoas(Connection connection, String tabela, int num) {
        StringBuilder query = new StringBuilder("SELECT*FROM " + tabela + " WHERE numPessoas > " + num);

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            int numColuna = resultSet.getMetaData().getColumnCount();

            int j = 0;
            while (resultSet.next()) {
                j++;
                System.out.println("Item n° " + j + ":");
                for (int i = 1; i <= numColuna; i++) {
                    System.out.print(rsMetaData.getColumnName(i) + ": "); // Resgata nome da coluna
                    System.out.println(resultSet.getString(i) + " ; "); // Resgata conteúdo da coluna
                }
                System.out.println();
            }

        } catch (SQLException e) {
            if (e.getMessage().contains("doesn't exist")) {
                System.out.println("Parece que você digitou uma tabela que não existe");
                System.out.println("Tente novamente e insira um nome válido para a consulta");
            } else {
                System.err.println("Erro ao consultar a tabela: " + e.getMessage());
            }
        }
    }

    public static void consultarTabelaPorProjeto(Connection connection, String tabela, String nomeProjeto) {
        StringBuilder query = new StringBuilder("SELECT tarefa.tarefaId, tarefa.nomeTarefa, tarefa.descricaoTarefa, tarefa.status, tarefa.projetoId FROM " + tabela + " JOIN Projeto AS projeto ON tarefa.projetoId = projeto.projetoId WHERE projeto.nomeProjeto = '" + nomeProjeto + "'");

        try {
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            int numColuna = rsMetaData.getColumnCount();

            int j = 0;
            while (resultSet.next()) {
                j++;
                System.out.println("Item n° " + j + ":");
                for (int i = 1; i <= numColuna; i++) {
                    System.out.print(rsMetaData.getColumnName(i) + ": ");
                    System.out.println(resultSet.getString(i) + " ; ");
                }
                System.out.println();
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            if (e.getMessage().contains("doesn't exist")) {
                System.out.println("Parece que você digitou uma tabela que não existe");
                System.out.println("Tente novamente e insira um nome válido para a consulta");
            } else {
                System.err.println("Erro ao consultar a tabela: " + e.getMessage());
            }
        }
    }
}