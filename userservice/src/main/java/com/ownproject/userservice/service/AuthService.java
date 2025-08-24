package com.ownproject.userservice.service;

import com.ownproject.userservice.payload.response.AuthResponse;
import com.ownproject.userservice.payload.response.dto.SignupDTO;

public interface AuthService {
    AuthResponse login(String username,String password) throws Exception;
    AuthResponse signUP(SignupDTO req) throws Exception;
    AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception;
}
