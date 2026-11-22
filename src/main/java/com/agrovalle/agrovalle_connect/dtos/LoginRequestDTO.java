package com.agrovalle.agrovalle_connect.dtos;

import java.nio.charset.StandardCharsets;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Credenciales para iniciar sesión como agricultor.
 */
public class LoginRequestDTO {

    private static final int CORREO_LONGITUD_MAXIMA = 254;
    private static final int PASSWORD_LONGITUD_MAXIMA = 72;
    private static final int PASSWORD_MAX_BYTES_BCRYPT = 72;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    @Size(max = CORREO_LONGITUD_MAXIMA, message = "El correo no puede superar 254 caracteres")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(max = PASSWORD_LONGITUD_MAXIMA, message = "La contraseña no puede superar 72 caracteres")
    private String password;

    /**
     * Obtiene el correo de inicio de sesión.
     *
     * @return correo del agricultor
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Define el correo de inicio de sesión.
     *
     * @param correo correo del agricultor
     */
    public void setCorreo(final String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la contraseña de inicio de sesión.
     *
     * @return contraseña recibida en la solicitud
     */
    public String getPassword() {
        return password;
    }

    /**
     * Define la contraseña de inicio de sesión.
     *
     * @param password contraseña recibida en la solicitud
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Comprueba que BCrypt compare la contraseña completa.
     *
     * @return true si la contraseña tiene hasta 72 bytes UTF-8
     */
    @AssertTrue(message = "La contraseña no puede superar 72 bytes en UTF-8")
    public boolean isPasswordWithinBcryptLimit() {
        return password == null
                || password.getBytes(StandardCharsets.UTF_8).length <= PASSWORD_MAX_BYTES_BCRYPT;
    }
}
