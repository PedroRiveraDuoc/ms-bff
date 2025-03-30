package com.vet.ms_bff.service.implementation;

import com.vet.ms_bff.presentation.dto.UsuarioDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Servicio que comunica con funciones Azure para crear usuarios y asignar roles.
 */
@Service
public class UsuarioServiceImpl {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String CREAR_USUARIO_FUNCTION_URL = "https://azf-createuser.azurewebsites.net/api/CreateUser?";
    private final String ASIGNAR_ROL_FUNCTION_URL = "https://azf-createuser.azurewebsites.net/api/AssignRole?";

    public String crearUsuario(UsuarioDto usuarioDto) {
        String requestBody = usuarioDto.getNombre() + "," + usuarioDto.getEmail();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                CREAR_USUARIO_FUNCTION_URL,
                HttpMethod.POST,
                requestEntity,
                String.class);

        return response.getBody();
    }

    public String asignarRol(String userId, String rolId) {
        String urlConParams = ASIGNAR_ROL_FUNCTION_URL + "?userId=" + userId + "&rolId=" + rolId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);

        ResponseEntity<String> response = restTemplate.exchange(
                urlConParams,
                HttpMethod.POST,
                requestEntity,
                String.class);

        return response.getBody();
    }
}