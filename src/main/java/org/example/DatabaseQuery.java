package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseQuery {
    // Consultar coluna específica de uma tabela
    public static void consultarColuna(Connection connection, String tabela, String coluna) {
        String idColuna = tabela + "Id";
        String query = "SELECT "+ idColuna + ", " + coluna + " FROM " + tabela;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);

            System.out.println("ID / Coluna selecionada");
            while (resultset.next()) {
                int id = resultset.getInt(idColuna);
                String resultado = resultset.getString(coluna);

                System.out.println(id+ " ; " +resultado);
            }

            resultset.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Consultar tabela inteira
    public static void consultarTabela(Connection connection, String tabela) {
        String query = "SELECT*FROM " + tabela;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int columnCount = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = resultSet.getString(i);
                    System.out.println(resultSet.getMetaData().getColumnName(i) + ": " + columnValue + " ");
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