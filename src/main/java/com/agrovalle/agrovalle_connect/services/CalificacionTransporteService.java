package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.exceptions.CalificacionTransporteException;
import com.agrovalle.agrovalle_connect.exceptions.OrdenNoEncontradaException;
import com.agrovalle.agrovalle_connect.models.CalificacionTransporte;
import com.agrovalle.agrovalle_connect.models.OrdenLogistica;
import com.agrovalle.agrovalle_connect.models.Pedido;
import com.agrovalle.agrovalle_connect.models.Transportista;
import com.agrovalle.agrovalle_connect.models.Vehiculo;
import com.agrovalle.agrovalle_connect.repositories.CalificacionTransporteRepository;
import com.agrovalle.agrovalle_connect.repositories.OrdenLogisticaRepository;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Registra las métricas de transporte y las vincula al conductor del viaje.
 */
@Service
public class CalificacionTransporteService {

    private static final int PUNTUACION_MINIMA = 1;
    private static final int PUNTUACION_MAXIMA = 5;
    private static final int ESTADO_CAJAS_LONGITUD_MAXIMA = 30;

    private final OrdenLogisticaRepository ordenLogisticaRepository;
    private final CalificacionTransporteRepository calificacionRepository;

    /**
     * Crea el servicio con sus repositorios.
     *
     * @param ordenes repositorio de viajes/órdenes logísticas
     * @param calificaciones repositorio de calificaciones
     */
    public CalificacionTransporteService(
            final OrdenLogisticaRepository ordenes,
            final CalificacionTransporteRepository calificaciones) {
        this.ordenLogisticaRepository = ordenes;
        this.calificacionRepository = calificaciones;
    }

    /**
     * Guarda la calificación asociándola al conductor asignado al vehículo del viaje.
     *
     * @param viajeId identificador del viaje
     * @param compradorId identificador del comprador autenticado
     * @param puntualidad puntuación de puntualidad, de 1 a 5
     * @param estadoCajas estado reportado de las cajas
     * @return calificación persistida
     */
    @Transactional
    public CalificacionTransporte registrar(
            final Long viajeId,
            final Long compradorId,
            final Integer puntualidad,
            final String estadoCajas) {
        validarEntrada(viajeId, compradorId, puntualidad, estadoCajas);

        OrdenLogistica viaje = ordenLogisticaRepository.buscarPorIdParaActualizar(viajeId)
                .orElseThrow(() -> new OrdenNoEncontradaException(viajeId));
        Pedido pedido = viaje.getPedido();
        if (pedido == null || !Objects.equals(pedido.getCompradorId(), compradorId)) {
            throw new OrdenNoEncontradaException(viajeId);
        }
        if (viaje.getEstado() != OrdenLogistica.Estado.EN_RUTA) {
            throw new CalificacionTransporteException(
                    "Solo se puede calificar un viaje con ruta asignada",
                    HttpStatus.CONFLICT);
        }

        Vehiculo vehiculo = viaje.getVehiculo();
        Transportista conductor = vehiculo == null ? null : vehiculo.getTransportista();
        if (conductor == null) {
            throw new CalificacionTransporteException(
                    "El viaje no tiene un conductor transportista asignado",
                    HttpStatus.CONFLICT);
        }
        if (calificacionRepository.existsByViajeId(viajeId)) {
            throw new CalificacionTransporteException(
                    "El viaje ya cuenta con una calificación de transporte",
                    HttpStatus.CONFLICT);
        }

        CalificacionTransporte calificacion = new CalificacionTransporte();
        calificacion.setViaje(viaje);
        calificacion.setConductor(conductor);
        calificacion.setPuntualidad(puntualidad.shortValue());
        calificacion.setEstadoCajas(estadoCajas.trim());
        return calificacionRepository.save(calificacion);
    }

    private void validarEntrada(
            final Long viajeId,
            final Long compradorId,
            final Integer puntualidad,
            final String estadoCajas) {
        if (viajeId == null || viajeId <= 0) {
            throw new CalificacionTransporteException(
                    "El identificador del viaje debe ser positivo",
                    HttpStatus.BAD_REQUEST);
        }
        if (compradorId == null || compradorId <= 0) {
            throw new CalificacionTransporteException(
                    "El identificador del comprador debe ser positivo",
                    HttpStatus.BAD_REQUEST);
        }
        if (puntualidad == null
                || puntualidad < PUNTUACION_MINIMA
                || puntualidad > PUNTUACION_MAXIMA) {
            throw new CalificacionTransporteException(
                    "La puntualidad debe calificarse entre 1 y 5",
                    HttpStatus.BAD_REQUEST);
        }
        if (estadoCajas == null || estadoCajas.isBlank()
                || estadoCajas.trim().length() > ESTADO_CAJAS_LONGITUD_MAXIMA) {
            throw new CalificacionTransporteException(
                    "El estado de las cajas es obligatorio y no debe exceder 30 caracteres",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
