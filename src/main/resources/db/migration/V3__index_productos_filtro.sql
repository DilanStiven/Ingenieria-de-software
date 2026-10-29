-- Keep compatibility with databases created before estado was part of V2.
ALTER TABLE productos ADD COLUMN IF NOT EXISTS estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO';

-- Create composite index for filtering products by municipio, categoria, and estado
CREATE INDEX IF NOT EXISTS idx_productos_filtro ON productos (municipio, categoria, estado);
