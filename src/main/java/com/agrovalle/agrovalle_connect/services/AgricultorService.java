package com.agrovalle.agrovalle_connect.services;

import org.springframework.stereotype.Service;

import com.agrovalle.agrovalle_connect.dtos.AgricultorRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.AgricultorResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.CedulaDuplicadaException;
import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.repositories.AgricultorRepository;

/**
 * Servicio encargado de gestionar los agricultores.
 */
@Service
public class AgricultorService {

    private final AgricultorRepository agricultorRepository;
    private final JwtService jwtService;

    /**
     * Crea el servicio con sus dependencias.
     *
     * @param agricultorRepository repositorio de agricultores
     * @param jwtService servicio de tokens
     */
    public AgricultorService(final AgricultorRepository agricultorRepository,
            final JwtService jwtService) {
        this.agricultorRepository = agricultorRepository;
        this.jwtService = jwtService;
    }

    /**
     * Registra un nuevo agricultor validando que la cédula no exista.
     *
     * @param dto datos del agricultor
     * @return agricultor registrado con su token de acceso
     */
    public AgricultorResponseDTO registrar(final AgricultorRequestDTO dto) {
        if (agricultorRepository.existsByCedula(dto.getCedula())) {
            throw new CedulaDuplicadaException(dto.getCedula());
        }
        Agricultor agricultor = new Agricultor();
        agricultor.setNombre(dto.getNombre());
        agricultor.setUbicacionValle(dto.getUbicacionValle());
        agricultor.setCedula(dto.getCedula());
        Agricultor guardado = agricultorRepository.save(agricultor);
        String token = jwtService.generarToken(guardado.getId());
        return new AgricultorResponseDTO(guardado.getId(), guardado.getNombre(),
        guardado.getUbicacionValle(), guardado.getCedula(), token);
    }
}
