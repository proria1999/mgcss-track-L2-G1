# Historial de Releases - MGCSS Track

## 🚀 Release v1.0.0 - First Stable Release
### Descripción Funcional
Esta entrega permite gestionar todo el ciclo de vida de una solicitud, tecnico y cliente cambio a través de servicios REST desacoplados del modelo de dominio interno gracias al uso de DTOs.

### Cambios Relevantes
* **API REST e Integración (Sesión 10):**
  * Controlador REST (`SolicitudController`) exponiendo la raíz `/api/solicitudes`.
  * Endpoints mínimos obligatorios implementados (`POST`, `GET`, `PUT`, `PATCH`).
  * Documentación automática con OpenAPI/Swagger UI.
* **Containerización y Despliegue (Sesión 11):**
  * `Dockerfile` ligero basado en `eclipse-temurin:17-jre-alpine`.

---

## 🔧 Release v1.1.0 - Análisis de Versionado Semántico

Para la determinación de esta nueva versión, se realiza el análisis de impacto de los últimos commits tomando como base la versión v1.0.0 y aplicando el formato obligatorio exigido:

**MAJOR** → cambios incompatibles con versión previa 
**MINOR** → nuevas funcionalidades compatibles 
**PATCH** → correcciones

### 1. Justificación del Salto de Versión (v1.0.0 → v1.1.0)

**¿Por qué NO es 2.0.0 (MAJOR)?** No se aplica debido a que no se ha introducido ningún cambio incompatible con la versión previa. Toda la API de solicitudes (`v1.0.0`) sigue funcionando exactamente igual sin romper los contratos existentes de los clientes.
**¿Por qué NO es 1.0.1 (PATCH)?** No se aplica porque los últimos commits no representan correcciones de errores o *bugfixes* sobre el código existente.
**¿Por qué SÍ es 1.1.0 (MINOR)?** Se incrementa la versión a **MINOR** porque se han incorporado **nuevas funcionalidades compatibles**.Se expande el dominio del sistema añadiendo un nuevo controlador completo para la creación de técnicos y mejoras en la configuración de entorno.

### 2. Descripción de los Nuevos Cambios (Changelog v1.1.0)

**Extensión de la API REST (MINOR):**
Implementación del nuevo controlador `TecnicoController` y la capa de negocio `TecnicoService` para permitir la creación de técnicos en el sistema.
Inclusión de anotaciones OpenAPI/Swagger en el nuevo endpoint para mantener la documentación actualizada y unificada.
**Configuración del Entorno de Despliegue (MINOR):**
Adición explícita de `server.port=8080` en `application.properties`. [cite_start]Esto garantiza que al generar la imagen Docker, el servidor Tomcat embebido levante siempre en un puerto predecible, facilitando la inmutabilidad y estabilidad del contenedor de cara al Continuous Delivery.
**Calidad y Cobertura (Pruebas Automatizadas):**Incorporación de las clases de pruebas unitarias `TecnicoControllerTest` y `TecnicoServiceTest` para cumplir con los requisitos del Quality Gate antes de liberar el artefacto.