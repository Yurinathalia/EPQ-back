package com.epq.epq_bakend.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.epq.epq_bakend.common.exeption.ApiException;
import com.epq.epq_bakend.dto.LoginRequest;
import com.epq.epq_bakend.dto.LoginResponse;
import com.epq.epq_bakend.dto.RegisterRequest;
import com.epq.epq_bakend.dto.RegisterResponse;
import com.epq.epq_bakend.entity.AppUser;
import com.epq.epq_bakend.repository.AppUserRepository;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class AuthService {

    private static final int MAX_INTENTOS = 5;
    private static final int MINUTOS_BLOQUEO = 15;
    private static final Set<String> ROLES_PERMITIDOS = Set.of("BOSS", "MANAGER");

    private final AppUserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final String hashFalso;

    public AuthService(AppUserRepository repository, PasswordEncoder encoder, JwtService jwtService) {
        this.repository = repository;
        this.encoder = encoder;
        this.jwtService = jwtService;
        // Para tardar lo mismo cuando el usuario no existe y no revelar cuáles usuarios
        // existen
        this.hashFalso = encoder.encode("contraseña-falsa");
    }

    public LoginResponse login(LoginRequest request) {
        String identificador = request.identificador().trim();
        AppUser usuario = repository
                .findByUsernameIgnoreCaseOrEmailIgnoreCase(identificador, identificador)
                .orElse(null);

        if (usuario == null) {
            encoder.matches(request.password(), hashFalso);
            throw credencialesInvalidas();
        }

        if (usuario.getLockedUntil() != null && usuario.getLockedUntil().isAfter(LocalDateTime.now())) {
            throw new ApiException(HttpStatus.LOCKED,
                    "Cuenta bloqueada temporalmente por intentos fallidos. Inténtalo más tarde.");
        }

        if (!usuario.isActive() || !encoder.matches(request.password(), usuario.getPasswordHash())) {
            registrarFallo(usuario);
            throw credencialesInvalidas();
        }

        if (!ROLES_PERMITIDOS.contains(usuario.getRole())) {
            throw new ApiException(HttpStatus.FORBIDDEN, "No tienes permiso para acceder a esta aplicación.");
        }

        usuario.setFailedLoginAttempts(0);
        usuario.setLockedUntil(null);
        usuario.setLastLoginAt(LocalDateTime.now());
        repository.save(usuario);

        JwtService.TokenGenerado token = jwtService.generar(usuario);
        return new LoginResponse(token.valor(), token.expiraEn(), usuario.getUsername(), usuario.getRole());
    }

    private void registrarFallo(AppUser usuario) {
        int intentos = usuario.getFailedLoginAttempts() + 1;
        if (intentos >= MAX_INTENTOS) {
            usuario.setLockedUntil(LocalDateTime.now().plusMinutes(MINUTOS_BLOQUEO));
            intentos = 0;
        }
        usuario.setFailedLoginAttempts(intentos);
        repository.save(usuario);
    }

    private ApiException credencialesInvalidas() {
        return new ApiException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos.");
    }

    public RegisterResponse register(RegisterRequest request) {

        AppUser usuario = new AppUser();

        usuario.setUsername(request.username());
        usuario.setEmail(request.email());
        usuario.setPasswordHash(
                encoder.encode(request.password()));

        usuario.setRole("MANAGER");
        usuario.setActive(true);
        usuario.setMustChangePassword(false);

        repository.save(usuario);

        return new RegisterResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getRole(),
                "Usuario registrado correctamente");
    }
}