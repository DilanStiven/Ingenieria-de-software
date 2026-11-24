package com.agrovalle.agrovalle_connect.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;

/**
 * Entidad que representa un agricultor registrado en AgroValle Connect.
 */
@Entity
@Table(name = "agricultor", uniqueConstraints = {
        @UniqueConstraint(name = "uk_agricultor_cedula", columnNames = "cedula")
})
public class Agricultor {

    private static final int CEDULA_LONGITUD_MAXIMA = 20;
    private static final int CORREO_LONGITUD_MAXIMA = 254;
    private static final int PASSWORD_HASH_LONGITUD_MAXIMA = 100;
    private static final int CALIFICACION_PRECISION = 3;
    private static final int CALIFICACION_ESCALA = 2;

    /**
     * Identificador único del agricultor.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del agricultor.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Ubicación del agricultor en el Valle.
     */
    @Column(name = "ubicacion_valle", nullable = false)
    private String ubicacionValle;

    /**
     * Número de cédula del agricultor.
     */
    @Column(nullable = false, length = CEDULA_LONGITUD_MAXIMA)
    private String cedula;

    /**
     * Correo normalizado para inicio de sesión.
     */
    @Column(length = CORREO_LONGITUD_MAXIMA, unique = true)
    private String correo;

    /**
     * Hash BCrypt de la contraseña; puede ser nulo en cuentas antiguas.
     */
    @Column(name = "password_hash", length = PASSWORD_HASH_LONGITUD_MAXIMA)
    private String passwordHash;

    @Column(name = "calificacion_promedio",
            precision = CALIFICACION_PRECISION, scale = CALIFICACION_ESCALA)
    private BigDecimal calificacionPromedio = BigDecimal.ZERO;

    /**
     * Obtiene el identificador del agricultor.
     *
     * @return identificador del agricultor
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del agricultor.
     *
     * @param idValue identificador del agricultor
     */
    public void setId(final Long idValue) {
        this.id = idValue;
    }

    /**
     * Obtiene el nombre del agricultor.
     *
     * @return nombre del agricultor
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del agricultor.
     *
     * @param nombreValue nombre del agricultor
     */
    public void setNombre(final String nombreValue) {
        this.nombre = nombreValue;
    }

    /**
     * Obtiene la ubicación del agricultor.
     *
     * @return ubicación del agricultor
     */
    public String getUbicacionValle() {
        return ubicacionValle;
    }

    /**
     * Establece la ubicación del agricultor.
     *
     * @param ubicacionValue ubicación del agricultor
     */
    public void setUbicacionValle(final String ubicacionValue) {
        this.ubicacionValle = ubicacionValue;
    }

    /**
     * Obtiene la cédula del agricultor.
     *
     * @return cédula del agricultor
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Establece la cédula del agricultor.
     *
     * @param cedulaValue cédula del agricultor
     */
    public void setCedula(final String cedulaValue) {
        this.cedula = cedulaValue;
    }

    /**
     * Obtiene el correo normalizado del agricultor.
     *
     * @return correo o null para cuentas antiguas sin credenciales
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo normalizado del agricultor.
     *
     * @param correoValue correo electrónico
     */
    public void setCorreo(final String correoValue) {
        this.correo = correoValue;
    }

    /**
     * Obtiene el hash BCrypt de la contraseña.
     *
     * @return hash o null para cuentas antiguas sin credenciales
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Establece el hash BCrypt de la contraseña.
     *
     * @param passwordHashValue hash de la contraseña
     */
    public void setPasswordHash(final String passwordHashValue) {
        this.passwordHash = passwordHashValue;
    }

    /**
     * Obtiene la calificación promedio actual del agricultor.
     *
     * @return promedio de estrellas
     */
    public BigDecimal getCalificacionPromedio() {
        return calificacionPromedio;
    }

    /**
     * Establece la calificación promedio recalculada.
     *
     * @param calificacionPromedioValue promedio de estrellas
     */
    public void setCalificacionPromedio(final BigDecimal calificacionPromedioValue) {
        this.calificacionPromedio = calificacionPromedioValue;
    }
}
