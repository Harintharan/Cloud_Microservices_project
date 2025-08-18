//package com.ownproject.userservice.controller;
//
//import com.ownproject.userservice.payload.response.dto.TokenDto;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.http.*;
//import java.util.*;
//
//@RestController
//@RequestMapping("/auth")
//public class KeycloakAuthController {
//
//    private final String KEYCLOAK_TOKEN_URL = "http://localhost:8090/realms/master/protocol/openid-connect/token";
//    private final String CLIENT_ID = "saloon-booking-client";
//    private final String CLIENT_SECRET = "fbE11dpIzGZPER1M1NqO3jM9uOyZbSVT";
//
//    @PostMapping("/token")
//    public void getToken(@RequestParam String username, @RequestParam String password) {
//        System.out.println("Welcome");
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
////        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//
////        Map<String, String> bodyParams = new HashMap<>();
////        bodyParams.put("grant_type", "password");
////        bodyParams.put("username", username);
////        bodyParams.put("password", password);
////        bodyParams.put("client_id", CLIENT_ID);
////        bodyParams.put("client_secret", CLIENT_SECRET);
////        bodyParams.put("scope", "openid email profile");
////
////        HttpEntity<Map<String, String>> request = new HttpEntity<>(bodyParams, headers);
//
//        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
//        requestBody.add("grant_type", "password");
//        requestBody.add("client_id", CLIENT_ID);
//        requestBody.add("client_secret", CLIENT_SECRET);
//        requestBody.add("username", username);
//        requestBody.add("password", password);
//
//
////        String body = "grant_type=password"
////                + "&username=" + username
////                + "&password=" + password
////                + "&client_id=" + CLIENT_ID
////                + "&client_secret=" + CLIENT_SECRET
////                + "&scope=openid email profile";
//
////        HttpEntity<String> request = new HttpEntity<>(body, headers);
////        System.out.println(request);
//
////        ResponseEntity<String> response = restTemplate.exchange(KEYCLOAK_TOKEN_URL, HttpMethod.POST, request, String.class);
//
//        ResponseEntity<TokenDto> response = restTemplate.postForEntity(KEYCLOAK_TOKEN_URL,
//                new HttpEntity<>(requestBody, headers), TokenDto.class);
//
//        System.out.println(response.toString());
//
//
////        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
//    }
//}




//package com.ownproject.userservice.controller;
//
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@RestController
//@RequestMapping("/auth")
//public class KeycloakAuthController {
//
//    private final String KEYCLOAK_TOKEN_URL = "http://localhost:8090/realms/master/protocol/openid-connect/token";
//    private final String CLIENT_ID = "saloon-booking-client";
//    private final String CLIENT_SECRET = "fbE11dpIzGZPER1M1NqO3jM9uOyZbSVT";
//
//    @PostMapping("/token")
//    public ResponseEntity<String> getToken(@RequestParam String username, @RequestParam String password) {
//        System.out.println("Welcome");
//
//        // Create a WebClient instance using the full token URL.
//        WebClient webClient = WebClient.create(KEYCLOAK_TOKEN_URL);
//
//        // Prepare form data as a MultiValueMap.
//        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
//        formData.add("grant_type", "password");
//        formData.add("username", username);
//        formData.add("password", password);
//        formData.add("client_id", CLIENT_ID);
//        formData.add("client_secret", CLIENT_SECRET);
//        formData.add("scope", "openid email profile");
//
//        // Send the POST request with form URL-encoded body.
//        String responseBody = webClient.post()
//                .uri("") // Since KEYCLOAK_TOKEN_URL is complete, no additional URI is needed.
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .accept(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromFormData(formData))
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//
//        return ResponseEntity.ok(responseBody);
//    }
//}

package com.ownproject.userservice.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@CrossOrigin(origins = "http://localhost:8090")
@RequestMapping("/auth")
public class KeycloakAuthController {

    private final String KEYCLOAK_TOKEN_URL = "http://localhost:8090/realms/master/protocol/openid-connect/token";
    private final String CLIENT_ID = "saloon-booking-client";
    private final String CLIENT_SECRET = "fbE11dpIzGZPER1M1NqO3jM9uOyZbSVT";

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestParam String username, @RequestParam String password) throws Exception {
        System.out.println("Welcome");

        // Customize the underlying HttpClient to remove the "Expect" header.
        HttpClient httpClient = HttpClient.create()
                .headers(h -> h.remove("Expect"));

        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(KEYCLOAK_TOKEN_URL)
                .build();

        // Manually build the URL-encoded request body.
        String body = "grant_type=" + URLEncoder.encode("password", StandardCharsets.UTF_8.name())
                + "&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8.name())
                + "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8.name())
                + "&client_id=" + URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8.name())
                + "&client_secret=" + URLEncoder.encode(CLIENT_SECRET, StandardCharsets.UTF_8.name())
                + "&scope=" + URLEncoder.encode("openid email profile", StandardCharsets.UTF_8.name());

        int contentLength = body.getBytes(StandardCharsets.UTF_8).length;

        // Send the POST request with the fully-buffered body.
        String responseBody = webClient.post()
                .uri("") // Base URL already contains the full endpoint.
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();


        System.out.println(responseBody);

        return ResponseEntity.ok(responseBody);
    }
}
