package com.agrovalle.agrovalle_connect.dtos;

import java.nio.charset.StandardCharsets;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * Datos de entrada para registrar un agricultor.
 */
public class AgricultorRequestDTO {

    private static final int CORREO_LONGITUD_MAXIMA = 254;
    private static final int PASSWORD_LONGITUD_MINIMA = 8;
    private static final int PASSWORD_LONGITUD_MAXIMA = 72;
    private static final int PASSWORD_MAX_BYTES_BCRYPT = 72;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La ubicación en el Valle es obligatoria")
    private String ubicacionValle;

    @NotBlank(message = "La cédula es obligatoria")
    @Pattern(regexp = "\\d{6,10}", message = "La cédula debe tener entre 6 y 10 dígitos")
    private String cedula;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    @Size(max = CORREO_LONGITUD_MAXIMA, message = "El correo no puede superar 254 caracteres")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = PASSWORD_LONGITUD_MINIMA, max = PASSWORD_LONGITUD_MAXIMA,
            message = "La contraseña debe tener entre 8 y 72 caracteres")
    private String password;

    /**
     * Obtiene el nombre.
     *
     * @return nombre del agricultor
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el nombre.
     *
     * @param nombre nombre del agricultor
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la ubicación.
     *
     * @return ubicación en el Valle del Cauca
     */
    public String getUbicacionValle() {
        return ubicacionValle;
    }

    /**
     * Define la ubicación.
     *
     * @param ubicacionValle ubicación en el Valle del Cauca
     */
    public void setUbicacionValle(String ubicacionValle) {
        this.ubicacionValle = ubicacionValle;
    }

    /**
     * Obtiene la cédula.
     *
     * @return cédula del agricultor
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Define la cédula.
     *
     * @param cedula cédula del agricultor
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * Obtiene el correo electrónico.
     *
     * @return correo del agricultor
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Define el correo electrónico.
     *
     * @param correo correo del agricultor
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la contraseña recibida para el registro.
     *
     * @return contraseña en texto plano recibida en la solicitud
     */
    public String getPassword() {
        return password;
    }

    /**
     * Define la contraseña recibida para el registro.
     *
     * @param password contraseña en texto plano recibida en la solicitud
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Comprueba que BCrypt reciba la contraseña completa sin truncarla.
     *
     * @return true si la contraseña tiene hasta 72 bytes UTF-8
     */
    @AssertTrue(message = "La contraseña no puede superar 72 bytes en UTF-8")
    public boolean isPasswordWithinBcryptLimit() {
        return password == null
                || password.getBytes(StandardCharsets.UTF_8).length <= PASSWORD_MAX_BYTES_BCRYPT;
    }
}
