ALTER TABLE agricultor
    ADD COLUMN IF NOT EXISTS calificacion_promedio NUMERIC(3, 2)
    NOT NULL DEFAULT 0;

ALTER TABLE orden_logistica
    DROP CONSTRAINT IF EXISTS ck_orden_logistica_ruta_completa;

ALTER TABLE orden_logistica
    ADD CONSTRAINT ck_orden_logistica_ruta_completa CHECK (
        (estado IN ('PENDIENTE_ALISTAMIENTO', 'ALISTADO')
            AND vehiculo_id IS NULL AND fecha_salida IS NULL AND guia_despacho IS NULL)
        OR (estado IN ('EN_RUTA', 'ENTREGADA')
            AND vehiculo_id IS NOT NULL
            AND fecha_salida IS NOT NULL
            AND guia_despacho IS NOT NULL)
    );
