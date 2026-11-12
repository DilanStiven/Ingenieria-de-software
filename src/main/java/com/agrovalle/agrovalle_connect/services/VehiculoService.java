package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.dtos.VehiculoRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.VehiculoResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.PlacaVehiculoDuplicadaException;
import com.agrovalle.agrovalle_connect.exceptions.PropietarioNoEncontradoException;
import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.models.Transportista;
import com.agrovalle.agrovalle_connect.models.Vehiculo;
import com.agrovalle.agrovalle_connect.repositories.AgricultorRepository;
import com.agrovalle.agrovalle_connect.repositories.TransportistaRepository;
import com.agrovalle.agrovalle_connect.repositories.VehiculoRepository;
import com.agrovalle.agrovalle_connect.security.RolUsuario;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Lógica de negocio para el registro de vehículos de carga (HU-06).
 */
@Service
public class VehiculoService {

    private static final int LETRAS_EN_PLACA = 3;

    private final VehiculoRepository vehiculoRepository;
    private final AgricultorRepository agricultorRepository;
    private final TransportistaRepository transportistaRepository;

    /**
     * Crea el servicio con sus repositorios.
     *
     * @param vehiculos repositorio de vehículos
     * @param agricultores repositorio de agricultores
     * @param transportistas repositorio de transportistas
     */
    public VehiculoService(final VehiculoRepository vehiculos,
            final AgricultorRepository agricultores,
            final TransportistaRepository transportistas) {
        this.vehiculoRepository = vehiculos;
        this.agricultorRepository = agricultores;
        this.transportistaRepository = transportistas;
    }

    /**
     * Persiste un vehículo disponible asociado al usuario autenticado.
     *
     * @param dto datos validados del vehículo
     * @param propietarioId identificador del usuario autenticado
     * @param rol rol autenticado
     * @return vehículo creado
     */
    @Transactional
    public VehiculoResponseDTO registrar(final VehiculoRequestDTO dto,
            final Long propietarioId, final RolUsuario rol) {
        String placa = normalizarPlaca(dto.getPlaca());
        if (vehiculoRepository.existsByPlaca(placa)) {
            throw new PlacaVehiculoDuplicadaException(placa);
        }

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca(placa);
        vehiculo.setTipo(dto.getTipo().trim());
        vehiculo.setCapacidadKg(dto.getCapacidadKg());
        vehiculo.setEstado(Vehiculo.Estado.DISPONIBLE);

        if (rol == RolUsuario.AGRICULTOR) {
            Agricultor propietario = agricultorRepository.findById(propietarioId)
                    .orElseThrow(() -> new PropietarioNoEncontradoException(
                            "agricultor", propietarioId));
            vehiculo.setAgricultor(propietario);
        } else {
            Transportista propietario = transportistaRepository.findById(propietarioId)
                    .orElseThrow(() -> new PropietarioNoEncontradoException(
                            "transportista", propietarioId));
            vehiculo.setTransportista(propietario);
        }

        return VehiculoResponseDTO.desde(vehiculoRepository.save(vehiculo));
    }

    private String normalizarPlaca(final String placa) {
        String caracteres = placa.replace("-", "").toUpperCase(Locale.ROOT);
        return caracteres.substring(0, LETRAS_EN_PLACA) + "-"
                + caracteres.substring(LETRAS_EN_PLACA);
    }
}
