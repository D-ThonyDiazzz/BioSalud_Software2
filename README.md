# Sistema BioSalud - Arquitectura de Microservicios

Plataforma basada en microservicios desarrollada con Spring Boot para la gestión centralizada del core de negocio de un laboratorio clínico. El proyecto aplica los principios de **Arquitectura Hexagonal (Puertos y Adaptadores)** para garantizar un alto nivel de desacoplamiento, testabilidad y mantenibilidad.

## Arquitectura y Diseño

Cada microservicio ha sido refactorizado para cumplir con una separación estricta de responsabilidades:
* **Dominio Puro (`domain`):** Entidades y Objetos de Valor (Value Objects) completamente aislados de frameworks técnicos (JPA, Spring).
* **Aplicación (`application`):** Implementación de Casos de Uso (Services) y definición de Puertos (Interfaces de entrada y salida).
* **Infraestructura (`infrastructure`):** Implementación de Adaptadores (Repositorios JPA, Clientes REST Feign, Controladores) y Entidades de persistencia.

## Ecosistema de Microservicios (Core)

El sistema se compone de 7 microservicios interconectados que gestionan el flujo principal del laboratorio:

| Microservicio | Puerto | Base de Datos | Comunicación (Clientes Feign) |
|---|---|---|---|
| `msvc-pacientes` | 8001 | `msvc_pacientes` | `msvc-responsables` |
| `msvc-responsables` | 8002 | `msvc_responsables` | - |
| `msvc-analisis` | 8006 | `msvc_analisis` | - |
| `msvc-perfiles` | 8007 | `msvc_perfiles` | `msvc-analisis` |
| `msvc-ordenesatencion` | 8008 | `msvc_ordenesatencion` | `msvc-pacientes`, `msvc-analisis`, `msvc-perfiles` |
| `msvc-muestras` | 8009 | `msvc_muestras` | `msvc-ordenesatencion` |
| `msvc-resultados` | 8011 | `msvc_resultados` | `msvc-muestras` |

## Stack Tecnológico

* **Lenguaje:** Java 26
* **Framework Principal:** Spring Boot 3.x
* **Comunicación Síncrona:** Spring Cloud OpenFeign
* **Persistencia:** Spring Data JPA / Hibernate
* **Base de Datos:** MySQL (ddl-auto=update)
* **Gestor de Dependencias:** Maven

## Requisitos de Ejecución

Para desplegar el ecosistema en un entorno local, se requiere:
1. Tener instalado JDK 26 y configurado en las variables de entorno.
2. Contar con un servidor MySQL corriendo en `localhost:3306`.
3. Configurar las siguientes variables de entorno para la conexión a las bases de datos:
   * `DB_USERNAME` (ej. root)
   * `DB_PASSWORD` (clave del motor local)

**Comando de ejecución por módulo:**
```bash
mvn -pl <nombre-del-microservicio> spring-boot:run
