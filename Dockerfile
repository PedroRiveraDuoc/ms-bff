# Dockerfile para ms-bff

FROM eclipse-temurin:17-jdk-alpine

# Crear directorio para la app
WORKDIR /app

# Copiar el JAR generado por Maven
COPY target/ms-bff-0.0.1-SNAPSHOT.jar app.jar

# Puerto expuesto (ajustar si se necesita)
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]