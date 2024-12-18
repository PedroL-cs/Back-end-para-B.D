-- Métodos de consulta

-- Consultar tabela por status
SELECT * FROM Projeto WHERE status = 'Concluído';
SELECT * FROM Projeto WHERE status = 'Pendente';
SELECT * FROM Tarefa WHERE status = 'Concluído';
SELECT * FROM Tarefa WHERE status = 'Pendente';

-- Consultar tabela por data
SELECT * FROM Projeto WHERE dataInicio = 'YYYY/MM/DD';
SELECT * FROM Projeto WHERE dataFim = 'YYYY/MM/DD';

-- Consultar tabela pelo número de pessoas
SELECT * FROM Projeto WHERE numPessoas = ?;
SELECT * FROM Tarefa WHERE numPessoas = ?;

-- Consultar projetos pelo número de tarefas
SELECT * FROM Projeto WHERE numTarefas = ?;

-- Consultar tarefa por projeto associado
SELECT tarefa.tarefaId, tarefa.nomeTarefa, tarefa.descricaoTarefa, tarefa.status, tarefa.projetoId
FROM tabela JOIN Projeto ON tarefa.projetoId = projeto.projetoId
WHERE projeto.projetoId = ?;

-- Consultar tarefa por projeto associado e status
SELECT tarefa.tarefaId, tarefa.nomeTarefa, tarefa.descricaoTarefa, tarefa.status, tarefa.projetoId FROM tarefa
JOIN Projeto AS projeto ON tarefa.projetoId = projeto.projetoId
WHERE projeto.projetoId = ? AND tarefa.status = '?';

-- Pesquisar nome de tabela
SELECT nomeProjeto FROM Projeto WHERE tabelaId = ?;
SELECT nomeTarefa FROM Tarefa WHERE tarefaId = ?;

-- Pesquisar descrição de tabela
SELECT descricaoProjeto FROM Projeto WHERE projetoId = ?;
SELECT descricaoTarefa FROM Tarefa WHERE tarefaId = ?;

-- Pesquisar status de tabela
SELECT status FROM Projeto WHERE projetoId = ?;
SELECT status FROM Tarefa WHERE tarefaId = ?;

-- Pesquisar número de pessoas na tabela
SELECT numPessoas FROM Projeto WHERE projetoId = ?;
SELECT numPessoas FROM Tarefa WHERE tarefaId = ?;

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
UPDATE Projeto SET nomeProjeto = '?' WHERE projetoId = ?;
UPDATE Tarefa SET nomeTarefa = '?' WHERE tarefaId = ?;

-- Editar descrição de um registro
UPDATE Projeto SET descricaoProjeto = '?' WHERE projetoId = ?;
UPDATE Tarefa SET descricaoTarefa = '?' WHERE tarefaId = ?;

-- Editar status de um registro
UPDATE Projeto SET status = 'Concluído' WHERE projetoId = ?;
UPDATE Projeto SET status = 'Pendente' WHERE projetoId = ?;
UPDATE Tarefa SET status = 'Concluído' WHERE tarefaId = ?;
UPDATE Tarefa SET status = 'Pendente' WHERE tarefaId = ?;

-- Editar número de pessoas de um registro
UPDATE Projeto SET numPessoas = ? WHERE projetoId = ?;
UPDATE Tarefa SET numPessoas = ? WHERE tarefaid = ?;

-- Editar número de tarefas de um projeto
UPDATE Projeto SET numTarefas = ? WHERE projetoId = ?;

-- Métodos de exclusão

-- Excluir registro por status
DELETE FROM Projeto WHERE status = 'Concluído';
DELETE FROM Projeto WHERE status = 'Pendente';
DELETE FROM Tarefa WHERE status = 'Concluído';
DELETE FROM Tarefa WHERE status = 'Pendente';

-- Excluir projeto por data de conclusão
DELETE FROM Projeto WHERE dataFim = '?';

-- Excluir registro por Id
DELETE FROM Projeto WHERE projetoId = ?;
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