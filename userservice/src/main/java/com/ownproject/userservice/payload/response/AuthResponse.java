package com.ownproject.userservice.payload.response;

import com.ownproject.userservice.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String jwt;
    private String refresh_Token;
    private String message;
    private String title;
    private UserRole role;
}
