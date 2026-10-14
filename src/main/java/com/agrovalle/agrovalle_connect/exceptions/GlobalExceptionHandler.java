package com.agrovalle.agrovalle_connect.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones de la API.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja el caso de cédula duplicada.
     *
     * @param ex excepción lanzada por el servicio
     * @return respuesta HTTP 409 con el mensaje de error
     */
    @ExceptionHandler(CedulaDuplicadaException.class)
    public ResponseEntity<Map<String, String>> manejarCedulaDuplicada(
            final CedulaDuplicadaException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Maneja fechas de cosecha inválidas (anteriores a hoy).
     *
     * @param ex excepción de fecha inválida
     * @return respuesta HTTP 400 Bad Request
     */
    @ExceptionHandler(FechaCosechaInvalidaException.class)
    public ResponseEntity<Map<String, String>> manejarFechaInvalida(
            final FechaCosechaInvalidaException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Maneja municipios fuera del área soportada por la plataforma.
     *
     * @param ex excepción de municipio no válido
     * @return respuesta HTTP 400 Bad Request
     */
    @ExceptionHandler(MunicipioNoValidoException.class)
    public ResponseEntity<Map<String, String>> manejarMunicipioNoValido(
            final MunicipioNoValidoException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Maneja errores de validación de campos (Bean Validation).
     *
     * @param ex excepción con los errores de validación
     * @return respuesta HTTP 400 con el detalle de cada campo y la clave "error"
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidacion(
            final MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        // Asigna el primer mensaje de error a la clave "error" para cumplir con las
        // pruebas
        String primerMensaje = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(e -> e.getDefaultMessage())
                .orElse("Error de validación en los campos");
        errores.put("error", primerMensaje);

        // Agrega los errores detallados por cada campo
        ex.getBindingResult().getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

}

