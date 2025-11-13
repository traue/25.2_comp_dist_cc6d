package br.mackenzie.mackmusic.controller.dto;

public record AuthRequest(
        String email,
        String password
) {}