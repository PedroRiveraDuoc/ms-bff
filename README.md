
# 🐾 ms-bff - Microservicio Backend For Frontend

Este microservicio forma parte del sistema de gestión de usuarios y roles para una veterinaria. El `ms-bff` actúa como intermediario entre el frontend y servicios externos como Azure Functions y Oracle Database.

---

## ⚙️ Tecnologías Utilizadas

- **Java 17 + Spring Boot**
- **Docker**
- **Azure Functions**
- **Oracle Database**
- **AWS EC2 (Ubuntu 24.04)**
- **Postman (para pruebas)**
- **Docker Hub (almacenamiento de imágenes)**

---

## 📁 Estructura del Proyecto

```
ms-bff
│-- src
│   └── main
│       └── java/com/vet/ms_bff
│           ├── configuration/security/WebSecurityConfig.java
│           ├── presentation/controller/UsuarioController.java
│           ├── presentation/dto/UsuarioDto.java
│           └── service/implementation/UsuarioServiceImpl.java
│-- Dockerfile
│-- application.properties
│-- pom.xml
```

---

## 🔌 Conexiones Establecidas

| Componente              | Descripción |
|-------------------------|-------------|
| `UsuarioController`     | Exposición de endpoints REST |
| `Azure Function - CreateUser` | Se invoca desde el método `crearUsuario()` para crear usuarios en Oracle |
| `Azure Function - AssignRole` | Se invoca desde `asignarRol()` para asignar roles a un usuario |
| `Oracle Database`       | Base de datos donde persisten usuarios y roles |
| `DockerHub`             | Imagen `pedroriveraduoc/ms-bff:latest` publicada |
| `EC2 AWS`               | Instancia t2.micro con Ubuntu + Docker corriendo el microservicio |

---

## 🚀 Despliegue en EC2

### Requisitos en EC2:

- Ubuntu 24.04
- Docker instalado

### Pasos:

1. **Conectarse por SSH**
   ```bash
   ssh -i ms-bff-key.pem ubuntu@<IP_EC2>
   ```

2. **Descargar imagen desde DockerHub**
   ```bash
   docker pull pedroriveraduoc/ms-bff:latest
   ```

3. **Ejecutar contenedor**
   ```bash
   docker run -d -p 8080:8080 pedroriveraduoc/ms-bff:latest
   ```

4. **Verificar que está corriendo**
   ```bash
   docker ps
   ```

---


## 🧪 Endpoints disponibles

| Método | Ruta                       | Descripción |
|--------|----------------------------|-------------|
| POST   | `/usuario`                 | Crea un nuevo usuario (con Azure Function) |
| POST   | `/asignar-rol`             | Asigna rol a usuario (con Azure Function) |

### Ejemplo POST `/usuario`

```json
{
  "nombre": "Pedro AWS",
  "email": "pedro@email.com"
}
```

---

## 🧪 Pruebas con Postman

- Accede a la URL:  
  `http://<IP_PUBLICA_EC2>:8080/usuario`

- Headers:
  ```
  Content-Type: application/json
  ```

---

