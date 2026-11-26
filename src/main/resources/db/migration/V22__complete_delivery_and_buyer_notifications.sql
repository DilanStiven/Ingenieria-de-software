ALTER TABLE orden_logistica
    DROP CONSTRAINT IF EXISTS ck_orden_logistica_ruta_completa;

ALTER TABLE orden_logistica
    ADD CONSTRAINT ck_orden_logistica_ruta_completa
    CHECK (
        (estado IN ('PENDIENTE_ALISTAMIENTO', 'ALISTADO')
            AND vehiculo_id IS NULL
            AND fecha_salida IS NULL
            AND guia_despacho IS NULL)
        OR
        (estado IN ('EN_RUTA', 'EN_DESTINO', 'ENTREGADO', 'ENTREGADA')
            AND vehiculo_id IS NOT NULL
            AND fecha_salida IS NOT NULL
            AND guia_despacho IS NOT NULL)
    );

ALTER TABLE checkpoint_viaje
    DROP CONSTRAINT IF EXISTS ck_checkpoint_viaje_estado;

ALTER TABLE checkpoint_viaje
    ADD CONSTRAINT ck_checkpoint_viaje_estado
    CHECK (estado IN ('EN_RUTA', 'EN_DESTINO', 'ENTREGADO', 'ENTREGADA'));

ALTER TABLE notificacion
    ADD COLUMN IF NOT EXISTS comprador_id BIGINT;

ALTER TABLE notificacion
    ALTER COLUMN agricultor_id DROP NOT NULL;

ALTER TABLE notificacion
    ADD CONSTRAINT ck_notificacion_destinatario
    CHECK (
        (agricultor_id IS NOT NULL AND comprador_id IS NULL)
        OR
        (agricultor_id IS NULL AND comprador_id IS NOT NULL)
    );

CREATE INDEX IF NOT EXISTS idx_notificacion_comprador_fecha
    ON notificacion (comprador_id, fecha_creacion);
