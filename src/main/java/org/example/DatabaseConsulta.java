package org.example;

import java.sql.*;

public class DatabaseConsulta {
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

        if (condicao == 1) {
            query.append(" WHERE status = 'Concluido'");
        } else if (condicao == 2) {
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

        if (condicao == 1) {
            query.append(" WHERE dataInicio = '" + data + "'");
        } else if (condicao == 2) {
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

    public static void consultarTabelaPorNumPessoas(Connection connection, String tabela, int num) {
        StringBuilder query = new StringBuilder("SELECT*FROM " + tabela + " WHERE numPessoas = " + num);

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

    public static void consultarTabelaPorNumTarefas(Connection connection, int num) {
        StringBuilder query = new StringBuilder("SELECT*FROM Projeto WHERE numTarefas = '" + num + "'");

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
            System.err.println("Erro ao consultar a tabela: " + e.getMessage());
        }
    }

    public static boolean consultarTabelaPorProjeto(Connection connection, String tabela, int projetoId) {
        StringBuilder query = new StringBuilder("SELECT tarefa.tarefaId, tarefa.nomeTarefa, tarefa.descricaoTarefa, tarefa.status, tarefa.projetoId FROM " + tabela + " JOIN Projeto ON tarefa.projetoId = projeto.projetoId WHERE projeto.projetoId = " + projetoId);

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            int numColuna = rsMetaData.getColumnCount();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Não há tarefas neste projeto!");
                return false;
            }

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
        return true;
    }

    public static void consultarTabelaPorProjetoEStatus(Connection connection, String tabela, int projetoId, int condicao) {
        StringBuilder query = new StringBuilder("SELECT tarefa.tarefaId, tarefa.nomeTarefa, tarefa.descricaoTarefa, tarefa.status, tarefa.projetoId FROM " + tabela + " JOIN Projeto AS projeto ON tarefa.projetoId = projeto.projetoId WHERE projeto.projetoId = " + projetoId + " AND ");

        if (condicao == 1) {
            query.append("tarefa.status = 'Concluído'");
        } else if (condicao == 2) {
            query.append("tarefa.status = 'Pendente'");
        }

        try {
            Statement statement = connection.createStatement();
            statement.executeQuery(query.toString());

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
            System.err.println("Erro ao consultar a tabela: " + e.getMessage());
        }
    }

    public static void consultarNomeDeTabela(Connection connection, String tabela, int id) {
        StringBuilder query = new StringBuilder("SELECT nome" + tabela + " FROM " + tabela + " WHERE " + tabela + "Id = " + id);

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            if (resultSet.next()) {
                String nome = resultSet.getString("nome" + tabela);
                System.out.println(nome);
            } else {
                System.out.println("Registro não encontrado.");
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.err.println("Erro ao consultar nome do registro: " + e.getMessage());
        }
    }

    public static void consultarDescricaoDeTabela(Connection connection, String tabela, int id) {
        StringBuilder query = new StringBuilder("SELECT descricao" + tabela + " FROM " + tabela + " WHERE " + tabela + "Id = " + id);

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            if (resultSet.next()) {
                String descricao = resultSet.getString("descricao" + tabela);
                System.out.println(descricao);
            } else {
                System.out.println("Registro não encontrado.");
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.err.println("Erro ao consultar descrição do registro: " + e.getMessage());
        }
    }

    public static String consultarStatusDeTabela(Connection connection, String tabela, int id) {
        StringBuilder query = new StringBuilder("SELECT status FROM " + tabela + " WHERE " + tabela + "Id = " + id);

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            if (resultSet.next()) {
                String status = resultSet.getString("status");
                return status;
            } else {
                System.out.println("Registro não encontrado.");
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.err.println("Erro ao consultar status do registro: " + e.getMessage());
        }
        return null;
    }

    public static void consultarNumPessoasDeTabela(Connection connection, String tabela, int id) {
        StringBuilder query = new StringBuilder("SELECT numPessoas FROM " + tabela + " WHERE " + tabela + "Id = " + id);

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            if (resultSet.next()) {
                String numPessoas = resultSet.getString("numPessoas");
                System.out.println(numPessoas);
            } else {
                System.out.println("Registro não encontrado.");
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.err.println("Erro ao consultar status do registro: " + e.getMessage());
        }
    }

    public static void consultarNumTarefasDeProjeto(Connection connection, int id) {
        StringBuilder query = new StringBuilder("SELECT numTarefas FROM Projeto WHERE projetoId = " + id);

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            if (resultSet.next()) {
                String numTarefas = resultSet.getString("numTarefas");
                System.out.println(numTarefas);
            } else {
                System.out.println("Registro não encontrado.");
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.err.println("Erro ao consultar status do registro: " + e.getMessage());
        }
    }

    public static void consultarDataDeProjeto(Connection connection, String date, int id) {

        StringBuilder query = new StringBuilder("SELECT " + date + " FROM Projeto WHERE projetoId = " + id);

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            if (resultSet.next()) {
                String data = resultSet.getString(date);
                System.out.println(data);
            } else {
                System.out.println("Registro não encontrado.");
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.err.println("Erro ao consultar status do registro: " + e.getMessage());
        }
    }

    public static String findProjectById(Connection connection, int projetoId) {
        StringBuilder query = new StringBuilder("SELECT nomeProjeto FROM Projeto WHERE projetoId = " + projetoId);
        String nomeProjeto = "";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            if (resultSet.next()) {
                nomeProjeto = resultSet.getString("nomeProjeto");
            } else {
                nomeProjeto = "Nome não encontrado";
            }

        } catch (SQLException e) {
            System.err.println("Erro ao consultar projeto: " + e.getMessage());
        }
        return nomeProjeto;
    }

    public static String findTaskById(Connection connection, int tarefaId) {
        StringBuilder query = new StringBuilder("SELECT nomeTarefa FROM Tarefa WHERE tarefaId = " + tarefaId);
        String nomeTarefa = "";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            if (resultSet.next()) {
                nomeTarefa = resultSet.getString("nomeTarefa");
            } else {
                nomeTarefa = "Nome não encontrado";
            }

        } catch (SQLException e) {
            System.err.println("Erro ao consultar projeto: " + e.getMessage());
        }
        return nomeTarefa;
    }
}