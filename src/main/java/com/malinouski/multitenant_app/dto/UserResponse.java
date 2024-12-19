package com.malinouski.multitenant_app.dto;

public record UserResponse(
        Long id,
        String name,
        String email
) {
}
