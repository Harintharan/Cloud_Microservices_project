package com.ownproject.userservice.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
@JsonProperty("access_token")
    private String accessToken;
@JsonProperty("refresh_token")
private String refreshToken;
    @JsonProperty("expire_in")
private int expireIn;

@JsonProperty("token_type")
    private String tokenType;

@JsonProperty("scope")
private String scope;




}
