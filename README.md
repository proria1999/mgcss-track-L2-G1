# mgcss-track-L2-G1
[![Java CI](https://github.com/proria1999/mgcss-track-L2-G1/actions/workflows/ci.yml/badge.svg)](https://github.com/proria1999/mgcss-track-L2-G1/actions/workflows/ci.yml)
[![Release pipeline](https://github.com/proria1999/mgcss-track-L2-G1/actions/workflows/release.yml/badge.svg)](https://github.com/proria1999/mgcss-track-L2-G1/actions/workflows/release.yml)

# MGCSS-Track

*Plataforma de Gestión de Solicitudes de Servicio Técnico*

Sistema desarrollado como proyecto práctico para la asignatura Mantenimiento y Gestión del Cambio en Sistemas Software (4º curso, Grado en Ingeniería Informática).

## Descripción

MGCSS-Track es una plataforma profesional para gestionar solicitudes de servicio técnico de forma centralizada y controlada. Permite:

- Registrar y clasificar solicitudes de incidencias
- Asignar solicitudes a técnicos especializados
- Gestionar el ciclo de vida completo (ABIERTA → EN_PROCESO → CERRADA)
- Reapertura de solicitudes cerradas cuando es necesario
- Mantener un histórico auditable de todos los cambios de estado
- Consultar métricas y seguimiento de solicitudes

El sistema fue diseñado desde el inicio con criterios de *mantenibilidad, **testabilidad* y *capacidad de evolución*, demostrando que pequeñas decisiones arquitectónicas iniciales tienen un impacto significativo a largo plazo.

## Autores

- *Pablo Pérez Muñoz*
- *Pablo de Borja*
- *Grupo:* L2-G1
- *Curso:* 2025/26

## Repositorio


https://github.com/proria1999/mgcss-track-L2-G1


## Stack Tecnológico

### Core
- *Java 17*
- *Spring Boot 4.0.3*
- *Spring Data JPA* (Hibernate)
- *H2 Database* (en memoria para testing)

### Testing
- *JUnit 5*
- *Mockito 5.11.0*
- *JaCoCo 0.8.11* (cobertura)
- Cobertura: *96.2%* en código nuevo

### Utilidades
- *Lombok*
- *Spring Validation* (validación de entradas)
- *springdoc-openapi 2.3.0* (Swagger/OpenAPI)

### DevOps y Calidad
- *Maven* (build y dependencias)
- *Git + GitHub* (control de versiones)
- *GitHub Actions* (CI/CD pipeline)
- *SonarCloud* (análisis de calidad)
- *H2 Console* (monitoreo en desarrollo)

## Requisitos Previos

- *Java 17* o superior
- *Maven 3.6* o superior
- *Git*
- IDE con soporte para Spring Boot (IntelliJ IDEA, Eclipse, VS Code, etc.)

## Instalación

### 1. Clonar el repositorio

bash
git clone https://github.com/proria1999/mgcss-track-L2-G1.git
cd mgcss-track-L2-G1


### 2. Compilar el proyecto

bash
mvn clean verify


Este comando:
- Compila el código fuente
- Ejecuta todos los tests (unitarios e integración)
- Valida la calidad del código

### 3. Ejecutar la aplicación

#### Opción A: Con Maven (desarrollo)

bash
mvn spring-boot:run


#### Opción B: Ejecutar el JAR compilado

bash
mvn clean package
java -jar target/proyecto-0.0.1-SNAPSHOT.jar


La aplicación estará disponible en: *http://localhost:8080*

### 4. Acceder a herramientas integradas

Una vez ejecutada, accede a:

| Herramienta | URL |
|---|---|
| *API Swagger/OpenAPI* | http://localhost:8080/swagger-ui.html |

## API REST

### Documentación interactiva


GET http://localhost:8080/swagger-ui.html


Se visualiza documentación completa, modelos y capacidad de probar endpoints directamente.

## Testing

### Ejecutar todos los tests

bash
mvn test


### Ejecutar solo tests unitarios

bash
mvn test -Dgroups=unit


### Ejecutar solo tests de integración

bash
mvn test -Dgroups=integration


### Generar reporte de cobertura

bash
mvn clean test jacoco:report


El reporte se genera en: target/site/jacoco/index.html

### Métricas actuales

- *Cobertura:* 96.2% en código nuevo
- *Bugs:* 0 (validado en SonarCloud)
- *Vulnerabilidades:* 0
- *Code Smells:* Mínimos
- *Tests:* 150+ (unitarios e integración)

## Calidad del Código

El proyecto está integrado con *SonarCloud* para análisis continuo:


[https://sonarcloud.io/organizations/proria1999/projects](https://sonarcloud.io/project/overview?id=proria1999_mgcss-track-L2-G1)


### Pipeline de calidad (GitHub Actions)

Cada push ejecuta automáticamente:

1. ✅ *Compilación limpia* con Maven
2. ✅ *Tests unitarios* (Mockito)
3. ✅ *Tests de integración* (JPA + H2)
4. ✅ *Análisis SonarCloud* (coverage, bugs, vulnerabilities)
5. ✅ *Quality Gate* (debe pasar para mergear)

*Rama protegida:* main

Requisitos para mergear:
- Pull Request obligatorio
- Pasar todos los tests
- Pasar SonarCloud Quality Gate
- Aprobación mínima de 1 reviewer

## Arquitectura

### Patrón: Arquitectura Hexagonal + Capas


        ┌─────────────────────────────┐
        │   API REST (Controllers)    │
        │    - DTOs (Request/Response)│
        ├─────────────────────────────┤
        │   SERVICIOS (Services)      │
        │   - Orquestación de casos   │
        │   - Lógica de aplicación    │
        ├─────────────────────────────┤
        │   DOMINIO (Domain)          │
        │   - Entidades               │
        │   - Reglas de negocio       │
        │   - Invariantes             │
        ├─────────────────────────────┤
        │   INFRAESTRUCTURA           │
        │   - JPA/Hibernate           │
        │   - Persistencia (H2/BD)    │
        └─────────────────────────────┘


### Principios aplicados

- *Separación de responsabilidades:* Cada capa tiene un propósito específico
- *Domain-Driven Design:* Las reglas de negocio están en el dominio, protegidas de cambios externos
- *Inyección de dependencias:* Facilita testing y flexibilidad
- *TDD:* Tests guían el diseño

## Flujo de cambios de estado


ABIERTA
  ├── Creación inicial de solicitud
  │
  └─→ EN_PROCESO
       ├── Técnico comienza a trabajar
       │
       └─→ CERRADA
            ├── Solicitud resuelta
            │
            └─→ REABIERTA (si es necesario)
                 └─→ EN_PROCESO (vuelve al trabajo)


### Reglas de negocio implementadas

1. *Solo técnicos activos:* No se puede asignar un técnico inactivo a una solicitud
2. *Cierre controlado:* Una solicitud solo se puede cerrar si está EN_PROCESO
3. *Reapertura permitida:* Una solicitud cerrada puede reabrirse si hay nuevas incidencias
4. *Histórico obligatorio:* Cada cambio de estado se registra con fecha y contexto

## Control de calidad

### Git Flow

- *Rama main:* Siempre estable, protegida
- *Ramas feature:* Para desarrollo de funcionalidades (feature/...)
- *Conventional Commits:* Formato obligatorio
  - feat: ... (nuevas funcionalidades)
  - fix: ... (correcciones)
  - refactor: ... (mejoras sin cambiar comportamiento)
  - test: ... (tests)
  - docs: ... (documentación)

Ejemplo:
bash
git commit -m "feat: implement request reopening capability"
git commit -m "test: add state history validation tests"
git commit -m "refactor: simplify state transition logic"


### Pre-merge checks

Antes de mergear a main se verifica:

- ✅ Pipeline CI verde (tests + SonarCloud)
- ✅ Cobertura >= 80%
- ✅ Cero bugs críticos
- ✅ PR con descripción clara
- ✅ Aprobación de compañero

## Métricas de Calidad

### Dashboard SonarCloud

Accede a: https://sonarcloud.io/organizations/proria1999/projects

### Métricas clave

| Métrica | Valor | Estado |
|---------|-------|--------|
| Cobertura | 96.2% | ✅ Excelente |
| Bugs | 0 | ✅ Correcto |
| Vulnerabilidades | 0 | ✅ Correcto |
| Code Smells | < 10 | ✅ Aceptable |
| Duplicación | < 5% | ✅ Bajo |
| Complejidad | Controlada | ✅ Correcto |

## Troubleshooting

### Error: "Build failure" en Maven

bash
# Limpiar cachés y recompilar
mvn clean install -DskipTests

# Si persiste, limpiar repositorio local
rm -rf ~/.m2/repository
mvn clean install


### Error: "Cannot create table" en H2

H2 en memoria se reinicia con cada ejecución. Es comportamiento esperado. Si necesitas persistencia local:

Edita application-test.yml:
yaml
spring:
  datasource:
    url: jdbc:h2:file:./testdb  # Cambia a archivo


### Tests lentos

Si los tests de integración son lentos:

bash
# Ejecutar solo unitarios (rápidos)
mvn test -Dgroups=unit

# O saltarse tests completamente
mvn clean package -DskipTests


### Puerto 8080 ocupado

Cambia el puerto en application.yml:
yaml
server:
  port: 8081


## Documentación Adicional

- *Spring Boot:* https://spring.io/projects/spring-boot
- *Spring Data JPA:* https://spring.io/projects/spring-data-jpa
- *JUnit 5:* https://junit.org/junit5/
- *Mockito:* https://site.mockito.org/
- *SonarCloud:* https://docs.sonarcloud.io/

## Notas de desarrollo

- *Tests:* Se ejecutan automáticamente en cada push
- *H2 Console:* Útil para inspeccionar datos durante debugging
- *Lombok:* Reduce código boilerplate, especialmente en entidades
- *OpenAPI:* Se actualiza automáticamente con los endpoints

## Licencia

Proyecto académico - Curso 2025/26

## Contacto

Para preguntas sobre el proyecto, contactar con el grupo L2-G1.

---

*Última actualización:* 4 de junio de 2026  
*Versión:* 0.0.1-SNAPSHOT  
*Estado:* En desarrollo activo
