create TABLE estado (
	id BIGINT not null auto_increment,
	nome varchar(80) not null,

	PRIMARY KEY(id)
) engine=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO estado (nome) SELECT DISTINCT nome_estado from cidade;

ALTER TABLE cidade ADD COLUMN estado_id BIGINT not NULL;

UPDATE cidade c SET c.estado_id = (SELECT e.id FROM estado e WHERE e.nome = c.nome_estado);
ALTER TABLE cidade add CONSTRAINT fk_cidade_estado FOREIGN KEY (estado_id) REFERENCES estado (id);

ALTER TABLE cidade DROP COLUMN nome_estado;

ALTER TABLE cidade CHANGE nome_cidade nome VARCHAR(80) NOT NULL;