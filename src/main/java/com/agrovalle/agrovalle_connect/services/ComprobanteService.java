package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.dtos.PedidoPagadoResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.PedidoNoEncontradoException;
import com.agrovalle.agrovalle_connect.models.OrdenCompra;
import com.agrovalle.agrovalle_connect.models.Pago;
import com.agrovalle.agrovalle_connect.models.Pedido;
import com.agrovalle.agrovalle_connect.repositories.OrdenCompraRepository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Consulta el historial de pedidos pagados y genera sus comprobantes PDF.
 */
@Service
public class ComprobanteService {

    private static final float MARGEN_IZQUIERDO = 50;
    private static final int ANCHO_LINEA_CARACTERES = 76;
    private static final float TAMANO_TITULO = 18;
    private static final float TAMANO_TEXTO = 12;
    private static final float POSICION_TITULO_Y = 740;
    private static final float POSICION_INICIAL_Y = 700;
    private static final float ESPACIADO_LINEA = 20;
    private static final int DECIMALES_MONTO = 2;
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    .withZone(ZoneId.of("America/Bogota"));

    private final OrdenCompraRepository ordenCompraRepository;

    public ComprobanteService(final OrdenCompraRepository ordenes) {
        this.ordenCompraRepository = ordenes;
    }

    /**
     * Obtiene las compras pagadas del usuario autenticado.
     *
     * @param compradorId identificador del comprador
     * @return historial ordenado de la más reciente a la más antigua
     */
    @Transactional(readOnly = true)
    public List<PedidoPagadoResponseDTO> obtenerHistorial(final Long compradorId) {
        return ordenCompraRepository.buscarHistorialPorComprador(compradorId).stream()
                .filter(this::esPagoConfirmado)
                .map(PedidoPagadoResponseDTO::new)
                .toList();
    }

    /**
     * Genera un comprobante únicamente para una compra pagada del usuario.
     *
     * @param pedidoId identificador del pedido
     * @param compradorId identificador del comprador
     * @return contenido binario del archivo PDF
     */
    @Transactional(readOnly = true)
    public byte[] generarComprobante(final Long pedidoId, final Long compradorId) {
        OrdenCompra orden = ordenCompraRepository
                .buscarPorPedidoYComprador(pedidoId, compradorId)
                .filter(this::esPagoConfirmado)
                .orElseThrow(() -> new PedidoNoEncontradoException(pedidoId));

        try (PDDocument documento = new PDDocument();
                ByteArrayOutputStream salida = new ByteArrayOutputStream()) {
            PDPage pagina = new PDPage(PDRectangle.LETTER);
            documento.addPage(pagina);
            try (PDPageContentStream contenido =
                    new PDPageContentStream(documento, pagina)) {
                escribirLinea(contenido,
                        new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD),
                        TAMANO_TITULO, POSICION_TITULO_Y, "COMPROBANTE DE PAGO");

                float posicionY = POSICION_INICIAL_Y;
                posicionY = escribirCampo(contenido, posicionY,
                        "Orden: " + orden.getNumeroOrden());
                posicionY = escribirCampo(contenido, posicionY,
                        "Pedido: " + orden.getPedido().getId());
                posicionY = escribirCampo(contenido, posicionY,
                        "Producto: " + orden.getPedido().getProducto().getNombre());
                posicionY = escribirCampo(contenido, posicionY,
                        "Categoría: " + orden.getPedido().getProducto().getCategoria());
                posicionY = escribirCampo(contenido, posicionY,
                        "Origen: " + orden.getPedido().getProducto().getMunicipio());
                posicionY = escribirCampo(contenido, posicionY,
                        "Cantidad: " + orden.getPedido().getCantidad().toPlainString() + " kg");
                posicionY = escribirCampo(contenido, posicionY,
                        "Precio unitario: COP "
                                + formatoMonto(orden.getPedido().getProducto().getPrecioUnitario())
                                + "/kg");
                posicionY = escribirCampo(contenido, posicionY,
                        "Monto pagado: COP " + formatoMonto(orden.getPago().getMonto()));
                posicionY = escribirCampo(contenido, posicionY,
                        "Medio de pago: " + orden.getPago().getMedioPago());
                escribirCampo(contenido, posicionY,
                        "Fecha de pago: " + FORMATO_FECHA.format(
                                orden.getPago().getFechaCreacion()));
            }
            documento.save(salida);
            return salida.toByteArray();
        } catch (IOException excepcion) {
            throw new IllegalStateException(
                    "No fue posible generar el comprobante del pedido " + pedidoId,
                    excepcion);
        }
    }

    private boolean esPagoConfirmado(final OrdenCompra orden) {
        return orden.getPedido().getEstado() == Pedido.Estado.PAGADO
                && orden.getPago().getEstado() == Pago.Estado.APROBADO;
    }

    private float escribirCampo(final PDPageContentStream contenido,
            final float posicionY, final String texto) throws IOException {
        float nuevaPosicionY = posicionY;
        for (String linea : ajustarLinea(texto)) {
            escribirLinea(contenido,
                    new PDType1Font(Standard14Fonts.FontName.HELVETICA),
                    TAMANO_TEXTO, nuevaPosicionY, linea);
            nuevaPosicionY -= ESPACIADO_LINEA;
        }
        return nuevaPosicionY;
    }

    private void escribirLinea(final PDPageContentStream contenido,
            final PDType1Font fuente, final float tamano, final float posicionY,
            final String texto) throws IOException {
        contenido.beginText();
        contenido.setFont(fuente, tamano);
        contenido.newLineAtOffset(MARGEN_IZQUIERDO, posicionY);
        contenido.showText(texto);
        contenido.endText();
    }

    private List<String> ajustarLinea(final String texto) {
        String[] palabras = texto.split("\\s+");
        ArrayList<String> lineas = new ArrayList<>();
        StringBuilder linea = new StringBuilder();
        for (String palabra : palabras) {
            if (linea.length() > 0
                    && linea.length() + palabra.length() + 1 > ANCHO_LINEA_CARACTERES) {
                lineas.add(linea.toString());
                linea.setLength(0);
            }
            if (linea.length() > 0) {
                linea.append(' ');
            }
            linea.append(palabra);
        }
        if (linea.length() > 0) {
            lineas.add(linea.toString());
        }
        return lineas;
    }

    private String formatoMonto(final BigDecimal monto) {
        return monto.setScale(DECIMALES_MONTO).toPlainString();
    }
}
