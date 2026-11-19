package com.agrovalle.agrovalle_connect.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.agrovalle.agrovalle_connect.dtos.ContactoMensajeResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.GlobalExceptionHandler;
import com.agrovalle.agrovalle_connect.security.RolUsuario;
import com.agrovalle.agrovalle_connect.security.SecurityConfig;
import com.agrovalle.agrovalle_connect.services.ContactoService;
import com.agrovalle.agrovalle_connect.services.JwtService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ContactoController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
class ContactoControllerTest {

    private static final String URL = "/api/v1/contacto/mensaje";
    private static final String TOKEN = "token-valido";
    private static final Long PRODUCTO_ID = 7L;
    private static final BigDecimal CANTIDAD = new BigDecimal("5.0");
    private static final String MENSAJE = "Me interesa el producto";
    private static final String DESTINO = "Cali";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ContactoService contactoService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void solicitaContactoConJwtYRespondeOk() throws Exception {
        when(jwtService.esValido(TOKEN)).thenReturn(true);
        when(jwtService.obtenerUsuarioId(TOKEN)).thenReturn(17L);
        when(jwtService.obtenerRol(TOKEN)).thenReturn(RolUsuario.AGRICULTOR);
        when(contactoService.enviarMensaje(PRODUCTO_ID, CANTIDAD, MENSAJE, DESTINO, 17L))
                .thenReturn(new ContactoMensajeResponseDTO(
                        13L, 11L, "Solicitud confirmada", true));

        mockMvc.perform(post(URL)
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id_producto": 7,
                                  "cantidad": 5.0,
                                  "mensaje": "Me interesa el producto",
                                  "destino": "Cali"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idMensaje").value(13))
                .andExpect(jsonPath("$.idPedido").value(11))
                .andExpect(jsonPath("$.mensaje").value("Solicitud confirmada"));

        verify(contactoService)
                .enviarMensaje(PRODUCTO_ID, CANTIDAD, MENSAJE, DESTINO, 17L);
    }

    @Test
    void rechazaSolicitudSinJwt() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id_producto": 7,
                                  "mensaje": "Me interesa el producto"
                                }
                                """))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(contactoService);
    }

    @Test
    void rechazaDatosInvalidos() throws Exception {
        when(jwtService.esValido(TOKEN)).thenReturn(true);
        when(jwtService.obtenerUsuarioId(TOKEN)).thenReturn(17L);
        when(jwtService.obtenerRol(TOKEN)).thenReturn(RolUsuario.AGRICULTOR);

        mockMvc.perform(post(URL)
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id_producto": 0,
                                  "cantidad": 0,
                                  "mensaje": " ",
                                  "destino": "Cali"
                                }
                                """))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(contactoService);
    }
}
