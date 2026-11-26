package com.agrovalle.agrovalle_connect.events;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.agrovalle.agrovalle_connect.services.NotificacionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LlegadaDestinoListenerTest {

    @Mock
    private NotificacionService notificacionService;

    @InjectMocks
    private LlegadaDestinoListener listener;

    @Test
    void persisteElAvisoCuandoElEventoIncluyeAlComprador() {
        LlegadaDestinoEvent event =
                new LlegadaDestinoEvent(21L, 90L, 41L, 31L, "Cali");

        listener.notificarComprador(event);

        verify(notificacionService).registrarLlegadaComprador(event);
    }

    @Test
    void noIntentaNotificarSiElPedidoNoTieneComprador() {
        LlegadaDestinoEvent event = new LlegadaDestinoEvent(21L, "Cali");

        listener.notificarComprador(event);

        verifyNoInteractions(notificacionService);
    }
}
