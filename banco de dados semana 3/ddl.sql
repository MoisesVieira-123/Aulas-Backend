drop table aluno
drop table curso
drop table professor
drop table matricula



create table aluno (
    id int primary key,
    nome varchar(100) not null unique,
    idade int check(idade>=0),
    RA varchar(20) not null unique
);

create table curso(
    id int primary key,
    nome_curso varchar(100) not null unique
);

create table professor(
	nome_professor varchar(100) not null unique,
	id int primary key
);

create table matricula (
    id_matricula int primary key,
    id_aluno int,
    id_curso int,
    id_professor int,
    data_matricula date default current_date,

    foreign key (id_aluno) references aluno(id),
    foreign key (id_curso) references curso(id),
    foreign key (id_professor) references professor(id)
);



insert into aluno (id, nome, idade, RA) values
(1, 'João', 20, 'RA001'),
(2, 'Maria', 21, 'RA002'),
(3, 'Pedro', 19, 'RA003');

insert into curso (id, nome_curso) values
(1, 'Engenharia da Computação'),
(2, 'ADS'),
(3, 'Desenvolvimento de Sistemas');

insert into professor(id, nome_professor) values 
(1, 'Antonio Marques'),
(2, 'José ferreira');

insert into matricula (id_matricula, id_aluno, id_curso, id_professor) values
(1, 3, 1, 1),
(2, 1, 2, 2),
(3, 2, 3, 2);

delete from matricula


select 
	aluno.nome,
	curso.nome_curso,
	aluno.RA,
	professor.nome_professor
from matricula
join aluno
	on matricula.id_aluno = aluno.id 
join curso
	on matricula.id_curso = curso.id
join professor
	on matricula.id_professor = professor.id;
	

show data_directory;