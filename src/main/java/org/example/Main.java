package org.example;

import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            System.out.println("Conexão estabelecida com sucesso!");

            Scanner dado = new Scanner(System.in);
            Boolean running = true;

            while (running) {

                // Cooldown para efeitos de visualização
                Thread.sleep(1000);

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
                        break;
                    case 2:
                        adicionarDados(connection, dado);
                        break;
                    case 5:
                        running = false;
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente com um valor válido");
                        break;
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
                System.out.println("Informe agora o nome das colunas desejadas (em caso de múltiplas colunas, separe-as por vírgula) :");
                String colunas = dado.nextLine();

                System.out.println("Deseja aplicar alguma condição? (Ex: status = 'Pendente') (Deixe em branco em caso de nenhuma condição) (em caso de múltiplas condições, separe-as por vírgula)");
                String condicaoColuna = dado.nextLine();
                String[] condicoesColuna = condicaoColuna.split(",");

                System.out.println();
                DatabaseConsulta.consultarColuna(connection, tabelaNome, colunas, condicoesColuna);
                break;
            case 2:
                listarTabelas(connection);
                System.out.println("Informe o nome da tabela:");
                tabelaNome = dado.nextLine();

                System.out.println("Deseja aplicar alguma condição? (Ex: status = 'Concluído') (Deixe em branco em caso de nenhuma condição) (em caso de múltiplas condições, separe-as por vírgula)");
                String condicaoTabela = dado.nextLine();
                String[] condicoesTabela = condicaoTabela.split(",");

                System.out.println();
                DatabaseConsulta.consultarTabela(connection, tabelaNome, condicoesTabela);
                break;
            default:
                System.out.println("Opção inválida! Por favor tente novamente com um valor válido");
                break;
        }
    }

    private static void adicionarDados(Connection connection, Scanner dado) {
        System.out.println("O que você deseja fazer?");
        System.out.println("1. Adicionar coluna numa tabela existente");
        System.out.println("2. Criar uma nova tabela");
        int escolha = dado.nextInt();
        dado.nextLine();

        switch (escolha) {
            case 1:
                listarTabelas(connection);
                System.out.println("Por favor, informe a tabela que receberá uma nova coluna");
                String tabelaNome = dado.nextLine().trim();

                System.out.println("Informe o nome da nova coluna:");
                String nomeColuna = dado.nextLine();

                System.out.println("Agora escolha o tipo de dado que a coluna receberá:");
                System.out.println("Exemplos: INT, VARCHAR, DATE");
                String tipoColuna = dado.nextLine().toUpperCase().trim();

                // pede tamanho da variável caso seja do tipo varchar
                int tamanhoColuna = 0;
                if (tipoColuna.equals("VARCHAR")) {
                    System.out.println("Informe o tamanho para VARCHAR:");
                    tamanhoColuna = dado.nextInt();
                    dado.nextLine();
                }

                boolean autoIncremento = false;
                if (tipoColuna.equals("INT")) {
                    System.out.println("Essa coluna deverá ser auto incrementada? (sim / não");
                    autoIncremento = dado.nextLine().equalsIgnoreCase("sim");
                }

                System.out.println("Informe agora o tipo de chave (deixe em branco se for o caso)");
                System.out.println("Opções: PRIMARY , UNIQUE , FOREIGN");
                String chaveTipo = dado.nextLine().toUpperCase();

                boolean chaveEstrangeira = false;
                String tabelaReferencia = "";
                String colunaReferencia = "";
                if (chaveTipo.equals("FOREIGN")) {
                    System.out.println("Chave selecionada: FOREIGN");

                    System.out.println("Informe o nome da tabela de referência:");
                    listarTabelas(connection);
                    tabelaReferencia = dado.nextLine().toUpperCase().trim();

                    System.out.println("Informe o nome da coluna de referência");
                    listarColunas(connection, tabelaReferencia);
                    colunaReferencia = dado.nextLine().toUpperCase().trim();
                }

                DatabaseAdicao.adicionarColuna(connection, tabelaNome, nomeColuna, tipoColuna, tamanhoColuna, autoIncremento, chaveTipo, tabelaReferencia, colunaReferencia);
                break;
            case 2:
                System.out.println("Por favor, informe o nome da nova tabela");
                tabelaNome = dado.nextLine();
                DatabaseAdicao.adicionarTabela(connection, tabelaNome);

                boolean adicionarColunas = true;
                while (adicionarColunas) {
                    System.out.println("Você deseja adicionar uma nova coluna? (sim / não)");
                    String resposta = dado.nextLine().trim().toLowerCase();
                    if (resposta.equals("não")) {
                        adicionarColunas = false;
                        break;
                    }

                    System.out.println("Informe o nome da nova coluna:");
                    nomeColuna = dado.nextLine();

                    System.out.println("Agora escolha o tipo de dado que a coluna receberá:");
                    System.out.println("Exemplos: INT, VARCHAR, DATE");
                    tipoColuna = dado.nextLine().toUpperCase().trim();

                    // pede tamanho da variável caso seja do tipo varchar
                    tamanhoColuna = 0;
                    if (tipoColuna.equals("VARCHAR")) {
                        System.out.println("Informe o tamanho para VARCHAR:");
                        tamanhoColuna = dado.nextInt();
                        dado.nextLine();
                    }

                    autoIncremento = false;
                    if (tipoColuna.equals("INT")) {
                        System.out.println("Essa coluna deverá ser auto incrementada? (sim / não");
                        autoIncremento = dado.nextLine().equalsIgnoreCase("sim");
                    }

                    System.out.println("Informe agora o tipo de chave (deixe em branco se for o caso)");
                    System.out.println("Opções: PRIMARY , UNIQUE , FOREIGN");
                    chaveTipo = dado.nextLine().trim().toUpperCase();

                    chaveEstrangeira = false;
                    tabelaReferencia = "";
                    colunaReferencia = "";
                    if (chaveTipo.equals("FOREIGN")) {
                        System.out.println("Chave selecionada: FOREIGN");


                        System.out.println("Informe o nome da tabela de referência:");
                        listarTabelas(connection);
                        tabelaReferencia = dado.nextLine().toUpperCase().trim();

                        System.out.println("Informe o nome da coluna de referência");
                        listarColunas(connection, tabelaReferencia);
                        colunaReferencia = dado.nextLine().toUpperCase().trim();
                    }

                    DatabaseAdicao.adicionarColuna(connection, tabelaNome, nomeColuna, tipoColuna, tamanhoColuna, autoIncremento, chaveTipo, tabelaReferencia, colunaReferencia);
                }
                break;
            default:
                System.out.println("Opção inválida");
                break;
        }
    }

    // Método lista todas as tabelas
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

    // Método lista todas as colunas de uma tabela específica
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
