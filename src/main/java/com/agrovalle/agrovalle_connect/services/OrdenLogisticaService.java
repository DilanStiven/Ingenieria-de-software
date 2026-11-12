package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.dtos.AsignacionRutaRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.OrdenLogisticaRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.OrdenLogisticaResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.OperacionRutaException;
import com.agrovalle.agrovalle_connect.exceptions.OrdenNoEncontradaException;
import com.agrovalle.agrovalle_connect.exceptions.PropietarioNoEncontradoException;
import com.agrovalle.agrovalle_connect.exceptions.VehiculoNoEncontradoException;
import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.models.OrdenLogistica;
import com.agrovalle.agrovalle_connect.models.Vehiculo;
import com.agrovalle.agrovalle_connect.repositories.AgricultorRepository;
import com.agrovalle.agrovalle_connect.repositories.OrdenLogisticaRepository;
import com.agrovalle.agrovalle_connect.repositories.VehiculoRepository;
import com.agrovalle.agrovalle_connect.security.RolUsuario;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Lógica de creación de órdenes logísticas y asignación de rutas (HU-07).
 */
@Service
public class OrdenLogisticaService {

    private static final String GUIA_PREFIX = "GV-";
    private static final String ROL_AGRICULTOR = "agricultor";
    private static final String ROL_TRANSPORTISTA = "transportista";

    private final OrdenLogisticaRepository ordenRepository;
    private final AgricultorRepository agricultorRepository;
    private final VehiculoRepository vehiculoRepository;

    /**
     * Crea el servicio con sus repositorios.
     *
     * @param ordenes repositorio de órdenes
     * @param agricultores repositorio de agricultores
     * @param vehiculos repositorio de vehículos
     */
    public OrdenLogisticaService(final OrdenLogisticaRepository ordenes,
            final AgricultorRepository agricultores,
            final VehiculoRepository vehiculos) {
        this.ordenRepository = ordenes;
        this.agricultorRepository = agricultores;
        this.vehiculoRepository = vehiculos;
    }

    /**
     * Crea una orden logística pendiente de alistamiento.
     *
     * @param dto datos de la carga
     * @param agricultorId agricultor autenticado
     * @return orden creada
     */
    @Transactional
    public OrdenLogisticaResponseDTO crear(final OrdenLogisticaRequestDTO dto,
            final Long agricultorId) {
        Agricultor agricultor = agricultorRepository.findById(agricultorId)
                .orElseThrow(() -> new PropietarioNoEncontradoException(
                        ROL_AGRICULTOR, agricultorId));

        OrdenLogistica orden = new OrdenLogistica();
        orden.setProducto(dto.getProducto().trim());
        orden.setPesoKg(dto.getPesoKg());
        orden.setDestino(dto.getDestino().trim());
        orden.setEstado(OrdenLogistica.Estado.PENDIENTE_ALISTAMIENTO);
        orden.setFechaCreacion(Instant.now());
        orden.setAgricultor(agricultor);
        return new OrdenLogisticaResponseDTO(ordenRepository.save(orden));
    }

    /**
     * Marca una orden del agricultor como alistada.
     *
     * @param ordenId identificador de la orden
     * @param agricultorId agricultor autenticado
     * @return orden actualizada
     */
    @Transactional
    public OrdenLogisticaResponseDTO marcarAlistada(
            final Long ordenId, final Long agricultorId) {
        OrdenLogistica orden = buscarOrdenBloqueada(ordenId);
        validarPropietario(orden, agricultorId);
        if (orden.getEstado() != OrdenLogistica.Estado.PENDIENTE_ALISTAMIENTO) {
            throw new OperacionRutaException(
                    "Solo una orden pendiente de alistamiento puede marcarse como alistada",
                    HttpStatus.CONFLICT);
        }

        orden.setEstado(OrdenLogistica.Estado.ALISTADO);
        return new OrdenLogisticaResponseDTO(ordenRepository.save(orden));
    }

    /**
     * Asigna una orden alistada a un vehículo y programa su salida.
     *
     * @param ordenId identificador de la orden
     * @param dto datos de asignación
     * @param usuarioId usuario autenticado
     * @param rol rol del usuario autenticado
     * @return orden en ruta con guía de despacho
     */
    @Transactional
    public OrdenLogisticaResponseDTO asignarRuta(final Long ordenId,
            final AsignacionRutaRequestDTO dto, final Long usuarioId,
            final RolUsuario rol) {
        OrdenLogistica orden = buscarOrdenBloqueada(ordenId);
        if (orden.getEstado() != OrdenLogistica.Estado.ALISTADO) {
            throw new OperacionRutaException(
                    "Solo se puede asignar una ruta a una orden en estado ALISTADO",
                    HttpStatus.CONFLICT);
        }

        validarFechaSalida(dto.getFechaSalida());
        Vehiculo vehiculo = vehiculoRepository.buscarPorIdParaActualizar(dto.getVehiculoId())
                .orElseThrow(() -> new VehiculoNoEncontradoException(dto.getVehiculoId()));
        validarAccesoYDisponibilidad(vehiculo, orden, usuarioId, rol);
        validarCapacidad(vehiculo, orden);

        vehiculo.setEstado(Vehiculo.Estado.EN_RUTA);
        orden.setVehiculo(vehiculo);
        orden.setFechaSalida(dto.getFechaSalida());
        orden.setGuiaDespacho(generarGuiaDespacho());
        orden.setEstado(OrdenLogistica.Estado.EN_RUTA);
        vehiculoRepository.save(vehiculo);
        return new OrdenLogisticaResponseDTO(ordenRepository.save(orden));
    }

    /**
     * Lista órdenes propias del agricultor o disponibles para los transportistas.
     *
     * @param usuarioId usuario autenticado
     * @param rol rol autenticado
     * @return órdenes visibles para el usuario
     */
    @Transactional(readOnly = true)
    public List<OrdenLogisticaResponseDTO> listar(final Long usuarioId, final RolUsuario rol) {
        List<OrdenLogistica> ordenes;
        if (rol == RolUsuario.AGRICULTOR) {
            ordenes = ordenRepository.buscarPorAgricultor(usuarioId);
        } else {
            ordenes = ordenRepository.findByEstadoOrderByFechaCreacionAsc(
                    OrdenLogistica.Estado.ALISTADO);
        }
        return ordenes.stream().map(OrdenLogisticaResponseDTO::new).toList();
    }

    /**
     * Obtiene una orden visible para su agricultor o para el transportista asignado.
     *
     * @param ordenId identificador de la orden
     * @param usuarioId usuario autenticado
     * @param rol rol autenticado
     * @return orden consultada
     */
    @Transactional(readOnly = true)
    public OrdenLogisticaResponseDTO obtener(final Long ordenId,
            final Long usuarioId, final RolUsuario rol) {
        OrdenLogistica orden = ordenRepository.findById(ordenId)
                .orElseThrow(() -> new OrdenNoEncontradaException(ordenId));
        if (rol == RolUsuario.AGRICULTOR) {
            validarPropietario(orden, usuarioId);
        } else if (orden.getVehiculo() == null
                || orden.getVehiculo().getTransportista() == null
                || !orden.getVehiculo().getTransportista().getId().equals(usuarioId)) {
            throw new OperacionRutaException(
                    "El transportista solo puede consultar órdenes asignadas a su vehículo",
                    HttpStatus.FORBIDDEN);
        }
        return new OrdenLogisticaResponseDTO(orden);
    }

    private OrdenLogistica buscarOrdenBloqueada(final Long ordenId) {
        return ordenRepository.buscarPorIdParaActualizar(ordenId)
                .orElseThrow(() -> new OrdenNoEncontradaException(ordenId));
    }

    private void validarPropietario(final OrdenLogistica orden, final Long agricultorId) {
        if (!orden.getAgricultor().getId().equals(agricultorId)) {
            throw new OperacionRutaException(
                    "La orden no pertenece al agricultor autenticado", HttpStatus.FORBIDDEN);
        }
    }

    private void validarFechaSalida(final LocalDate fechaSalida) {
        if (fechaSalida.isBefore(LocalDate.now())) {
            throw new OperacionRutaException(
                    "La fecha de salida no puede ser anterior a hoy", HttpStatus.BAD_REQUEST);
        }
    }

    private void validarAccesoYDisponibilidad(final Vehiculo vehiculo,
            final OrdenLogistica orden, final Long usuarioId, final RolUsuario rol) {
        if (vehiculo.getEstado() != Vehiculo.Estado.DISPONIBLE) {
            throw new OperacionRutaException(
                    "El vehículo no está disponible para una nueva ruta", HttpStatus.CONFLICT);
        }
        if (rol == RolUsuario.AGRICULTOR) {
            validarPropietario(orden, usuarioId);
        } else if (vehiculo.getTransportista() == null
                || !vehiculo.getTransportista().getId().equals(usuarioId)) {
            throw new OperacionRutaException(
                    "El transportista solo puede asignar sus propios vehículos",
                    HttpStatus.FORBIDDEN);
        }
    }

    private void validarCapacidad(final Vehiculo vehiculo, final OrdenLogistica orden) {
        BigDecimal capacidad = BigDecimal.valueOf(vehiculo.getCapacidadKg());
        if (orden.getPesoKg().compareTo(capacidad) > 0) {
            throw new OperacionRutaException(
                    "La carga supera la capacidad del vehículo", HttpStatus.CONFLICT);
        }
    }

    private String generarGuiaDespacho() {
        return GUIA_PREFIX + UUID.randomUUID().toString().toUpperCase(Locale.ROOT);
    }
}
