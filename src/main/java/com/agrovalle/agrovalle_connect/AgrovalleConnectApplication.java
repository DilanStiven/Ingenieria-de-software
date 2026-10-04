package com.agrovalle.agrovalle_connect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación AgroValle Connect.
 */
@SpringBootApplication
public class AgrovalleConnectApplication {

    /**
     * Constructor privado para evitar instancias innecesarias.
     */
    private AgrovalleConnectApplication() {
    }

    /**
     * Punto de entrada de la aplicación.
     *
     * @param args argumentos de la línea de comandos
     */
    public static void main(final String[] args) {
        SpringApplication.run(AgrovalleConnectApplication.class, args);
    }
}
