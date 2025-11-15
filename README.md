# CRUD Spring - License Plate Service

Servicio REST para gestión de placas vehiculares con operaciones CRUD completas, integración con MySQL y cobertura de tests superior al 75%.

## 📋 Descripción

Este servicio proporciona una API REST completa para gestionar información de placas vehiculares. Consume datos de una API externa, los almacena en MySQL y proporciona operaciones CRUD completas.

## 🚀 Características

- ✅ CRUD completo para placas vehiculares
- ✅ Integración con MySQL 8.0+
- ✅ Tests unitarios con JUnit 5 y Mockito
- ✅ Cobertura de código con JaCoCo (>75%)
- ✅ API REST documentada con OpenAPI/Swagger
- ✅ Spring Boot 3.3.0 con Java 21
- ✅ Validación de datos
- ✅ Manejo de errores robusto
- ✅ Health check endpoints

## 📋 Requisitos

- Java 21+
- Gradle 8.9+
- MySQL 8.0+
- Docker (opcional, para MySQL)

## ⚙️ Configuración

### Base de Datos MySQL

#### Con Docker

```bash
# Ejecutar contenedor MySQL
docker run -d \
  --name mysql-crud \
  -e MYSQL_ROOT_PASSWORD=secret \
  -e MYSQL_DATABASE=vehiculos \
  -p 3306:3306 \
  mysql:8.0

# Conectar a MySQL
docker exec -it mysql-crud mysql -uroot -p
```

#### Crear Esquema

```sql
CREATE DATABASE IF NOT EXISTS vehiculos;

USE vehiculos;

CREATE TABLE movil (
    `key` BIGINT AUTO_INCREMENT PRIMARY KEY,
    id VARCHAR(200) NULL,
    patente VARCHAR(10) NULL,
    tipoAuto VARCHAR(100) NULL,
    color VARCHAR(100) NULL
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8
  COLLATE=utf8_general_ci
  AUTO_INCREMENT=1;
```

### Variables de Entorno

```properties
# Database
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/vehiculos?useSSL=false&serverTimezone=UTC
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=secret

# Application
SERVER_PORT=8081
SPRING_PROFILES_ACTIVE=local

# External API
ENDPOINT_URL=https://arsene.azurewebsites.net/LicensePlate

# CORS
CORS_ALLOWED_ORIGINS=http://localhost:4200,http://localhost:3000
```

### application.properties

```properties
spring.application.name=crud-spring-service
server.port=8081

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/vehiculos?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=secret
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# JPA
spring.jpa.open-in-view=false

# External API
endpoint=https://arsene.azurewebsites.net/LicensePlate
timeout=25000

# CORS
cors.allowed-origins=http://localhost:4200,http://localhost:3000

# Logging
logging.level.cl.cleverit.licenseplate=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

## 🏃 Ejecución Local

### Con Gradle Wrapper

```bash
# Compilar
./gradlew clean build

# Ejecutar
./gradlew bootRun

# Ejecutar con perfil específico
./gradlew bootRun --args='--spring.profiles.active=local'

# Ejecutar JAR
java -jar build/libs/crud-spring-0.0.1-SNAPSHOT.jar
```

### Con Variables de Entorno

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/vehiculos
export SPRING_DATASOURCE_USERNAME=root
export SPRING_DATASOURCE_PASSWORD=secret

./gradlew bootRun
```

## 🐳 Docker

### Docker Compose

Crea un archivo `docker-compose.yml`:

```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: secret
      MYSQL_DATABASE: vehiculos
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

  crud-service:
    build: .
    ports:
      - "8081:8081"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/vehiculos
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: secret
    depends_on:
      - mysql

volumes:
  mysql_data:
```

Ejecutar:
```bash
docker-compose up -d
```

## 📡 API Endpoints

### PUT /api/save

Guarda todas las placas vehiculares desde la API externa en la base de datos.

**Request**: `PUT /api/save`

**Response**:
```json
{
  "code": 200,
  "message": "Success",
  "totalRecords": 150
}
```

### GET /api/healthcheck

Health check endpoint.

**Response**:
```json
{
  "status": "UP",
  "timestamp": "2025-01-15T10:30:00Z",
  "database": "UP"
}
```

### Repository REST Endpoints

El proyecto usa Spring Data REST, exponiendo automáticamente:

- `GET /vehiculos` - Lista todos los vehículos
- `GET /vehiculos/{id}` - Obtiene un vehículo por ID
- `POST /vehiculos` - Crea un nuevo vehículo
- `PUT /vehiculos/{id}` - Actualiza un vehículo
- `DELETE /vehiculos/{id}` - Elimina un vehículo
- `GET /vehiculos/search/findByPatente?patente=ABC123` - Busca por patente

## 🔐 Seguridad

### Configuración SSL/TLS

El servicio incluye configuración segura de SSL/TLS:

- **Producción**: Validación SSL estándar
- **Desarrollo**: TrustStrategy solo para desarrollo/test

Ver: `src/main/java/cl/cleverit/licenseplate/config/SecurityRestTemplateConfig.java`

### CORS

Configurado para permitir solo orígenes específicos:

```properties
cors.allowed-origins=https://production-domain.com
```

**Ver**: [SECURITY_IMPROVEMENTS.md](../SECURITY_IMPROVEMENTS.md)

## 🧪 Testing

### Ejecutar Tests

```bash
# Todos los tests
./gradlew test

# Solo tests unitarios
./gradlew test --tests "*Test"

# Solo tests de integración
./gradlew test --tests "*IT"

# Con cobertura
./gradlew test jacocoTestReport

# Ver reporte de cobertura
open build/reports/jacoco/test/html/index.html
```

### Cobertura de Código

El objetivo es mantener cobertura > 75%. Verificar con:

```bash
./gradlew jacocoTestReport
./gradlew jacocoTestCoverageVerification
```

El reporte se genera en: `build/reports/jacoco/test/html/index.html`

### Ejemplo de Test

```java
@Test
void testSaveLicensePlate() {
    // Arrange
    ServiceStatus expectedStatus = new ServiceStatus(200, "Success");
    when(service.saveLicensePlate()).thenReturn(expectedStatus);
    
    // Act
    ResponseEntity<ServiceStatus> response = controller.saveAllVechiculos();
    
    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(200, response.getBody().getCode());
}
```

## 📚 Documentación de API

Una vez ejecutando el servicio:

- **Swagger UI**: `http://localhost:8081/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8081/v3/api-docs`
- **HAL Explorer**: `http://localhost:8081/vehiculos` (para Spring Data REST)

## 📝 Estructura del Proyecto

```
crud-spring/
├── src/
│   ├── main/
│   │   ├── java/cl/cleverit/licenseplate/
│   │   │   ├── LicenseplateApplication.java
│   │   │   ├── config/
│   │   │   │   └── SecurityRestTemplateConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── CrudController.java
│   │   │   │   └── HealcheckController.java
│   │   │   ├── service/
│   │   │   │   ├── TransactionService.java
│   │   │   │   └── TransactionServiceImpl.java
│   │   │   ├── repository/
│   │   │   │   └── VehiculosRepository.java
│   │   │   ├── entity/
│   │   │   │   └── Movil.java
│   │   │   └── filters/
│   │   │       └── CorsFilter.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── local.properties
│   └── test/
│       └── java/
│           └── cl/cleverit/licenseplate/
│               ├── controller/
│               │   └── CrudControllerTest.java
│               └── service/
│                   └── TransactionServiceImplTest.java
├── build.gradle
├── settings.gradle
└── README.md
```

## 🔧 Troubleshooting

### Error: Cannot connect to MySQL

1. Verificar que MySQL esté ejecutándose: `docker ps`
2. Verificar URL de conexión en `application.properties`
3. Verificar credenciales
4. Verificar que la base de datos existe

### Error: Table 'vehiculos.movil' doesn't exist

Ejecutar el script SQL de creación de tabla (ver sección Configuración).

### Error: External API timeout

1. Verificar conectividad a `https://arsene.azurewebsites.net/LicensePlate`
2. Ajustar timeout: `timeout=30000`
3. Revisar configuración de RestTemplate

### Error: Cobertura de tests < 75%

1. Ejecutar: `./gradlew jacocoTestReport`
2. Revisar reporte para identificar código no cubierto
3. Agregar tests para aumentar cobertura

## 🛠️ Desarrollo

### Agregar Nueva Funcionalidad

1. Crear feature branch: `git checkout -b feature/nueva-funcionalidad`
2. Implementar cambios
3. Agregar tests unitarios (cobertura > 75%)
4. Verificar que todos los tests pasen
5. Verificar cobertura: `./gradlew jacocoTestReport`
6. Commit: `git commit -m "feat: agregar nueva funcionalidad"`
7. Crear Pull Request

### Comandos Útiles

```bash
# Limpiar y compilar
./gradlew clean build

# Ejecutar tests
./gradlew test

# Verificar dependencias
./gradlew dependencies

# Actualizar dependencias
./gradlew dependencyUpdates

# Ejecutar con debug
./gradlew bootRun --debug-jvm
```

## 📊 Modelo de Datos

### Entidad Movil

```java
@Entity
@Table(name = "movil")
public class Movil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long key;
    
    private String id;
    private String patente;
    private String tipoAuto;
    private String color;
}
```

## 🔄 Flujo de Datos

1. **Consumo API Externa**: El servicio consume `https://arsene.azurewebsites.net/LicensePlate`
2. **Transformación**: Los datos se transforman a la entidad `Movil`
3. **Persistencia**: Los datos se guardan en MySQL
4. **API REST**: Los datos están disponibles a través de endpoints REST

## 📞 Soporte

Para reportar issues o hacer preguntas:
1. Abre un issue en el repositorio
2. Revisa la documentación principal: [../README.md](../README.md)
3. Consulta la documentación de seguridad: [../SECURITY_IMPROVEMENTS.md](../SECURITY_IMPROVEMENTS.md)

---

**Versión**: 1.0  
**Última actualización**: Enero 2025
