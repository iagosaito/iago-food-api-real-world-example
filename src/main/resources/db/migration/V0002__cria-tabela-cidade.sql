create TABLE cidade (
	id BIGINT not null auto_increment,
	nome_cidade varchar(80) not null,
	nome_estado varchar(80) not null,

	PRIMARY KEY(id)
) engine=InnoDB DEFAULT CHARSET=utf8