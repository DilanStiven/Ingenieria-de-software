ALTER TABLE pedido
    ADD COLUMN id_comprador BIGINT;

CREATE INDEX idx_pedido_comprador
    ON pedido (id_comprador);
