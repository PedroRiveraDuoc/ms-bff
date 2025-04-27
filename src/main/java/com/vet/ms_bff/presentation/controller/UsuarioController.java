package com.vet.ms_bff.presentation.controller;

import com.vet.ms_bff.presentation.dto.UsuarioDto;
import com.vet.ms_bff.service.implementation.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST que expone los endpoints para la gestión de usuarios y roles.
 * Este controlador maneja las peticiones HTTP y delega la lógica de negocio al servicio correspondiente.
 * 
 * @author Pedro Rivera
 * @version 1.0
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    /**
     * Endpoint para crear un nuevo usuario en el sistema.
     * El usuario creado recibirá automáticamente un rol por defecto.
     * 
     * @param usuarioDto Objeto que contiene los datos del usuario a crear
     * @return ResponseEntity con el ID del usuario creado
     */
    @PostMapping
    public ResponseEntity<String> crearUsuario(@RequestBody UsuarioDto usuarioDto) {
        String resultado = usuarioService.crearUsuario(usuarioDto);
        return ResponseEntity.ok(resultado);
    }

    /**
     * Endpoint para asignar un rol específico a un usuario existente.
     * 
     * @param userId ID del usuario al que se le asignará el rol
     * @param rolId ID del rol a asignar
     * @return ResponseEntity con el resultado de la operación
     */
    @PostMapping("/asignar-rol")
    public ResponseEntity<String> asignarRol(@RequestParam String userId, @RequestParam String rolId) {
        String resultado = usuarioService.asignarRol(userId, rolId);
        return ResponseEntity.ok(resultado);
    }

    /**
     * Endpoint para eliminar un rol del sistema.
     * Al eliminar un rol, se actualizarán automáticamente todos los usuarios
     * que tenían este rol asignado.
     * 
     * @param rolId ID del rol a eliminar
     * @return ResponseEntity con el resultado de la operación
     */
    @DeleteMapping("/rol/{rolId}")
    public ResponseEntity<String> eliminarRol(@PathVariable String rolId) {
        String resultado = usuarioService.eliminarRol(rolId);
        return ResponseEntity.ok(resultado);
    }
}