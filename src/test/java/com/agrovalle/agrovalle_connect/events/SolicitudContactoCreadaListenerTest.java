package com.agrovalle.agrovalle_connect.events;

import static org.mockito.Mockito.verify;

import com.agrovalle.agrovalle_connect.services.NotificacionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SolicitudContactoCreadaListenerTest {

    @Mock
    private NotificacionService notificacionService;

    @Test
    void convierteElEventoDeContactoEnUnaNotificacionPersistida() {
        SolicitudContactoCreadaListener listener =
                new SolicitudContactoCreadaListener(notificacionService);
        SolicitudContactoCreadaEvent evento =
                new SolicitudContactoCreadaEvent(11L, 7L, 17L, "Mensaje de prueba");

        listener.notificarAgricultor(evento);

        verify(notificacionService).registrarSolicitudContacto(evento);
    }
}
