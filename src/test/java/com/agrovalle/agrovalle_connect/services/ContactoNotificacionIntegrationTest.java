package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.agrovalle.agrovalle_connect.dtos.ContactoMensajeResponseDTO;
import com.agrovalle.agrovalle_connect.dtos.NotificacionResponseDTO;
import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.models.Producto;
import com.agrovalle.agrovalle_connect.repositories.AgricultorRepository;
import com.agrovalle.agrovalle_connect.repositories.ProductoRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = {
            "spring.datasource.url=jdbc:h2:mem:hu11_boot;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
            "spring.datasource.username=sa",
            "spring.datasource.password=",
            "spring.datasource.driver-class-name=org.h2.Driver"
        })
class ContactoNotificacionIntegrationTest {

    @Autowired
    private AgricultorRepository agricultorRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ContactoService contactoService;

    @Autowired
    private NotificacionService notificacionService;

    @Test
    void persisteLaNotificacionYLaExponeEnLaBandejaDelAgricultor() {
        Agricultor agricultor = new Agricultor();
        agricultor.setNombre("Agricultor de prueba");
        agricultor.setUbicacionValle("Buga");
        agricultor.setCedula("55555555");
        Agricultor agricultorGuardado = agricultorRepository.saveAndFlush(agricultor);

        Producto producto = new Producto();
        producto.setNombre("Maíz dulce");
        producto.setCategoria("Granos");
        producto.setMunicipio("Buga");
        producto.setCantidad(new BigDecimal("10.00"));
        producto.setPrecioUnitario(new BigDecimal("2500.00"));
        producto.setFechaCosecha(LocalDate.now());
        producto.setEstado("ACTIVO");
        producto.setAgricultor(agricultorGuardado);
        Producto productoGuardado = productoRepository.saveAndFlush(producto);

        ContactoMensajeResponseDTO confirmacion = contactoService.enviarMensaje(
                productoGuardado.getId(),
                new BigDecimal("2.00"),
                "Solicito información de disponibilidad y negociar la entrega",
                "Cali",
                999L);

        List<NotificacionResponseDTO> notificaciones =
                notificacionService.listarPorAgricultor(agricultorGuardado.getId());

        assertTrue(confirmacion.isNotificacionEnviada());
        assertEquals(1, notificaciones.size());
        assertEquals(confirmacion.getIdPedido(), notificaciones.get(0).getIdPedido());
        assertEquals(productoGuardado.getId(), notificaciones.get(0).getIdProducto());
        assertTrue(notificaciones.get(0).getMensaje().contains("Nueva solicitud de compra"));
        assertTrue(notificaciones.get(0).getMensaje().contains(
                "Solicito información de disponibilidad y negociar la entrega"));
    }
}
