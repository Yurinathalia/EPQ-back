package com.epq.epq_bakend.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "Escriba su nombre de usuario o correo electrónico") String identificador,
        @NotBlank(message = "Escribe tu contraseña") String password

) {
}
