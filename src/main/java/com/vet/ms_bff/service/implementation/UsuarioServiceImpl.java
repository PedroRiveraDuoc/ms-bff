package com.vet.ms_bff.service.implementation;

import com.vet.ms_bff.presentation.dto.UsuarioDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Servicio que implementa la lógica de negocio para la gestión de usuarios y roles.
 * Se comunica con Azure Functions para realizar operaciones CRUD en la base de datos Oracle.
 * 
 * @author Pedro Rivera
 * @version 1.0
 */
@Service
public class UsuarioServiceImpl {

    // URLs de las Azure Functions para operaciones CRUD
    private final RestTemplate restTemplate = new RestTemplate();
    private final String CREAR_USUARIO_FUNCTION_URL = "https://azf-createuser.azurewebsites.net/api/CreateUser?";
    private final String ASIGNAR_ROL_FUNCTION_URL = "https://azf-createuser.azurewebsites.net/api/AssignRole?";
    private final String ELIMINAR_ROL_FUNCTION_URL = "https://azf-createuser.azurewebsites.net/api/DeleteRole?";
    
    // ID del rol por defecto que se asigna a nuevos usuarios
    // Este valor debe coincidir con el ID del rol 'Cliente' en la base de datos
    private final String ROL_POR_DEFECTO = "1";

    /**
     * Crea un nuevo usuario en el sistema y le asigna automáticamente un rol por defecto.
     * 
     * @param usuarioDto Objeto que contiene los datos del usuario a crear
     * @return String con el ID del usuario creado
     * @throws RuntimeException si la creación del usuario falla
     */
    public String crearUsuario(UsuarioDto usuarioDto) {
        // Preparamos el cuerpo de la petición con los datos del usuario
        String requestBody = usuarioDto.getNombre() + "," + usuarioDto.getEmail();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Llamamos a la Azure Function para crear el usuario
        ResponseEntity<String> response = restTemplate.exchange(
                CREAR_USUARIO_FUNCTION_URL,
                HttpMethod.POST,
                requestEntity,
                String.class);

        // Si el usuario se creó exitosamente, asignamos el rol por defecto
        if (response.getStatusCode() == HttpStatus.OK) {
            String userId = response.getBody();
            asignarRol(userId, ROL_POR_DEFECTO);
        }

        return response.getBody();
    }

    /**
     * Asigna un rol específico a un usuario en el sistema.
     * 
     * @param userId ID del usuario al que se le asignará el rol
     * @param rolId ID del rol a asignar
     * @return String con el resultado de la operación
     * @throws RuntimeException si la asignación del rol falla
     */
    public String asignarRol(String userId, String rolId) {
        // Construimos la URL con los parámetros necesarios
        String urlConParams = ASIGNAR_ROL_FUNCTION_URL + "?userId=" + userId + "&rolId=" + rolId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);

        // Llamamos a la Azure Function para asignar el rol
        ResponseEntity<String> response = restTemplate.exchange(
                urlConParams,
                HttpMethod.POST,
                requestEntity,
                String.class);

        return response.getBody();
    }

    /**
     * Elimina un rol del sistema y actualiza los usuarios que lo tenían asignado.
     * La Azure Function se encarga de:
     * 1. Eliminar el rol de la base de datos
     * 2. Actualizar los usuarios que tenían este rol
     * 3. Asignarles un rol por defecto si es necesario
     * 
     * @param rolId ID del rol a eliminar
     * @return String con el resultado de la operación
     * @throws RuntimeException si la eliminación del rol falla
     */
    public String eliminarRol(String rolId) {
        // Construimos la URL con el ID del rol a eliminar
        String urlConParams = ELIMINAR_ROL_FUNCTION_URL + "?rolId=" + rolId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);

        // Llamamos a la Azure Function para eliminar el rol
        ResponseEntity<String> response = restTemplate.exchange(
                urlConParams,
                HttpMethod.DELETE,
                requestEntity,
                String.class);

        return response.getBody();
    }
}