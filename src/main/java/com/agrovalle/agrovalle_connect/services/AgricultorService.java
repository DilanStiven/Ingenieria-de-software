package com.agrovalle.agrovalle_connect.services;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Locale;

import com.agrovalle.agrovalle_connect.dtos.LoginRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.AgricultorRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.AgricultorResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.CedulaDuplicadaException;
import com.agrovalle.agrovalle_connect.exceptions.CorreoDuplicadoException;
import com.agrovalle.agrovalle_connect.exceptions.CredencialesInvalidasException;
import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.repositories.AgricultorRepository;

/**
 * Servicio encargado de gestionar los agricultores.
 */
@Service
public class AgricultorService {

    private final AgricultorRepository agricultorRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Crea el servicio con sus dependencias.
     *
     * @param agricultorRepository repositorio de agricultores
     * @param jwtService servicio de tokens
     * @param passwordEncoder codificador BCrypt de contraseñas
     */
    public AgricultorService(final AgricultorRepository agricultorRepository,
            final JwtService jwtService, final PasswordEncoder passwordEncoder) {
        this.agricultorRepository = agricultorRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
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
        String correo = normalizarCorreo(dto.getCorreo());
        if (agricultorRepository.existsByCorreoIgnoreCase(correo)) {
            throw new CorreoDuplicadoException();
        }
        Agricultor agricultor = new Agricultor();
        agricultor.setNombre(dto.getNombre());
        agricultor.setUbicacionValle(dto.getUbicacionValle());
        agricultor.setCedula(dto.getCedula());
        agricultor.setCorreo(correo);
        agricultor.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        Agricultor guardado = agricultorRepository.save(agricultor);
        String token = jwtService.generarToken(guardado.getId());
        return new AgricultorResponseDTO(guardado.getId(), guardado.getNombre(),
        guardado.getUbicacionValle(), guardado.getCedula(), token);
    }

    /**
     * Autentica al agricultor y emite un token JWT de acceso.
     *
     * @param dto correo y contraseña
     * @return datos del agricultor y token de acceso
     */
    public AgricultorResponseDTO autenticar(final LoginRequestDTO dto) {
        String correo = normalizarCorreo(dto.getCorreo());
        Agricultor agricultor = agricultorRepository.findByCorreoIgnoreCase(correo)
                .filter(cuenta -> cuenta.getPasswordHash() != null
                        && passwordEncoder.matches(dto.getPassword(), cuenta.getPasswordHash()))
                .orElseThrow(CredencialesInvalidasException::new);
        String token = jwtService.generarToken(agricultor.getId());
        return new AgricultorResponseDTO(agricultor.getId(), agricultor.getNombre(),
                agricultor.getUbicacionValle(), agricultor.getCedula(), token);
    }

    private String normalizarCorreo(final String correo) {
        return correo.trim().toLowerCase(Locale.ROOT);
    }
}
