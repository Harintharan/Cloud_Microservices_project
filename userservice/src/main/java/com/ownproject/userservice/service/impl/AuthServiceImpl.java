package com.ownproject.userservice.service.impl;

import com.ownproject.userservice.model.User;
import com.ownproject.userservice.payload.response.AuthResponse;
import com.ownproject.userservice.payload.response.TokenResponse;
import com.ownproject.userservice.payload.response.dto.SignupDTO;
import com.ownproject.userservice.repository.UserRepository;
import com.ownproject.userservice.service.AuthService;
import com.ownproject.userservice.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl  implements AuthService {

    private final UserRepository userRepository;
    private final KeycloakService keycloakService;


    @Override
    public AuthResponse login(String username, String password) throws Exception {


        TokenResponse tokenResponse= keycloakService.getAdminAccessToken(username,password,"password",null);
        AuthResponse authResponse= new AuthResponse();
        authResponse.setRefresh_Token(tokenResponse.getRefreshToken());
        authResponse.setJwt(tokenResponse.getAccessToken());

        authResponse.setMessage("login sucess");

        return authResponse;


    }

    @Override
    public AuthResponse signUP(SignupDTO req) throws Exception {
      keycloakService.createUser(req);
      User user = new User();
      user.setUserName(req.getUsername());
      user.setPassword(req.getPassword());
      user.setEmail(req.getEmail());
      user.setRole(req.getRole());
      user.setFullName(req.getFullName());
      user.setCreateAt(LocalDateTime.now());

      userRepository.save(user);


        TokenResponse tokenResponse= keycloakService.getAdminAccessToken(req.getUsername(),req.getPassword(),"password",null);
        AuthResponse authResponse= new AuthResponse();
        authResponse.setRefresh_Token(tokenResponse.getRefreshToken());
        authResponse.setJwt(tokenResponse.getAccessToken());
        authResponse.setRole(user.getRole());
        authResponse.setMessage("Register sucess");

        return authResponse;





    }

    @Override
    public AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception {
        TokenResponse tokenResponse= keycloakService.getAdminAccessToken(null,null,"refresh_token",refreshToken);
        AuthResponse authResponse= new AuthResponse();
        authResponse.setRefresh_Token(tokenResponse.getRefreshToken());
        authResponse.setJwt(tokenResponse.getAccessToken());

        authResponse.setMessage("login success");

        return authResponse;
    }
}
