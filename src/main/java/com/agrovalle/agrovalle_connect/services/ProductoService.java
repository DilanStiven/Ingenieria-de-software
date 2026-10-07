package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.dtos.ProductoDTO;
import com.agrovalle.agrovalle_connect.dtos.ProductoRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.ProductoResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.FechaCosechaInvalidaException;
import com.agrovalle.agrovalle_connect.exceptions.MunicipioNoValidoException;
import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.models.Producto;
import com.agrovalle.agrovalle_connect.repositories.AgricultorRepository;
import com.agrovalle.agrovalle_connect.repositories.ProductoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Lógica de negocio para publicación y consulta de productos agrícolas (HU-02, HU-04).
 */
@Service
public class ProductoService {

    /** Estado por defecto de un producto recién publicado. */
    private static final String ESTADO_ACTIVO = "ACTIVO";

    /** Municipios del Valle del Cauca soportados por la plataforma. */
    private static final Set<String> MUNICIPIOS_VALIDOS =
        Set.of("Dagua", "Palmira", "Buga", "Tulua", "Caicedonia", "Jamundi");

    private final ProductoRepository productoRepository;
    private final AgricultorRepository agricultorRepository;

    /**
     * Crea el servicio con sus dependencias.
     *
     * @param productoRepository  repositorio de productos
     * @param agricultorRepository repositorio de agricultores
     */
    public ProductoService(final ProductoRepository productoRepository,
            final AgricultorRepository agricultorRepository) {
        this.productoRepository = productoRepository;
        this.agricultorRepository = agricultorRepository;
    }

    /**
     * Publica una cosecha del agricultor autenticado (HU-02).
     * Valida que la fecha de cosecha no sea anterior a hoy.
     *
     * @param dto          datos del producto a publicar
     * @param agricultorId ID del agricultor obtenido del token JWT
     * @return respuesta con el ID del producto creado
     */
    @Transactional
    public ProductoResponseDTO publicar(final ProductoRequestDTO dto,
            final Long agricultorId) {
        if (dto.getFechaCosecha().isBefore(LocalDate.now())) {
            throw new FechaCosechaInvalidaException(
                "La fecha de cosecha no puede ser anterior a hoy");
        }
        Agricultor agricultor = agricultorRepository.findById(agricultorId)
            .orElseThrow(() ->
                new IllegalStateException("Agricultor no encontrado con ID: " + agricultorId));

        Producto producto = new Producto();
        producto.setTipo(dto.getTipo());
        producto.setCategoria(dto.getCategoria());
        producto.setMunicipio(agricultor.getUbicacionValle());
        producto.setCantidad(dto.getCantidad());
        producto.setPrecioUnitario(dto.getPrecioUnitario());
        producto.setFechaCosecha(dto.getFechaCosecha());
        producto.setEstado(ESTADO_ACTIVO);
        producto.setAgricultor(agricultor);

        Producto guardado = productoRepository.save(producto);
        return new ProductoResponseDTO(guardado.getId(), "Producto publicado exitosamente");
    }

    /**
     * Filtra el catálogo de productos activos por municipio y categoría (HU-04).
     *
     * @param municipio municipio de origen (obligatorio)
     * @param categoria categoría del producto (opcional)
     * @return lista de productos que coinciden con los filtros
     */
    @Transactional(readOnly = true)
    public List<ProductoDTO> filtrarProductos(final String municipio,
            final String categoria) {
        if (!MUNICIPIOS_VALIDOS.contains(municipio)) {
            throw new MunicipioNoValidoException("Municipio no válido: " + municipio);
        }
        List<Producto> resultado;
        if (categoria == null || categoria.isBlank()) {
            resultado = productoRepository.findByMunicipioAndEstado(municipio, ESTADO_ACTIVO);
        } else {
            resultado = productoRepository.findByMunicipioAndCategoriaAndEstado(
                municipio, categoria, ESTADO_ACTIVO);
        }
        return resultado.stream().map(ProductoDTO::desde).toList();
    }
}
