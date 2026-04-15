# ⚙️ Historias de Usuario - Backend (API)

Este documento detalla los requerimientos técnicos y funcionales del core de **Tienda Central**.

---

## HU-06: Exponer catálogo de productos por API
**Como** sistema frontend,  
**quiero** consumir una API de productos,  
**para** mostrar el catálogo al usuario final.

### Criterios de Aceptación:
* [ ] Endpoint `GET` para listar todos los productos.
* [ ] Endpoint `GET` para consultar un producto específico por su ID.
* [ ] La API responde exclusivamente en formato JSON.
* [ ] Los objetos de respuesta incluyen campos de disponibilidad y stock real.

---

## HU-07: Administrar productos desde backend
**Como** administrador,  
**quiero** crear, editar y desactivar productos,  
**para** mantener actualizado el catálogo.

### Criterios de Aceptación:
* [ ] Endpoint `POST` para la creación de nuevos productos.
* [ ] Endpoint `PUT/PATCH` para editar nombre, precio, categoría y stock.
* [ ] Implementación de "Borrado Lógico": endpoint para desactivar productos sin eliminarlos de la base de datos.

---

## HU-08: Gestionar carrito por usuario
**Como** sistema,  
**quiero** almacenar un carrito por usuario,  
**para** conservar los productos seleccionados durante la compra.

### Criterios de Aceptación:
* [ ] Persistencia de un carrito asociado a un ID de usuario o sesión.
* [ ] Operaciones CRUD para los ítems del carrito.
* [ ] El objeto carrito almacena automáticamente el total y la última fecha de actualización.

---

## HU-09: Procesar checkout y generar pedido
**Como** cliente,  
**quiero** que el sistema procese mi compra,  
**para** convertir mi carrito en un pedido confirmado.

### Criterios de Aceptación:
* [ ] Validación estricta de stock en la base de datos antes de confirmar el pedido.
* [ ] Creación del registro de Pedido (Order) si hay disponibilidad.
* [ ] Manejo de excepciones: si no hay stock, se devuelve un error 400 con un mensaje claro.
* [ ] Al confirmar con éxito, el carrito asociado debe quedar vacío.

---

## HU-10: Enrutar peticiones mediante API Gateway
**Como** sistema,  
**quiero** centralizar las peticiones mediante un gateway,  
**para** tener un único punto de entrada al backend distribuido.

### Criterios de Aceptación:
* [ ] Configuración de Spring Cloud Gateway para exponer rutas de productos, carrito y pedidos.
* [ ] Redirección correcta de peticiones al microservicio correspondiente.
* [ ] Estandarización de respuestas de error a través del Gateway.
