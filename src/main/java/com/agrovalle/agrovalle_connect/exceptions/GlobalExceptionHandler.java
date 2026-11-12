package com.agrovalle.agrovalle_connect.exceptions;

import java.util.HashMap;
import java.util.Map;

import jakarta.validation.ConstraintViolationException;
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
     * Maneja placas de vehículo duplicadas.
     *
     * @param ex excepción lanzada por el servicio
     * @return respuesta HTTP 409 con el mensaje de error
     */
    @ExceptionHandler(PlacaVehiculoDuplicadaException.class)
    public ResponseEntity<Map<String, String>> manejarPlacaDuplicada(
            final PlacaVehiculoDuplicadaException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Maneja propietarios inexistentes asociados al token JWT.
     *
     * @param ex excepción lanzada por el servicio
     * @return respuesta HTTP 404 con el mensaje de error
     */
    @ExceptionHandler(PropietarioNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> manejarPropietarioNoEncontrado(
            final PropietarioNoEncontradoException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Maneja parámetros de solicitud inválidos.
     *
     * @param ex excepción de validación
     * @return respuesta HTTP 400 con el detalle
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> manejarRestriccionDeParametro(
            final ConstraintViolationException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getConstraintViolations().stream()
                .findFirst()
                .map(violation -> violation.getMessage())
                .orElse("Parámetro de solicitud inválido"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Maneja productos sin transacciones recientes.
     *
     * @param ex excepción lanzada por el servicio
     * @return respuesta HTTP 404 con el mensaje de error
     */
    @ExceptionHandler(PrecioNoDisponibleException.class)
    public ResponseEntity<Map<String, String>> manejarPrecioNoDisponible(
            final PrecioNoDisponibleException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Maneja órdenes logísticas inexistentes.
     *
     * @param ex excepción lanzada por el servicio
     * @return respuesta HTTP 404
     */
    @ExceptionHandler(OrdenNoEncontradaException.class)
    public ResponseEntity<Map<String, String>> manejarOrdenNoEncontrada(
            final OrdenNoEncontradaException ex) {
        return respuestaDeError(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja vehículos inexistentes.
     *
     * @param ex excepción lanzada por el servicio
     * @return respuesta HTTP 404
     */
    @ExceptionHandler(VehiculoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> manejarVehiculoNoEncontrado(
            final VehiculoNoEncontradoException ex) {
        return respuestaDeError(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja reglas y autorizaciones de operaciones logísticas.
     *
     * @param ex error de negocio con estado HTTP explícito
     * @return respuesta con el estado y mensaje correspondientes
     */
    @ExceptionHandler(OperacionRutaException.class)
    public ResponseEntity<Map<String, String>> manejarOperacionRuta(
            final OperacionRutaException ex) {
        return respuestaDeError(ex.getMessage(), ex.getStatus());
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

    private ResponseEntity<Map<String, String>> respuestaDeError(
            final String mensaje, final HttpStatus status) {
        Map<String, String> error = new HashMap<>();
        error.put("error", mensaje);
        return ResponseEntity.status(status).body(error);
    }

}

