# Product Backlog — AgroValle Connect

Estimación realizada mediante **Planning Poker** (escala de Fibonacci: 1, 2, 3, 5, 8, 13) y priorización **MoSCoW** (M = Must Have, S = Should Have, C = Could Have, W = Won't Have).

| ID | Historia de Usuario | MoSCoW | Story Points |
|----|----|:---:|:---:|
| HU-01 | Registro de Agricultores | M | 5 |
| HU-02 | Publicación de Productos | M | 5 |
| HU-03 | Visualización de Precios Regionales | S | 3 |
| HU-04 | Filtro de Categorías y Municipios | M | 3 |
| HU-05 | Contacto Directo / Intención de Compra | S | 3 |
| HU-06 | Registro de Vehículos de Carga | M | 3 |
| HU-07 | Programación y Asignación de Ruta | M | 5 |
| HU-08 | Actualización de Estado de Entrega (Checkpoints) | S | 5 |
| HU-09 | Pago de Pedido con Pasarela Virtual (PSE/Tarjetas) | M | 8 |
| HU-10 | Generación de Comprobante de Pago y Factura | S | 3 |
| HU-11 | Visualización de Saldo (Billetera del Agricultor) | M | 5 |
| HU-12 | Notificación de Pago Rechazado | C | 2 |
| HU-13 | Calificación del Producto y del Agricultor | M | 3 |
| HU-14 | Calificación del Servicio de Transporte | S | 3 |
| HU-15 | Visualización de Reputación Promedio del Productor | M | 3 |

**Total estimado: 58 Story Points**

---

## Detalle por Historia de Usuario (Given-When-Then)

### HU-01: Registro de Agricultores
**Historia:** Como Agricultor, quiero registrarme en la plataforma para ofrecer mis productos.
**Priorización:** Must Have — **Estimación:** 5 SP
```gherkin
Given que el usuario ingresa a /api/v1/auth/register
When envía un JSON con nombre, ubicacion_valle y cedula válida
Then el sistema responde con status 201 Created y el registro persiste en PostgreSQL
```

### HU-02: Publicación de Productos
**Historia:** Como Agricultor, quiero publicar mis cosechas para que sean visibles.
**Priorización:** Must Have — **Estimación:** 5 SP
```gherkin
Given un agricultor autenticado con token JWT
When publica un producto con tipo, cantidad y fecha_cosecha
Then el sistema valida que la fecha no sea anterior a hoy y retorna un ID de producto único
```

### HU-03: Visualización de Precios Regionales
**Historia:** Como Usuario, quiero ver los precios promedio del Valle para negociar mejor.
**Priorización:** Should Have — **Estimación:** 3 SP
```gherkin
Given que existen 50 transacciones de "Café" en las últimas 24 horas
When solicito el precio promedio de "Café"
Then el sistema calcula la media aritmética y despliega el valor exacto en pesos colombianos
```

### HU-04: Filtro de Categorías y Municipios
**Historia:** Como Comprador, quiero filtrar las cosechas por municipio (Dagua, Palmira, Buga) y categoría, para encontrar productos locales de mi interés rápidamente.
**Priorización:** Must Have — **Estimación:** 3 SP
```gherkin
Given que existen productos registrados en PostgreSQL bajo el municipio "Dagua" y categoría "Frutas"
When el usuario realiza una petición GET a /api/v1/productos?municipio=Dagua&categoria=Frutas
Then el sistema responde con status 200 OK y un arreglo JSON con las ofertas activas correspondientes
```

### HU-05: Contacto Directo / Intención de Compra
**Historia:** Como Comprador, quiero enviar una solicitud de contacto directo al agricultor, para acordar condiciones de compra y logística.
**Priorización:** Should Have — **Estimación:** 3 SP
```gherkin
Given un comprador autenticado con token JWT y una oferta activa registrada con id_producto
When envía una petición POST a /api/v1/contacto/mensaje con el id_producto y el mensaje de negociación
Then el sistema persiste la interacción en PostgreSQL y retorna status 200 OK con la confirmación de notificación enviada
```

### HU-06: Registro de Vehículos de Carga
**Historia:** Como agricultor o transportista, quiero registrar los vehículos disponibles (placa, tipo de camión y capacidad en kg), para tener una flota lista al momento de despachar los pedidos.
**Priorización:** Must Have — **Estimación:** 3 SP
```gherkin
Given que el usuario tiene rol de transportista o agricultor
When ingresa los datos de su camión (placa "ABC-123", capacidad "1500kg") y guarda
Then el sistema registra el vehículo y lo deja disponible para futuras programaciones de rutas
```

### HU-07: Programación y Asignación de Ruta
**Historia:** Como encargado de logística, quiero asignar un pedido preparado a un vehículo específico y fijar la fecha de salida, para organizar el despacho hacia Cali.
**Priorización:** Must Have — **Estimación:** 5 SP
```gherkin
Given que existe una orden en estado "Alistado"
When el usuario selecciona un vehículo registrado y confirma la fecha de salida
Then el pedido cambia a estado "En Ruta" y genera la guía de despacho
```

### HU-08: Actualización de Estado de Entrega (Checkpoints)
**Historia:** Como conductor, quiero actualizar el estado del viaje desde mi celular cuando llego a puntos clave o entrego la carga, para que el comprador sepa dónde está su mercancía.
**Priorización:** Should Have — **Estimación:** 5 SP
```gherkin
Given que el conductor tiene un viaje "En Ruta" asignado
When presiona el botón "Llegada al destino" en la plataforma
Then el sistema actualiza la ubicación y notifica al comprador que su pedido está por ser entregado
```

### HU-09: Pago de Pedido con Pasarela Virtual (PSE/Tarjetas)
**Historia:** Como comerciante, quiero pagar mi carrito de compras utilizando una pasarela de pagos, para asegurar mi pedido de forma rápida y sin usar efectivo.
**Priorización:** Must Have — **Estimación:** 8 SP
```gherkin
Given que el comerciante confirmó su carrito y está en la pantalla de pago
When ingresa una tarjeta de crédito válida o aprueba la transacción por PSE
Then el sistema descuenta el dinero, confirma el pago exitoso y genera la orden de compra oficial
```

### HU-10: Generación de Comprobante de Pago y Factura
**Historia:** Como comerciante, quiero poder descargar un comprobante digital en PDF de mi transacción, para llevar el control contable de mi negocio.
**Priorización:** Should Have — **Estimación:** 3 SP
```gherkin
Given que el sistema procesó un pago exitosamente
When el comerciante accede al historial de sus pedidos pagados y selecciona "Descargar Recibo"
Then el sistema genera y descarga un archivo PDF con los detalles de la compra y el monto pagado
```

### HU-11: Visualización de Saldo (Billetera del Agricultor)
**Historia:** Como agricultor, quiero ver el saldo acumulado de las ventas que ya entregué, para poder solicitar el desembolso del dinero a mi cuenta bancaria.
**Priorización:** Must Have — **Estimación:** 5 SP
```gherkin
Given que el agricultor ha completado entregas pagadas con éxito
When ingresa a la sección "Mi Billetera" en su perfil
Then el sistema suma y muestra el saldo total disponible para retirar
```

### HU-12: Notificación de Pago Rechazado
**Historia:** Como comerciante, quiero recibir una alerta si mi método de pago falla, para poder intentar con otra tarjeta sin perder los productos que había reservado.
**Priorización:** Could Have — **Estimación:** 2 SP
```gherkin
Given que el comerciante intenta pagar pero la tarjeta no tiene fondos
When la pasarela de pagos retorna un error de transacción
Then el sistema muestra un mensaje de "Pago Rechazado" y mantiene el carrito activo durante 15 minutos para reintentar
```

### HU-13: Calificación del Producto y del Agricultor
**Historia:** Como comerciante, quiero calificar de 1 a 5 estrellas la calidad de la cosecha y dejar un comentario, para evaluar al agricultor que me vendió.
**Priorización:** Must Have — **Estimación:** 3 SP
```gherkin
Given que una orden de compra fue marcada como "Entregada"
When el comerciante asigna 5 estrellas y escribe "Excelente tomate"
Then el sistema guarda la reseña y actualiza el perfil del agricultor
```

### HU-14: Calificación del Servicio de Transporte
**Historia:** Como comerciante, quiero calificar de forma independiente al transportista (puntualidad, estado de las cajas), para garantizar que la logística se mantenga con altos estándares.
**Priorización:** Should Have — **Estimación:** 3 SP
```gherkin
Given que el comerciante está llenando el formulario de recepción de pedido
When evalúa con 4 estrellas la puntualidad del camión
Then el sistema asocia esta métrica de desempeño al conductor que realizó el viaje
```

### HU-15: Visualización de Reputación Promedio del Productor
**Historia:** Como comerciante, quiero ver la calificación promedio de un agricultor antes de comprarle, para sentir confianza en la calidad de los productos que voy a adquirir.
**Priorización:** Must Have — **Estimación:** 3 SP
```gherkin
Given que un agricultor tiene 10 ventas calificadas previamente
When un comprador visita el perfil del agricultor o ve su lote en el catálogo
Then el sistema calcula y muestra visualmente el promedio (ej. "4.8 Estrellas")
```

---

## Auditoría INVEST (Trabajo Independiente)

Antes de entregar, revisen cada historia contra los criterios **INVEST**:

- [ ] **I**ndependiente — ¿se puede desarrollar sin depender de otra HU inconclusa?
- [ ] **N**egociable — ¿el detalle puede ajustarse en conversación con el equipo/PO?
- [ ] **V**aliosa — ¿aporta valor claro a un rol (agricultor, comerciante, transportista)?
- [ ] **E**stimable — ¿el equipo pudo asignarle Story Points con confianza?
- [ ] **S**mall (Pequeña) — ¿cabe dentro de un sprint?
- [ ] **T**estable — ¿el Given-When-Then permite verificar el cumplimiento sin ambigüedad?
