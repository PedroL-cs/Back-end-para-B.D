-- Métodos de consulta

-- Consultar tabela por status
SELECT * FROM tabela WHERE status = 'Concluído';
SELECT * FROM tabela WHERE status = 'Pendente';

-- Consultar tabela por data
SELECT * FROM tabela WHERE dataInicio = 'YYYY/MM/DD';
SELECT * FROM tabela WHERE dataFim = 'YYYY/MM/DD';

-- Consultar tabela pelo número de pessoas
SELECT * FROM tabela WHERE numPessoas = ?;

-- Consultar projetos pelo número de tarefas
SELECT * FROM Projeto WHERE numTarefas = ?;

-- Consultar tarefa por projeto associado
SELECT tarefa.tarefaId, tarefa.nomeTarefa, tarefa.descricaoTarefa, tarefa.status, tarefa.projetoId
FROM tabela JOIN Projeto ON tarefa.projetoId = projeto.projetoId
WHERE projeto.projetoId = ?;

-- Consultar tarefa por projeto associado e status
SELECT tarefa.tarefaId, tarefa.nomeTarefa, tarefa.descricaoTarefa, tarefa.status, tarefa.projetoId FROM tabela
JOIN Projeto AS projeto ON tarefa.projetoId = projeto.projetoId
WHERE projeto.projetoId = ?;

-- Pesquisar nome de tabela
SELECT nomeTabela FROM Tabela WHERE tabelaId = ?;

-- Pesquisar descrição de tabela
SELECT descricaoTabela FROM tabela WHERE tabelaId = ?;

-- Pesquisar status de tabela
SELECT status FROM tabela WHERE tabelaId = ?;

-- Pesquisar número de pessoas na tabela
SELECT numPessoas FROM tabela WHERE tabelaID = ?;

-- Pesquisar número de tarefas no projeto
SELECT numTarefas FROM Projeto WHERE projetoId = ?;

-- Pesquisar data de projeto
SELECT dataInicio FROM Projeto WHERE projetoId = ?;
SELECT dataFim FROM Projeto WHERE projetoId = ?;

-- Métodos de inserção

-- Adicionar projeto
INSERT INTO Projeto (nomeProjeto, descricao, dataInicio, numPessoas) VALUES ('?', '?', '?', ?);

-- Adicionar tarefa
INSERT INTO Tarefa (nomeTarefa, descricaoTarefa, projetoId) VALUES ('?', '?', ?);

-- Métodos de edição

-- Editar nome de um registro
UPDATE tabela SET nomeTabela = '?' WHERE tabelaId = ?;

-- Editar descrição de um registro
UPDATE tabela SET descricaoTabela = '?' WHERE tabelaId = ?;

-- Editar status de um registro
UPDATE tabela SET status = 'Concluído' WHERE tabelaId = ?;
UPDATE tabela SET status = 'Pendente' WHERE tabelaId = ?;

-- Editar número de pessoas de um registro
UPDATE tabela SET numPessoas = ? WHERE tabelaId = ?;

-- Editar número de tarefas de um projeto
UPDATE Projeto SET numTarefas = ? WHERE projetoId = ?;

-- Métodos de exclusão

-- Excluir registro por status
DELETE FROM tabela WHERE status = 'Concluído';
DELETE FROM tabela WHERE status = 'Pendente';

-- Excluir projeto por data de conclusão
DELETE FROM Projeto WHERE dataFim = '?';

-- Excluir registro por Id
DELETE FROM tabela WHERE tabelaId = ?;
DELETE FROM Tarefa WHERE tarefaId IN (?);

-- Excluir tarefa por projeto associado
DELETE Tarefa FROM Tarefa
JOIN Projeto ON tarefa.projetoId = Projeto.projetoId
WHERE projeto.projetoId = ?;

-- Excluir tarefa por status e projeto associado
DELETE FROM Tarefa WHERE projetoId = ? AND status = '?';

-- Estrutura de tabelas

create table Projeto (
	projetoId int auto_increment not null,
    nomeProjeto varchar(100) not null,
    descricaoProjeto text not null,
    status ENUM('Pendente', 'Concluído') not null,
    numPessoas int not null,
    numTarefas int not null,
    dataInicio date not null,
    dataFim date,
    primary key (projetoId)
);

create table Tarefa (
	tarefaId int auto_increment not null,
    nomeTarefa varchar(50) not null,
    descricaoTarefa text not null,
    status ENUM('Pendente', 'Concluído'),
    numPessoas int not null,
    projetoId int not null,
    primary key (tarefaId),
    foreign key (projetoId) references Projeto (projetoId)
);