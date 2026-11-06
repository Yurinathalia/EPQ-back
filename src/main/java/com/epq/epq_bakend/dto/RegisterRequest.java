package com.epq.epq_bakend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(


        @NotBlank(message = "Escribe un nombre de usuario")
        String username,

        @NotBlank(message = "Escribe un correo electrónico")
        @Email(message = "El correo electrónico no es válido")
        String email,

        @NotBlank(message = "Escribe una contraseña")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password

) {
}
