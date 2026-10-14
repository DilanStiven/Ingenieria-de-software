-- Add estado column to productos table if it doesn't exist
ALTER TABLE productos ADD COLUMN IF NOT EXISTS estado VARCHAR(20) DEFAULT 'activo';

-- Create composite index for filtering products by municipio, categoria, and estado
CREATE INDEX idx_productos_filtro ON productos (municipio, categoria, estado);
