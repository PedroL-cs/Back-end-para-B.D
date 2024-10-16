package org.example;

import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {

            try {
                System.out.println("Conexão estabelecida com sucesso!");
                // Cooldown para efeitos de visualização
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Scanner dado = new Scanner(System.in);
            Boolean running = true;

            while (running) {
                System.out.println("O que deseja fazer?");
                System.out.println("1. Consultar dados");
                System.out.println("2. Adicionar dados");
                System.out.println("3. Editar dados");
                System.out.println("4. Excluir dados");
                System.out.println("5. Sair");

                int escolha = dado.nextInt();
                dado.nextLine();

                switch (escolha) {
                    case 1:
                        consultarDados(connection, dado);
                    case 5:
                        running = false;
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente com um valor válido");
                        Thread.sleep(2000);
                        break;
                }

                try {
                    connection.close();
                    // System.out.println("Conexão fechada.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } else {

            System.out.println("Ocorreu um erro ao tentar se conectar");
        }

    }

    private static void consultarDados(Connection connection, Scanner dado) {
        System.out.println("O que você deseja fazer?");
        System.out.println("1. Consultar coluna de uma tabela");
        System.out.println("2. Consultar uma tabela inteira");
        int escolha = dado.nextInt();
        dado.nextLine();

        switch (escolha) {
            case 1:
                listarTabelas(connection);
                System.out.println("Informe o nome da tabela desejada:");
                String tabelaNome = dado.nextLine();

                listarColunas(connection, tabelaNome);
                System.out.println("Informe agora o nome da coluna específica:");
                String colunaNome = dado.nextLine();

                DatabaseQuery.consultarColuna(connection, tabelaNome, colunaNome);
                break;
            case 2:
                System.out.println("Informe o nome da tabela:");
                tabelaNome = dado.nextLine();

                DatabaseQuery.consultarTabela(connection, tabelaNome);
                break;
            default:
                System.out.println("Opção inválida! Por favor tente novamente com um valor válido");
                break;
        }
    }

    private static void listarTabelas(Connection connection) {
        String query = "SHOW TABLES";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);

            System.out.println("Tabelas existentes:");
            while (resultset.next()) {
                String tabela = resultset.getString(1);
                System.out.print(tabela + ", ");
            }
            System.out.println();

            resultset.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listarColunas(Connection connection, String tabela) {
        String query = "SHOW COLUMNS FROM " + tabela;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);

            System.out.println("Colunas disponíveis na tabela " + tabela + ":");
            while (resultset.next()) {
                String coluna = resultset.getString("Field");
                System.out.print(coluna + ", ");
            }
            System.out.println();

            resultset.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
