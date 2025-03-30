package com.vet.ms_bff.presentation.controller;

import com.vet.ms_bff.presentation.dto.UsuarioDto;
import com.vet.ms_bff.service.implementation.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestión de usuarios.
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @PostMapping
    public ResponseEntity<String> crearUsuario(@RequestBody UsuarioDto usuarioDto) {
        String resultado = usuarioService.crearUsuario(usuarioDto);
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/asignar-rol")
    public ResponseEntity<String> asignarRol(@RequestParam String userId, @RequestParam String rolId) {
        String resultado = usuarioService.asignarRol(userId, rolId);
        return ResponseEntity.ok(resultado);
    }
}