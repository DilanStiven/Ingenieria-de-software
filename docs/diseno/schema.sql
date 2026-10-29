-- =============================================================================
-- INSTITUCIÓN UNIVERSITARIA ANTONIO JOSÉ CAMACHO
-- Asignatura: Ingeniería de Software II - Proyecto AgroValle Connect
-- Artefacto: Esquema DDL y Persistencia Relacional (PostgreSQL 15/16)
-- Modelo Normalizado hasta Tercera Forma Normal (3FN)
-- =============================================================================

-- Limpieza preventiva de tablas con integridad referencial
DROP TABLE IF EXISTS oferta CASCADE;
DROP TABLE IF EXISTS cosecha CASCADE;
DROP TABLE IF EXISTS producto CASCADE;
DROP TABLE IF EXISTS agricultor CASCADE;
DROP TABLE IF EXISTS municipio CASCADE;

-- 1. Catálogo de Municipios del Valle del Cauca
CREATE TABLE municipio (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

-- 2. Entidad Agricultor (Productores locales)
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

-- 3. Catálogo de Productos Agrícolas
CREATE TABLE producto (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL
);

-- 4. Registro de Cosechas por Agricultor
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

-- 5. Publicación de Ofertas de Venta
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

-- Índices de consulta para optimizar búsquedas derivadas
CREATE INDEX idx_cosecha_fecha ON cosecha (fecha_cosecha);
CREATE INDEX idx_oferta_estado ON oferta (estado);
CREATE INDEX idx_agricultor_cedula ON agricultor (cedula);
