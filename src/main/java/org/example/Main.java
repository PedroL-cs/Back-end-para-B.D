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
                    case 3:
                        editarDados(connection, dado);
                        break;
                    case 4:
                        excluirDados(connection, dado);
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
        System.out.println("1. Consultar registros específicos de uma tabela");
        System.out.println("2. Consultar dados de colunas selecionadas de uma tabela");
        System.out.println("3. Visualizar todos os registros de uma tabela");

        int escolha = dado.nextInt();
        dado.nextLine();

        if (escolha < 1 || escolha > 3) {
            System.out.println("Opção inválida! Por favor tente novamente com um valor válido.");
            return;
        }

        switch (escolha) {
            case 1:
                listarTabelas(connection);
                System.out.println("Informe o nome da tabela desejada:");
                String tabelaNome = dado.nextLine();

                if (tabelaNome.equalsIgnoreCase("projeto")) {
                    System.out.println("Qual será a condição de consulta?");
                    System.out.println("1. Nenhuma");
                    System.out.println("2. Consultar projetos por status (concluído / pendente)");
                    System.out.println("3. Consultar projetos por data (início / fim)");
                    System.out.println("4. Consultar projetos pelo número de pessoas envolvidas");
                    System.out.println("5. Consultar projetos pelo número de tarefas associadas");

                    int escolha2 = dado.nextInt();
                    dado.nextLine();

                    switch (escolha2) {
                        case 1:
                            DatabaseConsulta.consultarTabela(connection, tabelaNome);
                            break;
                        case 2:
                            System.out.println("Como deverá ser feita a consulta?");
                            System.out.println("1. Consultar por projetos concluídos");
                            System.out.println("2. Consultar por projetos pendentes");
                            int escolha3 = dado.nextInt(); dado.nextLine();

                            switch (escolha3) {
                                case 1:
                                    DatabaseConsulta.consultarTabelaPorStatus(connection, tabelaNome, 1);
                                    break;
                                case 2:
                                    DatabaseConsulta.consultarTabelaPorStatus(connection, tabelaNome, 2);
                                    break;
                                default:
                                    System.out.println("Insira um valor válido!");
                                    break;
                            }

                            break;
                        case 3:
                            System.out.println("Que data será usada como condição para a consulta?");
                            System.out.println("1. Data de início");
                            System.out.println("2. Data de finalização");
                            escolha3 = dado.nextInt(); dado.nextLine();

                            switch (escolha3) {
                                case 1:
                                    System.out.println("Informe por favor a data de preferência (YYYY-MM-DD)");
                                    String data = dado.nextLine();

                                    DatabaseConsulta.consultarTabelaPorData(connection, tabelaNome, 1, data);
                                    break;
                                case 2:
                                    System.out.println("Informe por favor a data de preferência (YYYY-MM-DD)");
                                    data = dado.nextLine();

                                    DatabaseConsulta.consultarTabelaPorData(connection, tabelaNome, 2, data);
                                    break;
                                default:
                                    System.out.println("Insira um valor válido!");
                                    break;
                            }

                            break;
                        case 4:
                            System.out.println("Informe o número de pessoas para consulta");
                            int numPessoas = dado.nextInt();

                            DatabaseConsulta.consultarTabelaPorNumPessoas(connection, tabelaNome, numPessoas);
                            break;
                        case 5:
                            System.out.println("Informe o número de tarefas para consulta");
                            int numTarefas = dado.nextInt();

                            DatabaseConsulta.consultarTabelaPorNumTarefas(connection, numTarefas);
                            break;
                        default:
                            System.out.println("Insira um valor válido!");
                            break;
                    }

                } else if (tabelaNome.equalsIgnoreCase("tarefa")) {
                    System.out.println("Qual será a condição de consulta?");
                    System.out.println("1. Nenhuma");
                    System.out.println("2. Consultar tarefas por status (concluída / pendente)");
                    System.out.println("3. Consultar tarefas por projeto associado (id)");
                    int escolha2 = dado.nextInt();
                    dado.nextLine();

                    switch (escolha2) {
                        case 1:
                            DatabaseConsulta.consultarTabela(connection, tabelaNome);
                            break;
                        case 2:
                            System.out.println("Como deverá ser feita a consulta?");
                            System.out.println("1. Consultar por tarefas concluídos");
                            System.out.println("2. Consultar por tarefas pendentes");
                            int escolha3 = dado.nextInt(); dado.nextLine();

                            switch (escolha3) {
                                case 1:
                                    DatabaseConsulta.consultarTabelaPorStatus(connection, tabelaNome, 1);
                                    break;
                                case 2:
                                    DatabaseConsulta.consultarTabelaPorStatus(connection, tabelaNome, 2);
                                    break;
                                default:
                                    System.out.println("Insira um valo válido");
                                    break;
                            }
                        case 3:
                            DatabaseConsulta.consultarColuna(connection, "Projeto", "nomeProjeto");
                            System.out.println("Informe o Id do projeto que será usado como condição de consulta");
                            int projetoId = dado.nextInt(); dado.nextLine();

                            System.out.println("O que você deseja fazer com as tarefas associadas à este projeto");
                            System.out.println("1. Consultar todas as tarefas");
                            System.out.println("2. Consultar tarefas concluídas");
                            System.out.println("3. Consultar tarefas pendentes");
                            escolha3 = dado.nextInt(); dado.nextLine();

                            switch (escolha3) {
                                case 1:
                                    DatabaseConsulta.consultarTabelaPorProjeto(connection, "Tarefa", projetoId);
                                    break;
                                case 2:
                                    DatabaseConsulta.consultarTabelaPorProjetoEStatus(connection, "Tarefa", projetoId, 1);
                                    break;
                                case 3:
                                    DatabaseConsulta.consultarTabelaPorProjetoEStatus(connection, "Tarefa", projetoId, 2);
                                    break;
                                default:
                                    System.out.println("Insira um valor válido!");
                                    break;
                            }

                            break;
                    }
                }

                break;
            case 2:
                listarTabelas(connection);
                System.out.println("Informe o nome da tabela desejada:");
                tabelaNome = dado.nextLine();

                listarColunas(connection, tabelaNome);
                System.out.println("Informe agora o nome das colunas desejadas (em caso de múltiplas colunas, separe-as por vírgula) :");
                String colunas = dado.nextLine();

                System.out.println();
                DatabaseConsulta.consultarColuna(connection, tabelaNome, colunas);
                break;
            case 3:
                listarTabelas(connection);
                System.out.println("Informe o nome da tabela:");
                tabelaNome = dado.nextLine();

                System.out.println();
                DatabaseConsulta.consultarTabela(connection, tabelaNome);
                break;
            default:
                System.out.println("Opção inválida! Por favor tente novamente com um valor válido");
                break;
        }
    }

    private static void adicionarDados(Connection connection, Scanner dado) {
        System.out.println("O que você deseja fazer?");
        System.out.println("1. Adicionar novo registro numa tabela");
        System.out.println("2. Adicionar coluna numa tabela existente");
        System.out.println("3. Criar uma nova tabela");
        int escolha = dado.nextInt();
        dado.nextLine();

        switch (escolha) {
            case 1:
                listarTabelas(connection);
                System.out.println("Informe qual tabela receberá um novo registro");
                String tabelaNome = dado.nextLine();

                if (tabelaNome.equalsIgnoreCase("projeto")) {
                    DatabaseAdicao.adicionarProjeto(connection, dado);
                } else if (tabelaNome.equalsIgnoreCase("tarefa")) {
                    DatabaseAdicao.adicionarTarefa(connection, dado);
                } else {
                    System.out.println("A tabela informada não foi encontrada ou não existe");
                    return;
                }
                break;

            case 2:
                listarTabelas(connection);
                System.out.println("Por favor, informe a tabela que receberá uma nova coluna");
                tabelaNome = dado.nextLine().trim();

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
            case 3:
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

    private static void editarDados(Connection connection, Scanner dado) {
        System.out.println("O que você deseja fazer?");
        System.out.println("1. Editar projeto");
        System.out.println("2. Editar tarefa");
        int escolha = dado.nextInt(); dado.nextLine();

        if (escolha == 1) {
            DatabaseConsulta.consultarTabela(connection, "Projeto");
            System.out.println("Por favor, informe o id do projeto que deseja atualizar");
            int projetoId = dado.nextInt(); dado.nextLine();

            System.out.println("O que você deseja fazer com este projeto?");
            System.out.println("1. Alterar nome");
            System.out.println("2. Alterar descrição");
            System.out.println("3. Alternar status");
            System.out.println("4. Alterar número de pessoas envolvidas");
            System.out.println("5. Alterar número de tarefas");
            System.out.println("6. Alterar data de início ou de conclusão");
            escolha = dado.nextInt(); dado.nextLine();

            switch (escolha) {
                case 1:
                    System.out.print("Nome anterior: ");
                    DatabaseConsulta.consultarNomeDeTabela(connection, "Projeto", projetoId);
                    System.out.print("Novo nome: ");
                    String novoNome = dado.nextLine();

                    DatabaseEdicao.editarNomeRegistro(connection, "Projeto", novoNome, projetoId);
                    break;
                case 2:
                    System.out.println("Descrição anterior: ");
                    DatabaseConsulta.consultarDescricaoDeTabela(connection, "Projeto", projetoId);
                    System.out.println("Nova descrição: ");
                    String novaDesc = dado.nextLine();

                    DatabaseEdicao.editarDescricaoRegistro(connection, "Projeto", novaDesc, projetoId);
                    break;
                case 3:
                    String status = DatabaseConsulta.consultarStatusDeTabela(connection, "Projeto", projetoId);
                    // Tornar o status do projeto como concluído caso esteja pendente
                    if (status.equalsIgnoreCase("Pendente")) {
                        System.out.println("O status do projeto está 'Pendente'. Deseja torná-lo 'Concluído'? (sim / não)");
                        String escolha2 = dado.nextLine();
                        if (escolha2.equalsIgnoreCase("sim")) {
                            DatabaseEdicao.editarStatusRegistro(connection, "Projeto", true, projetoId);
                        } else {
                            System.out.println("Operação cancelada!");
                        }
                    }
                    // Tornar o status do projeto como pendente caso esteja concluído
                    else if (status.equalsIgnoreCase("Concluído")) {
                        System.out.println("O status do projeto está 'Concluído'. Deseja torná-lo 'Pendente'? (sim / não)");
                        String escolha2 = dado.nextLine();
                        if (escolha2.equalsIgnoreCase("sim")) {
                            DatabaseEdicao.editarStatusRegistro(connection, "Projeto", false, projetoId);
                        } else {
                            System.out.println("Operação cancelada!");
                        }
                    }
                    break;
                case 4:
                    System.out.print("Número atual de pessoas no projeto: ");
                    DatabaseConsulta.consultarNumPessoasDeTabela(connection, "Projeto", projetoId);
                    System.out.println("Você deseja alterar o número de pessoas envolvidas neste projeto? (sim / não)");
                    String escolha2 = dado.nextLine();

                    if (escolha2.equalsIgnoreCase("sim")) {
                        System.out.println("Informe o novo número de pessoas envolvidas neste projeto:");
                        int novoNumPessoas = dado.nextInt(); dado.nextLine();

                        if (novoNumPessoas < 0) {
                            System.out.println("O valor não é válido. Tente novamente e forneça um valor válido para o número de pessoas");
                        } else
                            DatabaseEdicao.editarNumPessoasRegistro(connection, "Projeto",novoNumPessoas, projetoId);
                    } else {
                        System.out.println("Operação cancelada!");
                    }
                    break;
                case 5:
                    System.out.print("Número atual de tarefas no projeto: ");
                    DatabaseConsulta.consultarNumTarefasDeProjeto(connection, projetoId);
                    System.out.println("Você deseja alterar o número de tarefas deste projeto? (sim / não)");
                    escolha2 = dado.nextLine();

                    if (escolha2.equalsIgnoreCase("sim")) {
                        System.out.println("Informe o novo número de tarefas no projeto:");
                        int novoNumTarefas = dado.nextInt(); dado.nextLine();

                        if (novoNumTarefas < 0) {
                            System.out.println("O valor não é válido. Tente novamente e forneça um valor válido para o número de tarefas");
                        } else {
                            DatabaseEdicao.editarNumTarefasProjeto(connection, novoNumTarefas, projetoId);
                        }
                    } else {
                        System.out.println("Operação cancelada!");
                    }
                    break;
                case 6:
                    System.out.println("Você deseja alterar a data de início ou de conclusão do projeto?");
                    System.out.println("1. Início");
                    System.out.println("2. Conclusão");
                    int escolha3 = dado.nextInt(); dado.nextLine();

                    if (escolha3 == 1) {
                        System.out.print("Data de início atual: ");
                        DatabaseConsulta.consultarDataDeProjeto(connection, "dataInicio", projetoId);
                        System.out.println("Insira a nova data de início do projeto (YYYY-MM-DD) (Insira 'Cancelar' caso queira cancelar a ação");
                        String novaData = dado.nextLine();

                        if (novaData.equalsIgnoreCase("cancelar")) {
                            System.out.println("Operação cancelada");
                            break;
                        } else {
                            DatabaseEdicao.editarDataProjeto(connection, "dataInicio", novaData, projetoId);
                        }
                    } else if (escolha3 == 2) {
                        System.out.print("Data de início atual: ");
                        DatabaseConsulta.consultarDataDeProjeto(connection, "dataFim", projetoId);
                        System.out.println("Insira a nova data de conclusão do projeto (YYYY-MM-DD) (Insira 'Cancelar' caso queira cancelar a ação");
                        String novaData = dado.nextLine();

                        if (novaData.equalsIgnoreCase("cancelar")) {
                            System.out.println("Operação cancelada");
                            break;
                        } else {
                            DatabaseEdicao.editarDataProjeto(connection, "dataFim", novaData, projetoId);
                        }
                    } else {
                        System.out.println("Operação cancelada");
                    }
                    break;
            }
        } else if (escolha == 2) {
            DatabaseConsulta.consultarColuna(connection, "Projeto", "nomeProjeto");
            System.out.println("Por favor, escolha o projeto associado à tarefa que você deseja atualizar");
            int projetoId = dado.nextInt(); dado.nextLine();

            DatabaseConsulta.consultarTabelaPorProjeto(connection, "Tarefa", projetoId);
            System.out.println("Por favor, informe o id da tarefa que deseja atualizar");
            int tarefaId = dado.nextInt(); dado.nextLine();

            System.out.println("O que você deseja fazer com esta tarefa?");
            System.out.println("1. Alterar nome");
            System.out.println("2. Alterar descrição");
            System.out.println("3. Alternar status");
            System.out.println("4. Alterar número de pessoas envolvidas");
            escolha = dado.nextInt(); dado.nextLine();

            switch (escolha) {
                case 1:
                    System.out.print("Nome anterior: ");
                    DatabaseConsulta.consultarNomeDeTabela(connection, "Tarefa", tarefaId);

                    System.out.println("Você deseja alterar o nome da tarefa? (sim / não)");
                    String escolha2 = dado.nextLine();

                    if (escolha2.equalsIgnoreCase("sim")) {
                        System.out.println("Informe o novo nome: ");
                        String novoNome = dado.nextLine();

                        DatabaseEdicao.editarNomeRegistro(connection, "Tarefa", novoNome, tarefaId);
                    } else {
                        System.out.println("Operação cancelada!");
                    }
                    break;

                case 2:
                    System.out.println("Descrição anterior:");
                    DatabaseConsulta.consultarDescricaoDeTabela(connection, "Tarefa", tarefaId);

                    System.out.println("Você deseja alterar a descrição da tarefa? (sim / não)");
                    escolha2 = dado.nextLine();

                    if (escolha2.equalsIgnoreCase("sim")) {
                        System.out.println("Informe a nova descrição: ");
                        String novaDesc = dado.nextLine();

                        DatabaseEdicao.editarDescricaoRegistro(connection, "Tarefa", novaDesc, tarefaId);
                    } else {
                        System.out.println("Operação cancelada!");
                    }
                    break;
                case 3:
                    String status = DatabaseConsulta.consultarStatusDeTabela(connection, "Tarefa", tarefaId);
                    // Tornar o status da tarefa como concluído caso esteja pendente
                    if (status.equalsIgnoreCase("Pendente")) {
                        System.out.println("O status da tarefa está 'Pendente'. Deseja torná-la 'Concluído'? (sim / não)");
                        escolha2 = dado.nextLine();
                        if (escolha2.equalsIgnoreCase("sim")) {
                            DatabaseEdicao.editarStatusRegistro(connection, "Tarefa", true, tarefaId);
                        } else {
                            System.out.println("Operação cancelada!");
                        }
                    }
                    // Tornar o status da tarefa como pendente caso esteja concluído
                    else if (status.equalsIgnoreCase("Concluído")) {
                        System.out.println("O status da tarefa está 'Pendente'. Deseja torná-la 'Concluído'? (sim / não)");
                        escolha2 = dado.nextLine();
                        if (escolha2.equalsIgnoreCase("sim")) {
                            DatabaseEdicao.editarStatusRegistro(connection, "Tarefa", false, tarefaId);
                        } else {
                            System.out.println("Operação cancelada!");
                        }
                    }
                    break;
                case 4:
                    System.out.print("Número atual de pessoas na tarefa: ");
                    DatabaseConsulta.consultarNumPessoasDeTabela(connection, "Tarefa", tarefaId);
                    System.out.println("Você deseja alterar o número de pessoas envolvidas nesta tarefa? (sim / não)");
                    escolha2 = dado.nextLine();

                    if (escolha2.equalsIgnoreCase("sim")) {
                        System.out.println("Informe o novo número de pessoas envolvidas nesta tarefa:");
                        int novoNumPessoas = dado.nextInt(); dado.nextLine();

                        if (novoNumPessoas < 0) {
                            System.out.println("O valor não é válido. Tente novamente e forneça um valor válido para o número de pessoas");
                        } else
                            DatabaseEdicao.editarNumPessoasRegistro(connection, "Tarefa",novoNumPessoas, tarefaId);
                    } else {
                        System.out.println("Operação cancelada!");
                    }
                    break;
            }
        } else {
            System.out.println("Opção inválida. Por favor tente novamente com um valor válido");
        }
    }

    private static void excluirDados(Connection connection, Scanner dado) {
        System.out.println("ATENÇÃO: a exclusão de dados não pode ser desfeita!");
        System.out.println("O que deseja fazer?");
        System.out.println("1. Excluir registros de uma tabela");
        System.out.println("2. Excluir colunas selecionadas de uma tabela");
        System.out.println("3. Excluir uma tabela inteira");
        int escolha = dado.nextInt();
        dado.nextLine();

        switch (escolha) {
            case 1:
                listarTabelas(connection);
                System.out.println("Informe o nome da tabela desejada:");
                String tabelaNome = dado.nextLine();

                if (tabelaNome.equalsIgnoreCase("projeto")) {
                    System.out.println("Qual será a condição da exclusão?");
                    System.out.println("1. Excluir projetos concluídos");
                    System.out.println("2. Excluir projetos pendentes");
                    System.out.println("3. Excluir projetos de acordo com a data de finalização");
                    System.out.println("4. Excluir projetos de acordo com seu Id");
                    int escolha2 = dado.nextInt();
                    dado.nextLine();

                    switch (escolha2) {
                        case 1:
                            DatabaseConsulta.consultarTabelaPorStatus(connection, "Projeto", 2);
                            System.out.println("Esses registros devem ser excluídos? Isso não poderá ser desfeito. (sim / não)");
                            String escolha3 = dado.nextLine();

                            if (escolha3.equalsIgnoreCase("sim")) {
                                DatabaseExclusao.excluirRegistroPorStatus(connection, "Projeto", 1);
                            } else {
                                System.out.println("A exclusão foi cancelada!");
                            }
                            break;
                        case 2:
                            DatabaseConsulta.consultarTabelaPorStatus(connection, "Projeto", 3);
                            System.out.println("Esses registros devem ser excluídos? Isso não poderá ser desfeito. (sim / não)");
                            escolha3 = dado.nextLine();

                            if (escolha3.equalsIgnoreCase("sim")) {
                                DatabaseExclusao.excluirRegistroPorStatus(connection, "Projeto", 2);
                            } else {
                                System.out.println("A exclusão foi cancelada!");
                            }
                            break;
                        case 3:
                            System.out.println("Informe a data desejada: (YYYY-MM-DD)");
                            String data = dado.nextLine();

                            DatabaseConsulta.consultarTabelaPorData(connection, "Projeto", 5, data);
                            System.out.println("Esse registro deve ser excluído? Isso não poderá ser desfeito. (sim / não)");
                            escolha3 = dado.nextLine();

                            if (escolha3.equalsIgnoreCase("sim")) {
                                DatabaseExclusao.excluirRegistroPorData(connection, data);
                            } else {
                                System.out.println("A exclusão foi cancelada!");
                            }
                            break;
                        case 4:
                            DatabaseConsulta.consultarColuna(connection, "Projeto", "nomeProjeto");
                            System.out.println("Digite o Id do registro a ser excluído:");
                            int id = dado.nextInt(); dado.nextLine();

                            System.out.println("Esse registro deve ser excluído? Isso não poderá ser desfeito. (sim / não)");
                            escolha3 = dado.nextLine();

                            if (escolha3.equalsIgnoreCase("sim")) {
                                DatabaseExclusao.excluirRegistroPorId(connection, "Projeto", id);
                            } else {
                                System.out.println("A exclusão foi cancelada!");
                            }
                            break;
                        default:
                            System.out.println("Insira um valor válido!");
                            break;
                    }

                } else if (tabelaNome.equalsIgnoreCase("tarefa")) {
                    System.out.println("Qual será a condição de exclusão?");
                    System.out.println("1. Excluir tarefas concluídas");
                    System.out.println("2. Excluir tarefas pendentes");
                    System.out.println("3. Excluir tarefas de acordo com o projeto associado");
                    System.out.println("4. Excluir tarefas de acordo com seu Id");
                    int escolha2 = dado.nextInt(); dado.nextLine();

                    switch (escolha2) {
                        case 1:
                            DatabaseConsulta.consultarTabelaPorStatus(connection, "Tarefa", 2);
                            System.out.println("Esses registros devem ser excluídos? Isso não poderá ser desfeito. (sim / não)");
                            String escolha3 = dado.nextLine();

                            if (escolha3.equalsIgnoreCase("sim")) {
                                DatabaseExclusao.excluirRegistroPorStatus(connection, "Tarefa", 1);
                            } else {
                                System.out.println("A exclusão foi cancelada!");
                            }
                            break;
                        case 2:
                            DatabaseConsulta.consultarTabelaPorStatus(connection, "Tarefa", 3);
                            System.out.println("Esses registros devem ser excluídos? Isso não poderá ser desfeito. (sim / não)");
                            escolha3 = dado.nextLine();

                            if (escolha3.equalsIgnoreCase("sim")) {
                                DatabaseExclusao.excluirRegistroPorStatus(connection, "Tarefa", 2);
                            } else {
                                System.out.println("A exclusão foi cancelada!");
                            }
                            break;
                        case 3:
                            DatabaseConsulta.consultarColuna(connection, "Projeto", "nomeProjeto");
                            System.out.println("Informe o Id do projeto desejado");
                            int projetoId = dado.nextInt(); dado.nextLine();

                            System.out.println("O que deseja fazer com as tarefas associadas à este projeto?");
                            System.out.println("1. Excluir as tarefas concluídas");
                            System.out.println("2. Excluir as tarefas pendentes");
                            System.out.println("3. Excluir as tarefas de acordo com seu Id");
                            System.out.println("4. Excluir todas as tarefas deste projeto *");
                            int escolha4 = dado.nextInt(); dado.nextLine();

                            switch (escolha4) {
                                case 1:
                                    DatabaseConsulta.consultarTabelaPorProjetoEStatus(connection, "Tarefa", projetoId, 1);

                                    System.out.println("Tem certeza que deseja excluir essas tarefas? Isso não poderá ser desfeito. (sim / não)");
                                    String escolha5 = dado.nextLine();

                                    if (escolha5.equalsIgnoreCase("sim")) {
                                        DatabaseExclusao.excluirTarefaPorStatusEProjeto(connection, projetoId, 1);
                                    } else  {
                                        System.out.println("Exclusão cancelada!");
                                    }
                                    break;
                                case 2:
                                    DatabaseConsulta.consultarTabelaPorProjetoEStatus(connection, "Tarefa", projetoId, 2);

                                    System.out.println("Tem certeza que deseja excluir estas tarefas? Isso não poderá ser desfeito. (sim / não)");
                                    escolha5 = dado.nextLine();

                                    if (escolha5.equalsIgnoreCase("sim")) {
                                        DatabaseExclusao.excluirTarefaPorStatusEProjeto(connection, projetoId, 2);
                                    } else  {
                                        System.out.println("Exclusão cancelada!");
                                    }
                                    break;
                                case 3:
                                    DatabaseConsulta.consultarTabelaPorProjeto(connection, "Tarefa", projetoId);

                                    System.out.println("Informe o Id ou Ids que serão excluídos (Separe-os por vírgula em caso de múltiplos ids)");
                                    String ids = dado.nextLine();

                                    System.out.println("Tem certeza que deseja excluir estas tarefas? Isso não poderá ser desfeito. (sim / não)");
                                    escolha5 = dado.nextLine();

                                    if (escolha5.equalsIgnoreCase("sim")) {
                                        DatabaseExclusao.excluirRegistroPorIds(connection, ids);
                                    } else  {
                                        System.out.println("Exclusão cancelada!");
                                    }
                                    break;
                                case 4:
                                    DatabaseConsulta.consultarTabelaPorProjeto(connection, "Tarefa", projetoId);

                                    System.out.println("ATENÇÃO: esta operação não é recomendável. Saiba o que está fazendo");

                                    System.out.println("Tem certeza que deseja excluir todas essas tarefas? Isso não poderá ser desfeito. (sim / não)");
                                    escolha5 = dado.nextLine();

                                    if (escolha5.equalsIgnoreCase("sim")) {
                                        DatabaseExclusao.excluirTarefaPorProjetoAssociado(connection, projetoId);
                                    } else  {
                                        System.out.println("Exclusão cancelada!");
                                    }
                                    break;
                            }
                            break;
                        case 4:
                            DatabaseConsulta.consultarColuna(connection, "Tarefa", "nomeTarefa");
                            System.out.println("Digite o Id do registro a ser excluído:");
                            int id = dado.nextInt(); dado.nextLine();

                            System.out.println("Esse registro deve ser excluído? Isso não poderá ser desfeito. (sim / não)");
                            escolha3 = dado.nextLine();

                            if (escolha3.equalsIgnoreCase("sim")) {
                                DatabaseExclusao.excluirRegistroPorId(connection, "Tarefa", id);
                            } else {
                                System.out.println("A exclusão foi cancelada!");
                            }
                            break;
                    }
                }

                break;
            case 2:
                listarTabelas(connection);
                System.out.println("Informe o nome da tabela desejada:");
                tabelaNome = dado.nextLine();

                listarColunas(connection, tabelaNome);
                System.out.println("Informe agora o nome das colunas desejadas (em caso de múltiplas colunas, separe-as por vírgula) :");
                String colunas = dado.nextLine();

                DatabaseExclusao.excluirColuna(connection, tabelaNome, colunas);
                break;
            case 3:
                System.out.println("ATENÇÃO: a exclusão de tabelas não é aconselhável. Saiba o que está fazendo!");
                listarTabelas(connection);
                System.out.println("Forneça o nome da tabela a ser excluída:");
                tabelaNome = dado.nextLine();

                DatabaseExclusao.excluirTabela(connection, tabelaNome);
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
