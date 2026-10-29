-- Esquema del modelo académico propuesto de cinco entidades.
-- No corresponde a las entidades JPA ni a la base actual de AgroValle Connect.
-- Ejecutar únicamente en una base vacía creada para evaluar esta propuesta.

CREATE TABLE municipio (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE agricultor (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    correo VARCHAR(120) NOT NULL UNIQUE,
    cedula VARCHAR(20) NOT NULL UNIQUE,
    municipio_id INT NOT NULL,
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_agricultor_municipio
        FOREIGN KEY (municipio_id)
        REFERENCES municipio (id)
        ON DELETE RESTRICT
);

CREATE TABLE producto (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL
);

CREATE TABLE cosecha (
    id BIGSERIAL PRIMARY KEY,
    agricultor_id BIGINT NOT NULL,
    producto_id INT NOT NULL,
    cantidad_kg NUMERIC(10, 2) NOT NULL CHECK (cantidad_kg > 0),
    fecha_cosecha DATE NOT NULL,
    CONSTRAINT fk_cosecha_agricultor
        FOREIGN KEY (agricultor_id)
        REFERENCES agricultor (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_cosecha_producto
        FOREIGN KEY (producto_id)
        REFERENCES producto (id)
        ON DELETE RESTRICT
);

CREATE TABLE oferta (
    id BIGSERIAL PRIMARY KEY,
    cosecha_id BIGINT NOT NULL,
    precio_kg NUMERIC(12, 2) NOT NULL CHECK (precio_kg >= 0),
    cantidad_disponible NUMERIC(10, 2) NOT NULL CHECK (cantidad_disponible >= 0),
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVA',
    fecha_publicacion DATE NOT NULL DEFAULT CURRENT_DATE,
    CONSTRAINT fk_oferta_cosecha
        FOREIGN KEY (cosecha_id)
        REFERENCES cosecha (id)
        ON DELETE CASCADE
);

CREATE INDEX idx_cosecha_fecha ON cosecha (fecha_cosecha);
CREATE INDEX idx_oferta_estado ON oferta (estado);
