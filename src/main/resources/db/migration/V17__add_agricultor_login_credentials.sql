ALTER TABLE agricultor
    ADD COLUMN correo VARCHAR(254);

ALTER TABLE agricultor
    ADD COLUMN password_hash VARCHAR(100);

ALTER TABLE agricultor
    ADD CONSTRAINT uk_agricultor_correo UNIQUE (correo);
