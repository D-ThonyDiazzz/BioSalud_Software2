# BioSalud - Proyecto base (solo microservicios CORE)

Base de microservicios Spring Boot para practicar **arquitectura hexagonal** en equipo.
Contiene unicamente los 7 agregados del core del negocio. El codigo esta en su estructura
original (controllers / services / repositories / models): **la refactorizacion a hexagonal
la hace cada integrante en su propio servicio.**

## Servicios del core

| Servicio | Puerto | Base de datos | Llama a |
|---|---|---|---|
| msvc-pacientes | 8001 | msvc_pacientes | msvc-responsables |
| msvc-responsables | 8002 | msvc_responsables | - |
| msvc-analisis | 8006 | msvc_analisis | - |
| msvc-perfiles | 8007 | msvc_perfiles | msvc-analisis |
| msvc-ordenesatencion | 8008 | msvc_ordenesatencion | msvc-pacientes, msvc-analisis, msvc-perfiles |
| msvc-muestras | 8009 | msvc_muestras | msvc-ordenesatencion |
| msvc-resultados | 8011 | msvc_resultados | msvc-muestras |

(Verifica el nombre exacto de la BD en el `application.properties` de cada servicio.)

## Cambios respecto al proyecto anterior

Se retiraron los servicios fuera del core: `afiliados`, `convenios`, `instituciones`,
`informesresultados`, `parametrosanaliticos` y `reactivos`. Para que los servicios del core
sigan funcionando solos:

- **msvc-ordenesatencion**: se elimino `ConvenioClientRest`, el modelo `Convenio`, el campo
  `idConvenio` y la validacion de convenio. El descuento por detalle queda en 0.
- **msvc-muestras**: se elimino `ReactivoClientRest`. `PATCH /{id}/procesar` ya no recibe
  `idReactivo` ni `idAnalisis`.
- **msvc-resultados**: se elimino `ParametroClientRest`. `Resultado` guarda ahora
  `nombreParametro`, `unidad`, `rangoReferencia` e `interpretacion` en lugar de `idParametro`.
  Ya no se marca OBSERVADO automaticamente por rango; se hace con el endpoint de observar.

> Si se cambian entidades, la BD local (`ddl-auto=update`) puede conservar columnas viejas
> (`id_convenio`, `id_parametro`). Lo mas simple es borrar las BD locales y dejar que se recreen.

## Requisitos

- JDK 26 (definido en los `pom.xml`) configurado igual en IntelliJ para todos
- Maven, MySQL en `localhost:3306`

## Credenciales de BD

Ya no hay contrasenas en el repositorio. Cada quien define variables de entorno
(o las configura en *Run/Debug Configurations* de IntelliJ):

```
DB_USERNAME=root      # por defecto: root
DB_PASSWORD=su_clave  # por defecto: vacio
```

## Como trabajar en equipo

1. Un servicio = un dueno (ver `.github/CODEOWNERS`). Nadie edita el modulo de otro.
2. Rama por servicio: `feature/<servicio>-hexagonal` (ej. `feature/pacientes-hexagonal`).
3. `main` protegida: solo por Pull Request con 1 aprobacion.
4. No es necesario tocar el `pom.xml` raiz, el `.gitignore` ni este README para refactorizar a hexagonal.
5. Commits pequenos y descriptivos (ej. `pacientes: mover entidad a infrastructure/entity`).

## Estructura objetivo por servicio (del video)

```
<servicio>/src/main/java/org/onions/laboratorio/msvc/<servicio>/
├── domain/model/          # negocio puro, sin JPA
├── application/
│   ├── port/              # interfaces (contratos)
│   └── service/           # casos de uso, dependen de los puertos
└── infrastructure/
    ├── controller/        # entrada REST
    ├── adapter/           # implementan los puertos (JPA, Feign)
    ├── entity/            # entidades JPA (XxxEntity)
    └── repository/        # Spring Data (XxxJpaRepository)
```

## Ejecutar un servicio

```
mvn -pl msvc-pacientes spring-boot:run
```
(o desde IntelliJ, ejecutando la clase `Msvc...Application` de cada modulo)
