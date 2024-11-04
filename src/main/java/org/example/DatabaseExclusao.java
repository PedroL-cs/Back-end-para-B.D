package org.example;

import java.sql.*;
import java.util.Scanner;

public class DatabaseExclusao {
    static Scanner dado = new Scanner(System.in);

    public static void excluirRegistroPorStatus(Connection connection, String tabelaNome, int condicao) {
        StringBuilder query = new StringBuilder("DELETE FROM " + tabelaNome);

        if (condicao == 1) {
            query.append(" WHERE status = 'Concluído'");
        } else if (condicao == 2) {
            query.append(" WHERE status = 'Pendente'");
        } else
            System.out.println("Algo deu errado");

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query.toString());

            System.out.println("Os registros foram excluídos com sucesso");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir registro(s): " + e.getMessage());
        }
    }

    public static void excluirRegistroPorData(Connection connection, String data) {
        StringBuilder query = new StringBuilder("DELETE FROM Projeto WHERE dataFim = '" + data + "'");

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query.toString());

            System.out.println("Os registros foram excluídos com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir registro(s): " + e.getMessage());
        }
    }

    public static void excluirRegistroPorId(Connection connection, String tabelaNome, int id) {
        StringBuilder query = new StringBuilder("DELETE FROM " + tabelaNome + " WHERE " + tabelaNome + "Id = " + id);

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query.toString());

            System.out.println("Os registros foram excluídos com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir registro(s): " + e.getMessage());
        }
    }

    public static void excluirRegistroPorIds(Connection connection, String ids) {
        String[] idArr = ids.split("\\s*,\\s*");

        if (idArr.length == 0) {
            System.out.println("Nenhum ID foi fornecido para exclusão.");
            return;
        }

        StringBuilder query = new StringBuilder("DELETE FROM Tarefa WHERE tarefaId IN (");

        for (int i = 0; i < idArr.length; i++) {
            query.append(idArr[i]);
            if (i < idArr.length - 1) {
                query.append(", ");
            }
        }
        query.append(")");

        try {
            Statement statement = connection.createStatement();
            int exclusoes = statement.executeUpdate(query.toString());
            System.out.println("Número de registros excluídos: " + exclusoes);
        } catch (SQLException e) {
            System.err.println("Erro ao excluir registro(s): " + e.getMessage());
        }
    }

    public static void excluirTarefaPorProjetoAssociado(Connection connection, String nomeProjeto) {
        StringBuilder query = new StringBuilder("DELETE tarefa FROM Tarefa " +
                "JOIN Projeto AS projeto ON tarefa.projetoId = projeto.projetoId " +
                "WHERE projeto.nomeProjeto = '" + nomeProjeto + "'");

        try {
            Statement statement = connection.createStatement();
            int exclusoes = statement.executeUpdate(query.toString());

            if (exclusoes > 0) {
                String updateProjeto = "UPDATE Projeto SET numTarefas = numTarefas - " + exclusoes +
                        " WHERE nomeProjeto = '" + nomeProjeto + "'";
                statement.executeUpdate(updateProjeto);
            }

            System.out.println("Número de tarefas excluídas: " + exclusoes);
        } catch (SQLException e) {
            System.err.println("Erro ao excluir registro(s): " + e.getMessage());
        }
    }

    public static void excluirTarefaPorStatusEProjeto(Connection connection, String nomeProjeto, int condicao) {
        StringBuilder query = new StringBuilder("DELETE FROM Tarefa WHERE projetoId = (SELECT projetoId FROM Projeto WHERE nomeProjeto = '" + nomeProjeto + "') AND "); // subconsulta

        if (condicao == 1) {
            query.append("status = 'Concluído'");
        } else if (condicao == 2) {
            query.append("status = 'Pendente'");
        }

        try {
            Statement statement = connection.createStatement();
            int exclusoes = statement.executeUpdate(query.toString());

            if (exclusoes > 0) {
                String updateProjeto = "UPDATE Projeto SET numTarefas = numTarefas - " + exclusoes +
                        " WHERE nomeProjeto = '" + nomeProjeto + "'";
                statement.executeUpdate(updateProjeto);
            }

            System.out.println("Número de tarefas excluídas: " + exclusoes);
        } catch (SQLException e) {
            System.err.println("Erro ao excluir tarefas: " + e.getMessage());
        }
    }

    public static void excluirColuna(Connection connection, String tabelaNome, String colunas) {
        StringBuilder query = new StringBuilder("ALTER TABLE " + tabelaNome + " ");
        String[] colunasArr;

        if (colunas != null && !colunas.isEmpty()) {
            colunasArr = colunas.split("\\s*,\\s*");
        } else {
            System.out.println("Nenhuma coluna foi fornecida.");
            return;
        }

        try {
            // Formata a query caso haja mais de uma coluna
            for (int i = 0; i < colunasArr.length; i++) {
                query.append("DROP COLUMN ").append(colunasArr[i]);
                if (i < colunasArr.length - 1) {
                    query.append(", ");
                }
            }

            Statement statement = connection.createStatement();
            System.out.println(query);

            statement.executeUpdate(query.toString());

            System.out.print("As coluna(s) ");
            for (int i = 0; i < colunasArr.length; i++) {
                System.out.print(colunasArr[i]);
                if (i < colunasArr.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(" foram excluídas da tabela " + tabelaNome + ".");
        } catch (SQLException e) {
            // Verifica se a coluna a ser excluída é uma FK
            if (e.getMessage().contains("foreign key constraint")) {
                System.out.println("Parece que uma coluna ou mais estão associadas à uma chave estrangeira");
                System.out.println("Você gostaria de excluir mesmo assim? (sim / não)");
                String escolha = dado.nextLine();
                if (escolha.toLowerCase().equals("sim")) {
                    excluirColunaForcadamente(connection, tabelaNome, colunasArr);
                }
            } else {
                System.err.println("Erro ao excluir a coluna: " + e.getMessage());
            }
        }
    }

    private static void excluirColunaForcadamente(Connection connection, String tabelaNome, String[] colunasArr) {
        try {
            for (String coluna : colunasArr) {
                String dropFk = "ALTER TABLE " + tabelaNome + " DROP FOREIGN KEY fk_" + coluna;
                Statement statement = connection.createStatement();
                statement.executeUpdate(dropFk);
                System.out.println("Chave estrangeira removida para a coluna " + coluna + ".");
            }
            excluirColuna(connection, tabelaNome, String.join(",", colunasArr));
        } catch (SQLException e) {
            System.err.println("Erro ao forçar exclusão da coluna: " + e.getMessage());
        }
    }


    public static void excluirTabela(Connection connection, String tabelaNome) {
        StringBuilder query = new StringBuilder("DROP TABLE " + tabelaNome);

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query.toString());
            System.out.println("Tabela " + tabelaNome + " foi excluída do banco");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir a tabela: " + e.getMessage());
        }
    }
}
