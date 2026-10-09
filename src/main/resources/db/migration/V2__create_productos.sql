-- V2__create_productos.sql
-- Tabla de productos (cosechas) publicados por los agricultores
CREATE TABLE productos (
  id              BIGSERIAL      PRIMARY KEY,
  nombre          VARCHAR(100)   NOT NULL,
  categoria       VARCHAR(50)    NOT NULL,
  municipio       VARCHAR(50)    NOT NULL,
  cantidad        NUMERIC(10, 2) NOT NULL CHECK (cantidad > 0),
  precio_unitario NUMERIC(12, 2) NOT NULL CHECK (precio_unitario >= 0),
  fecha_cosecha   DATE           NOT NULL,
  estado          VARCHAR(20)    NOT NULL DEFAULT 'ACTIVO',
  agricultor_id   BIGINT         NOT NULL REFERENCES usuarios_productores (id)
);
