# LiterAlura



## Descripción

LiterAlura es una aplicación Java desarrollada con Spring Boot que consume la API pública de Gutendex para mostrar libros clásicos de dominio público. La app se conecta a una base de datos PostgreSQL donde guarda información de autores y libros, usando JPA/Hibernate para la gestión de datos.

---

## Tecnologías usadas

- Java 17+
- Spring Boot
- Spring Data JPA / Hibernate
- PostgreSQL
- API REST pública: [Gutendex](https://gutendex.com/)
- Jackson (para manejo de JSON)

---

## Características

- Conexión a base de datos PostgreSQL con creación automática de tablas a partir de entidades JPA.
- Consumo de la API Gutendex para obtener información de libros y autores.
- Manejo de conversiones JSON a objetos Java con `ObjectMapper`.
- Logs SQL activados para facilitar el debug.
- Variables de entorno para configuración de base de datos y conexión segura.


