package com.epq.epq_bakend.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Service;

import com.epq.epq_bakend.entity.AppUser;

@Service
public class JwtService {

    public record TokenGenerado(String valor, Instant expiraEn) {}

    private final JwtEncoder encoder;
    private final long horasValidez;

    public JwtService(
            JwtEncoder encoder,
            @Value("${app.jwt.expiracion-horas:8}") long horasValidez) {
        this.encoder = encoder;
        this.horasValidez = horasValidez;
    }

    public TokenGenerado generar(AppUser usuario) {
        Instant ahora = Instant.now();
        Instant expira = ahora.plus(horasValidez, ChronoUnit.HOURS);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("epq-backend")
                .issuedAt(ahora)
                .expiresAt(expira)
                .subject(String.valueOf(usuario.getId()))
                .claim("username", usuario.getUsername())
                .claim("role", usuario.getRole())
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        String token = encoder
                .encode(JwtEncoderParameters.from(header, claims))
                .getTokenValue();

        return new TokenGenerado(token, expira);
    }
}
