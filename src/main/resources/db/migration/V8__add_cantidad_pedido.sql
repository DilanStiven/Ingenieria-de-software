ALTER TABLE pedido
    ADD COLUMN cantidad NUMERIC(10, 2);

-- Antes de guardar la cantidad, los pagos heredados cobraban un único precio unitario.
UPDATE pedido
SET cantidad = 1.00;

ALTER TABLE pedido
    ALTER COLUMN cantidad SET NOT NULL;

ALTER TABLE pedido
    ADD CONSTRAINT ck_pedido_cantidad_positiva CHECK (cantidad > 0);
