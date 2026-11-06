package com.epq.epq_bakend.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.epq.epq_bakend.entity.AppUser;
import com.epq.epq_bakend.repository.AppUserRepository;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class UsuarioInicialRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UsuarioInicialRunner.class);
    private static final Set<String> ROLES = Set.of("BOSS", "MANAGER", "ADMIN");

    private final AppUserRepository repository;
    private final PasswordEncoder encoder;
    private final String username;
    private final String email;
    private final String password;
    private final String role;

    public UsuarioInicialRunner(AppUserRepository repository,
            PasswordEncoder encoder,
            @Value("${app.bootstrap.username:}") String username,
            @Value("${app.bootstrap.email:}") String email,
            @Value("${app.bootstrap.password:}") String password,
            @Value("${app.bootstrap.role:BOSS}") String role) {
        this.repository = repository;
        this.encoder = encoder;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public void run(String... args) {
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            return;
        }
        if (password.length() < 10) {
            log.warn("No se creó el usuario inicial: la contraseña debe tener al menos 10 caracteres.");
            return;
        }
        if (!ROLES.contains(role)) {
            log.warn("No se creó el usuario inicial: rol no válido ({}).", role);
            return;
        }
        if (repository.findByUsernameIgnoreCaseOrEmailIgnoreCase(username, email).isPresent()) {
            log.info("El usuario '{}' ya existe; no se crea de nuevo.", username);
            return;
        }

        AppUser usuario = new AppUser();
        usuario.setUsername(username.trim());
        usuario.setEmail(email.trim());
        usuario.setPasswordHash(encoder.encode(password));
        usuario.setRole(role);
        usuario.setMustChangePassword(false);
        usuario.setPasswordChangedAt(LocalDateTime.now());
        repository.save(usuario);
        log.info("Usuario '{}' creado con rol {}.", username, role);
    }
}