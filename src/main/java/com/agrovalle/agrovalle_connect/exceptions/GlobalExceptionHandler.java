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
     * Maneja consultas que contienen un municipio fuera del catálogo permitido.
     *
     * @param ex excepción lanzada por el servicio
     * @return respuesta HTTP 400 con el mensaje de error
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
     * @return respuesta HTTP 400 con el detalle de cada campo
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidacion(
            final MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e ->
                errores.put(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }
}
