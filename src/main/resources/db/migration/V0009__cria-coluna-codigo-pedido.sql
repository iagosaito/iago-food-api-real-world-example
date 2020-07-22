ALTER TABLE pedido add codigo VARCHAR(36) NOT NULL AFTER id;
UPDATE pedido SET codigo = UUID();

ALTER TABLE pedido ADD CONSTRAINT uk_pedido_codigo UNIQUE(codigo)