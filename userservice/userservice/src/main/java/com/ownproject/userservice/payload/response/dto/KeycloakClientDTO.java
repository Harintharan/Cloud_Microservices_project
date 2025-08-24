package com.ownproject.userservice.payload.response.dto;

import lombok.Data;

@Data
public class KeycloakClientDTO {
    private String id;       // UUID
    private String clientId; // human-readable
}
