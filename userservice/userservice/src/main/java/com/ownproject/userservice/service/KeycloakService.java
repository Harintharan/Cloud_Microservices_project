package com.ownproject.userservice.service;

import com.ownproject.userservice.payload.response.TokenResponse;
import com.ownproject.userservice.payload.response.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class KeycloakService {

    private static final String KEYCLOAK_BASE_URL = "http://keycloak:8080";
    private static final String KEYCLOAK_ADMIN_API=KEYCLOAK_BASE_URL +"/admin/realms/master/users";


    private static final String TOKEN_URL= KEYCLOAK_BASE_URL+"/realms/master/protocol/openid-connect/token";

    private static final String  CLIENT_ID="saloon-booking-client";
    private static final String CLIENT_SECRET="WbASFbWUku4XYLJEOjpCKOHLUjzpGucr";


    private static final String GRANT_TYPE="password";
    private static final String scope = "openid email profile";

    private static final String username = "admin";
    private static final String password ="admin";
    private static final String clientId = "aafce9b9-755d-42c5-8fe8-e83b5273c6a2";

    @Autowired

    private final RestTemplate restTemplate;


    public void createUser(SignupDTO signupDTO) throws Exception{

        String ACCESS_TOKEN =getAdminAccessToken(username,password,GRANT_TYPE,null).getAccessToken();

        System.out.println(ACCESS_TOKEN);
        CredentialsDTO credentialsDTO = new CredentialsDTO();
        credentialsDTO.setTemporary(false);
        credentialsDTO.setType("password");
        credentialsDTO.setValue(signupDTO.getPassword());



        UserRequest userRequest = new UserRequest();
       // userRequest.setUserName(signupDTO.getUsername());
        userRequest.setUsername(signupDTO.getUsername());



        userRequest.setEmail(signupDTO.getEmail());
        userRequest.setEnabled(true);

        userRequest.setLastName(signupDTO.getFullName());

        System.out.println(credentialsDTO);
       userRequest.getCredentials().add(credentialsDTO);



        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(ACCESS_TOKEN);


        HttpEntity<UserRequest> requestEntity = new HttpEntity<>(userRequest,httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                KEYCLOAK_ADMIN_API, HttpMethod.POST,requestEntity ,String.class
        );

        if(response.getStatusCode() == HttpStatus.CREATED)
        {
            System.out.println("User created successfully");

            KeycloakUserDTO user = fetchFirstUserByUsername(signupDTO.getUsername(),ACCESS_TOKEN);
            KeycloakRole role = getRoleByName(clientId,ACCESS_TOKEN,signupDTO.getRole().toString());


            List<KeycloakRole> roles =new ArrayList<>();
            roles.add(role);

            assignRoleToUser(user.getId(),clientId,roles,ACCESS_TOKEN);
        }

        else {
            System.out.println("user creation fail");
            throw  new Exception(response.getBody());
        }



    }



    public TokenResponse getAdminAccessToken(String username, String password, String grantType, String refreshToken) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      // httpHeaders.setContentType(MediaType.APPLICATION_JSON);

      //  httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));


        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", grantType);
        requestBody.add("username", username);
        requestBody.add("password", password);
        requestBody.add("refresh_token", refreshToken);
        requestBody.add("client_id", CLIENT_ID);
        requestBody.add("client_secret", CLIENT_SECRET);
        requestBody.add("scope", scope);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, httpHeaders);


        System.out.println("URL: " + TOKEN_URL);
        System.out.println("Headers: " + httpHeaders);
        System.out.println("Body: " + requestBody);

        try {
            ResponseEntity<TokenResponse> response = restTemplate.exchange(
                    TOKEN_URL, HttpMethod.POST, requestEntity, TokenResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new Exception("Failed to obtain access token: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception("Failed to obtain access token: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new Exception("Failed to obtain access token: " + e.getMessage(), e);
        }
    }

//    public TokenResponse getAdminAccessToken(String username,
//                                             String password,
//                                             String grantType,
//                                             String refreshToken) throws Exception {
//
//        WebClient webClient = WebClient.builder()
//                .baseUrl(TOKEN_URL)
//                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
//                .build();
//
//        // Prepare form data
//        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
//        formData.add("grant_type", grantType);
//        formData.add("client_id", CLIENT_ID);
//        formData.add("client_secret", CLIENT_SECRET);
//        formData.add("scope", scope);
//
//        if ("password".equalsIgnoreCase(grantType)) {
//            formData.add("username", username);
//            formData.add("password", password);
//        } else if ("refresh_token".equalsIgnoreCase(grantType)) {
//            formData.add("refresh_token", refreshToken);
//        }
//
//        try {
//            return webClient.post()
//                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                    .bodyValue(formData)
//                    .retrieve()
//                    .bodyToMono(TokenResponse.class)
//                    .block(); // block to get the response synchronously
//        } catch (Exception e) {
//            throw new Exception("Failed to obtain access token: " + e.getMessage(), e);
//        }
//    }





    public KeycloakRole getRoleByName(String clientId, String token, String role)  {

        String url = KEYCLOAK_BASE_URL+"/admin/realms/master/clients/" +clientId+"/roles/"+role;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization","Bearer "+token);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);




        HttpEntity<Void > requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<KeycloakRole> response = restTemplate.exchange(
                url, HttpMethod.GET,requestEntity ,KeycloakRole.class
        );




        return response.getBody();






    }


    public KeycloakUserDTO fetchFirstUserByUsername(String username, String token) throws Exception {

        String url = KEYCLOAK_BASE_URL+"/admin/realms/master/users?username="+username;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);




        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<KeycloakUserDTO[]> response = restTemplate.exchange(
                url, HttpMethod.GET,requestEntity ,KeycloakUserDTO[].class
        );

        KeycloakUserDTO[] users = response.getBody();

        if(users != null && users.length>0)


        { return users[0];}

        throw new Exception("user not found with username"+ username);









    }

    public  void assignRoleToUser(String userId, String clientId, List<KeycloakRole> roles,String token) throws Exception {


        String url = KEYCLOAK_BASE_URL+"/admin/realms/master/users/"+userId+"/role-mappings/clients/"+clientId;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);




        HttpEntity<List<KeycloakRole>> requestEntity = new HttpEntity<>(roles,httpHeaders);


       try{

           ResponseEntity<String> response = restTemplate.exchange(
                   url, HttpMethod.POST,requestEntity ,String.class
           );

       }catch (Exception e)
       {
           throw new Exception("faild to assign new role"+ e.getMessage());
       }


    }

    public  KeycloakUserDTO fetchUserProfileByJwt(String token) throws Exception {


        String url = KEYCLOAK_BASE_URL+"/realms/master/protocol/openid-connect/userinfo";

        HttpHeaders httpHeaders = new HttpHeaders();
       httpHeaders.set("Authorization",token);
       // httpHeaders.set("Authorization", "Bearer " + token);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);




        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);


        try{

            ResponseEntity<KeycloakUserDTO> response = restTemplate.exchange(
                    url, HttpMethod.GET,requestEntity ,KeycloakUserDTO.class
            );

            return response.getBody();

        }catch (Exception e)
        {
            throw new Exception("faild to get user info"+ e.getMessage());
        }




    }


}


