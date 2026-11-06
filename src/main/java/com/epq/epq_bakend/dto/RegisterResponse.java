package com.epq.epq_bakend.dto;

public record RegisterResponse(

        Long id,
        String username,
        String email,
        String role,
        String mensaje

) {
}