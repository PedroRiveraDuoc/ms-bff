package com.vet.ms_bff.presentation.dto;

/**
 * DTO (Data Transfer Object) que representa los datos de un usuario.
 */
public class UsuarioDto {
    private String nombre;
    private String email;

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}