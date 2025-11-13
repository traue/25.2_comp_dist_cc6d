package br.mackenzie.mackmusic.controller.dto;

import java.util.UUID;

public record AuthResponse(
        boolean authenticated,
        UUID userId,
        String name
) {}