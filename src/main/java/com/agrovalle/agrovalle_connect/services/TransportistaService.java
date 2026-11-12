package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.dtos.TransportistaRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.TransportistaResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.CedulaDuplicadaException;
import com.agrovalle.agrovalle_connect.models.Transportista;
import com.agrovalle.agrovalle_connect.repositories.AgricultorRepository;
import com.agrovalle.agrovalle_connect.repositories.TransportistaRepository;
import com.agrovalle.agrovalle_connect.security.RolUsuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio para registrar transportistas y emitir sus tokens de acceso.
 */
@Service
public class TransportistaService {

    private final AgricultorRepository agricultorRepository;
    private final TransportistaRepository transportistaRepository;
    private final JwtService jwtService;

    /**
     * Crea el servicio con sus dependencias.
     *
     * @param agricultores repositorio de agricultores
     * @param transportistas repositorio de transportistas
     * @param tokens servicio de JWT
     */
    public TransportistaService(final AgricultorRepository agricultores,
            final TransportistaRepository transportistas, final JwtService tokens) {
        this.agricultorRepository = agricultores;
        this.transportistaRepository = transportistas;
        this.jwtService = tokens;
    }

    /**
     * Registra un transportista con una cédula no usada en la plataforma.
     *
     * @param dto datos del transportista
     * @return respuesta con el token emitido
     */
    @Transactional
    public TransportistaResponseDTO registrar(final TransportistaRequestDTO dto) {
        if (agricultorRepository.existsByCedula(dto.getCedula())
                || transportistaRepository.existsByCedula(dto.getCedula())) {
            throw new CedulaDuplicadaException(dto.getCedula());
        }

        Transportista transportista = new Transportista();
        transportista.setNombre(dto.getNombre().trim());
        transportista.setCedula(dto.getCedula());
        Transportista guardado = transportistaRepository.save(transportista);
        String token = jwtService.generarToken(guardado.getId(), RolUsuario.TRANSPORTISTA);
        return new TransportistaResponseDTO(guardado.getId(), guardado.getNombre(),
                guardado.getCedula(), token);
    }
}
