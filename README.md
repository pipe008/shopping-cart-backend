# ⚙️ Tienda Central - API Backend

Este es el núcleo de **Tienda Central**, una API REST robusta construida con **Spring Boot**. Se encarga de gestionar la lógica de negocio, la persistencia de datos en MongoDB y la comunicación con el frontend.

## 🚀 Tecnologías Utilizadas

* **Java 17+**: Lenguaje principal.
* **Spring Boot 3**: Framework para la creación de la API.
* **Spring Data MongoDB**: Para la interacción con la base de datos NoSQL.
* **MongoDB**: Base de datos documental para almacenar productos, carritos y pedidos.
* **Docker & Docker Compose**: Para la contenedorización y fácil despliegue de la base de datos.
* **Maven**: Gestor de dependencias y construcción del proyecto.

## 🏗️ Arquitectura del Proyecto

El proyecto sigue una estructura limpia organizada por módulos (Cart, Order, Product):
- **Controller**: Endpoints de la API.
- **Service**: Lógica de negocio.
- **Repository**: Consultas a la base de datos.
- **DTO**: Objetos de transferencia de datos.
- **Common/Exception**: Manejo global de errores.

## 🛠️ Instalación y Ejecución

Sigue estos pasos para levantar el servidor localmente:

### 1. Clonar el repositorio
```bash
git clone [https://github.com/pipe008/shopping-cart-backend.git](https://github.com/pipe008/shopping-cart-backend.git)
cd shopping-cart-backend
