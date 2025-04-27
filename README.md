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

## 🔌 Conexiones Establecidas

| Componente              | Descripción |
|-------------------------|-------------|
| `UsuarioController`     | Exposición de endpoints REST |
| `Azure Function - CreateUser` | Se invoca desde el método `crearUsuario()` para crear usuarios en Oracle |
| `Azure Function - AssignRole` | Se invoca desde `asignarRol()` para asignar roles a un usuario |
| `Azure Function - DeleteRole` | Se invoca desde `eliminarRol()` para eliminar roles y actualizar usuarios |
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
| POST   | `/usuario`                 | Crea un nuevo usuario y le asigna automáticamente un rol por defecto |
| POST   | `/usuario/asignar-rol`     | Asigna un rol específico a un usuario existente |
| DELETE | `/usuario/rol/{rolId}`     | Elimina un rol y actualiza los usuarios que lo tenían asignado |

### Ejemplo POST `/usuario`

```json
{
  "nombre": "Pedro AWS",
  "email": "pedro@email.com"
}
```

### Ejemplo POST `/usuario/asignar-rol`

```
?userId=123&rolId=2
```

### Ejemplo DELETE `/usuario/rol/{rolId}`

```
/usuario/rol/2
```

---

## 🔄 Flujo de Trabajo

1. **Creación de Usuario**
   - Se crea el usuario en la base de datos
   - Se le asigna automáticamente el rol por defecto (Cliente)
   - Se retorna el ID del usuario creado

2. **Asignación de Rol**
   - Se verifica que el usuario y el rol existan
   - Se asigna el rol al usuario
   - Se retorna el resultado de la operación

3. **Eliminación de Rol**
   - Se elimina el rol de la base de datos
   - Se actualizan los usuarios que tenían este rol
   - Se les asigna un rol por defecto si es necesario
   - Se retorna el resultado de la operación

---

## 🧪 Pruebas con Postman

- Accede a la URL base:  
  `http://<IP_PUBLICA_EC2>:8080`

- Headers:
  ```
  Content-Type: application/json
  ```

---
Diagrama

![Diagrama_Sumativa1](https://github.com/user-attachments/assets/aac28acd-afe1-4aa8-bbf7-41d18ed56c2c)

## 📝 Notas Importantes

- El ID del rol por defecto está configurado como "1" en el servicio
- Al eliminar un rol, los usuarios afectados serán actualizados automáticamente
- Las operaciones son atómicas: si falla alguna parte de la operación, se realiza un rollback

