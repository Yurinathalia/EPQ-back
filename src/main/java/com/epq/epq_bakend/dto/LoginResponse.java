package com.epq.epq_bakend.dto;

import java.time.Instant;

public record LoginResponse (String token, Instant expiraEn, String username, String role) {}
