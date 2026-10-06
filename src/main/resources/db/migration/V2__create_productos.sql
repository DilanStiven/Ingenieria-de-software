CREATE TABLE productos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    municipio VARCHAR(100) NOT NULL,
    cantidad DECIMAL(10,2) NOT NULL,
    fecha_cosecha DATE NOT NULL,
    precio_unitario DECIMAL(12,2) NOT NULL,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_productos_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);