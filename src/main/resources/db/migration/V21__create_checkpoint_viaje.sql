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
        (estado IN ('EN_RUTA', 'EN_DESTINO', 'ENTREGADO')
         AND vehiculo_id IS NOT NULL
         AND fecha_salida IS NOT NULL
         AND guia_despacho IS NOT NULL)
    );

CREATE TABLE IF NOT EXISTS checkpoint_viaje (
    id BIGSERIAL PRIMARY KEY,
    orden_logistica_id BIGINT NOT NULL,
    estado VARCHAR(30) NOT NULL,
    ubicacion VARCHAR(100) NOT NULL,
    fecha_hora TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_checkpoint_viaje_orden
        FOREIGN KEY (orden_logistica_id)
        REFERENCES orden_logistica(id),
    CONSTRAINT ck_checkpoint_viaje_estado
        CHECK (estado IN ('EN_RUTA', 'EN_DESTINO', 'ENTREGADO'))
);

CREATE INDEX IF NOT EXISTS idx_checkpoint_viaje_orden_fecha
    ON checkpoint_viaje (orden_logistica_id, fecha_hora);
