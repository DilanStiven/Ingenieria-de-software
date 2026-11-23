package com.agrovalle.agrovalle_connect;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = {
            "spring.datasource.url=jdbc:h2:mem:hu11_boot;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
            "spring.datasource.username=sa",
            "spring.datasource.password=",
            "spring.datasource.driver-class-name=org.h2.Driver"
        })
class BilleteraFlywayApplicationTest {

    @Autowired
    private Flyway flyway;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void configuraFlywayYAplicaLaMigracionDeBilleteraAlIniciar() {
        assertEquals("19", flyway.info().current().getVersion().getVersion());
        Integer billeteras = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables "
                        + "WHERE table_schema = 'PUBLIC' AND table_name = 'BILLETERA'",
                Integer.class);
        Integer notificaciones = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables "
                        + "WHERE table_schema = 'PUBLIC' AND table_name = 'NOTIFICACION'",
                Integer.class);
        Integer ordenesLogisticas = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables "
                        + "WHERE table_schema = 'PUBLIC' AND table_name = 'ORDEN_LOGISTICA'",
                Integer.class);
        Integer resenas = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables "
                        + "WHERE table_schema = 'PUBLIC' AND table_name = 'RESENA'",
                Integer.class);

        assertEquals(1, billeteras);
        assertEquals(1, notificaciones);
        assertEquals(1, ordenesLogisticas);
        assertEquals(1, resenas);
    }
}
