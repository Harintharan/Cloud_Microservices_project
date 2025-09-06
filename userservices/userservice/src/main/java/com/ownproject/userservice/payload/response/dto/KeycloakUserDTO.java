package com.ownproject.userservice.payload.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakUserDTO {

    private String id;
    private String firstName;
    private String lastName;

    private String email;

    private String username;
}
