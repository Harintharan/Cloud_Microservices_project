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
    private static final String CLIENT_SECRET="mnxGvUXZhMDIXHRDgZKoRq0zmaeVLnYL";


    private static final String GRANT_TYPE="password";
    private static final String scope = "openid email profile";

    private static final String username = "admin";
    private static final String password ="admin";
    private static final String clientId = "496f6d4d-f2a0-4f92-89a7-381e41ab2d5d";

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

//package com.ownproject.userservice.service;
//
//import com.ownproject.userservice.payload.response.TokenResponse;
//import com.ownproject.userservice.payload.response.dto.CredentialsDTO;
//import com.ownproject.userservice.payload.response.dto.KeycloakClientDTO;
//import com.ownproject.userservice.payload.response.dto.KeycloakRole;
//import com.ownproject.userservice.payload.response.dto.KeycloakUserDTO;
//import com.ownproject.userservice.payload.response.dto.SignupDTO;
//import com.ownproject.userservice.payload.response.dto.UserRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.HttpServerErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class KeycloakService {
//
//    private final RestTemplate restTemplate;
//
//    // ===== Config from application.yml =====
//    @Value("${security.keycloak.base-url}")
//    private String keycloakBaseUrl;
//
//    @Value("${security.keycloak.realm}")
//    private String realm;                 // realm where users & clients exist
//
//    @Value("${security.keycloak.admin-realm:${security.keycloak.realm}}")
//    private String adminRealm;            // realm to obtain admin token (default = realm)
//
//    @Value("${security.keycloak.client-id}")
//    private String clientIdConfigured;    // human clientId
//
//    @Value("${security.keycloak.client-secret:}")
//    private String clientSecret;          // optional (for confidential clients)
//
//    @Value("${security.keycloak.admin-username:}")
//    private String adminUsername;
//
//    @Value("${security.keycloak.admin-password:}")
//    private String adminPassword;
//
//
//
//    // ===== URL builders =====
//    private String tokenUrlForRealm(String r) {
//        return keycloakBaseUrl + "/realms/" + r + "/protocol/openid-connect/token";
//    }
//    private String usersAdminUrl() {
//        return keycloakBaseUrl + "/admin/realms/" + realm + "/users";
//    }
//    private String clientsQueryUrl(String clientId) {
//        return keycloakBaseUrl + "/admin/realms/" + realm + "/clients?clientId=" + clientId;
//    }
//    private String clientRolesBaseUrl(String clientUuid) {
//        return keycloakBaseUrl + "/admin/realms/" + realm + "/clients/" + clientUuid + "/roles";
//    }
//    private String userClientRoleMappingsUrl(String userId, String clientUuid) {
//        return keycloakBaseUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/clients/" + clientUuid;
//    }
//    private String userInfoUrl() {
//        return keycloakBaseUrl + "/realms/" + realm + "/protocol/openid-connect/userinfo";
//    }
//
//    // ===== Helpers =====
//    private static String bearer(String token) {
//        if (token == null) return null;
//        return token.startsWith("Bearer ") ? token : "Bearer " + token;
//    }
//
//    /** Resolve the Keycloak INTERNAL client UUID from the human clientId */
//    private String resolveClientUuid(String clientId, String adminAccessToken) throws Exception {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", bearer(adminAccessToken));
//        HttpEntity<Void> req = new HttpEntity<>(headers);
//
//        ResponseEntity<KeycloakClientDTO[]> resp =
//                restTemplate.exchange(clientsQueryUrl(clientId), HttpMethod.GET, req, KeycloakClientDTO[].class);
//
//        KeycloakClientDTO[] arr = resp.getBody();
//        if (arr != null && arr.length > 0 && arr[0].getId() != null) {
//            return arr[0].getId(); // UUID needed by admin endpoints
//        }
//        throw new Exception("Keycloak client not found for clientId=" + clientId);
//    }
//
//    /** Obtain admin access token using password or refresh_token grant (configured adminRealm) */
//    public TokenResponse getAdminAccessToken(String username, String password, String grantType, String refreshToken) throws Exception {
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
//        form.add("grant_type", grantType);
//
//        if ("password".equalsIgnoreCase(grantType)) {
//            form.add("username", username);
//            form.add("password", password);
//        } else if ("refresh_token".equalsIgnoreCase(grantType) && refreshToken != null) {
//            form.add("refresh_token", refreshToken);
//        }
//
//        form.add("client_id", clientIdConfigured);
//        if (clientSecret != null && !clientSecret.isBlank()) {
//            form.add("client_secret", clientSecret);
//        }
//        form.add("scope", "openid email profile");
//
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(form, httpHeaders);
//
//        String tokenUrl = tokenUrlForRealm(adminRealm);
//        log.debug("Requesting admin token at {}", tokenUrl);
//
//        try {
//            ResponseEntity<TokenResponse> response = restTemplate.exchange(
//                    tokenUrl, HttpMethod.POST, requestEntity, TokenResponse.class);
//
//            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//                return response.getBody();
//            } else {
//                throw new Exception("Failed to obtain access token: " + response.getStatusCode());
//            }
//        } catch (HttpClientErrorException | HttpServerErrorException e) {
//            throw new Exception("Failed to obtain access token: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
//        } catch (Exception e) {
//            throw new Exception("Failed to obtain access token: " + e.getMessage(), e);
//        }
//    }
//
//    /** Create user, then assign a client role by name */
//    public void createUser(SignupDTO signupDTO) throws Exception {
//        // 1) Admin token
//        String adminAccessToken = getAdminAccessToken(adminUsername, adminPassword, "password", null).getAccessToken();
//
//        // 2) Build user
//        CredentialsDTO credentialsDTO = new CredentialsDTO();
//        credentialsDTO.setTemporary(false);
//        credentialsDTO.setType("password");
//        credentialsDTO.setValue(signupDTO.getPassword());
//
//        UserRequest userRequest = new UserRequest();
//        userRequest.setUsername(signupDTO.getUsername());
//        userRequest.setEmail(signupDTO.getEmail());
//        userRequest.setEnabled(true);
//        userRequest.setLastName(signupDTO.getFullName());
//        userRequest.getCredentials().add(credentialsDTO);
//
//        // 3) Create user
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", bearer(adminAccessToken));
//        HttpEntity<UserRequest> requestEntity = new HttpEntity<>(userRequest, headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                usersAdminUrl(), HttpMethod.POST, requestEntity, String.class
//        );
//
//        if (response.getStatusCode() != HttpStatus.CREATED) {
//            throw new Exception("User creation failed: " + response.getBody());
//        }
//
//        // 4) Fetch created user (or parse Location header)
//        KeycloakUserDTO user = fetchFirstUserByUsername(signupDTO.getUsername(), adminAccessToken);
//
//        // 5) Resolve client UUID from human clientId (from yml)
//        String clientUuid = resolveClientUuid(clientIdConfigured, adminAccessToken);
//
//        // 6) Load role by name & assign to user (client role)
//        KeycloakRole role = getRoleByName(clientUuid, adminAccessToken, signupDTO.getRole().toString());
//        List<KeycloakRole> roles = new ArrayList<>();
//        roles.add(role);
//
//        assignRoleToUser(user.getId(), clientUuid, roles, adminAccessToken);
//    }
//
//    /** Read a client role by name (requires client UUID) */
//    public KeycloakRole getRoleByName(String clientUuid, String token, String roleName) {
//        String url = clientRolesBaseUrl(clientUuid) + "/" + roleName;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", bearer(token));
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<Void> req = new HttpEntity<>(headers);
//        ResponseEntity<KeycloakRole> resp =
//                restTemplate.exchange(url, HttpMethod.GET, req, KeycloakRole.class);
//
//        return resp.getBody();
//    }
//
//    /** Find first user matching username */
//    public KeycloakUserDTO fetchFirstUserByUsername(String username, String token) throws Exception {
//        String url = usersAdminUrl() + "?username=" + username;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", bearer(token));
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<String> req = new HttpEntity<>(headers);
//        ResponseEntity<KeycloakUserDTO[]> resp =
//                restTemplate.exchange(url, HttpMethod.GET, req, KeycloakUserDTO[].class);
//
//        KeycloakUserDTO[] users = resp.getBody();
//        if (users != null && users.length > 0) return users[0];
//
//        throw new Exception("User not found: " + username);
//    }
//
//    /** Assign client roles to user */
//    public void assignRoleToUser(String userId, String clientUuid, List<KeycloakRole> roles, String token) throws Exception {
//        String url = userClientRoleMappingsUrl(userId, clientUuid);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", bearer(token));
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<List<KeycloakRole>> req = new HttpEntity<>(roles, headers);
//        try {
//            restTemplate.exchange(url, HttpMethod.POST, req, String.class);
//        } catch (Exception e) {
//            throw new Exception("Failed to assign new role: " + e.getMessage());
//        }
//    }
//
//    /** Read user profile from an access token */
//    public KeycloakUserDTO fetchUserProfileByJwt(String accessToken) throws Exception {
//        String url = userInfoUrl();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", bearer(accessToken)); // ensure Bearer prefix
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<String> req = new HttpEntity<>(headers);
//        try {
//            ResponseEntity<KeycloakUserDTO> resp =
//                    restTemplate.exchange(url, HttpMethod.GET, req, KeycloakUserDTO.class);
//            return resp.getBody();
//        } catch (Exception e) {
//            throw new Exception("Failed to get user info: " + e.getMessage());
//        }
//    }
//}
//
//
