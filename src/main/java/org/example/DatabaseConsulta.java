package org.example;

import java.sql.*;

public class DatabaseConsulta {
    // Consultar coluna específica de uma tabela
    public static void consultarColuna(Connection connection, String tabela, String colunas, String[] condicoes) {
        String idColuna = tabela + "Id";
        StringBuilder query = new StringBuilder("SELECT " + idColuna);
        String[] colunasArr;

        if (colunas != null && !colunas.isEmpty()) {
            colunasArr = colunas.split("\\s*,\\s*");

            for (String coluna : colunasArr) {
                System.out.println(coluna);
                query.append(", " + coluna.trim());
            }
        } else {
            System.out.println("Nenhuma coluna foi fornecida.");
            return;
        }

        query.append(" FROM " + tabela);

        // Caso haja condição, aplica à query
        if (!condicoes[0].isEmpty()) {
            query.append(" WHERE ");
            for (int i = 0; i < condicoes.length; i++) {
                query.append(condicoes[i].trim());
                if (i < condicoes.length - 1) {
                    query.append(" AND ");
                }
            }
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query.toString());

            System.out.println("ID / Coluna selecionada");
            while (resultset.next()) {
                int id = resultset.getInt(idColuna);
                System.out.println("Id: " + id);

                for (String coluna : colunasArr){
                    String resultado = resultset.getString(coluna);
                    System.out.println(coluna + ": " + resultado);
                }
                System.out.println();
            }
            System.out.println();

            resultset.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Consultar tabela inteira
    public static void consultarTabela(Connection connection, String tabela, String[] condicoes) {
        StringBuilder query = new StringBuilder("SELECT*FROM " + tabela);

        // Caso haja condição, aplica à query
        if (!condicoes[0].isEmpty()) {
            query.append(" WHERE ");
            for (int i = 0; i < condicoes.length; i++) {
                query.append(condicoes[i].trim());
                if (i < condicoes.length - 1) {
                    query.append(" AND ");
                }
            }
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
            e.printStackTrace();
        }
    }
}