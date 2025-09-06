package com.ownproject.userservice.payload.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsDTO {

    private String type;
    private String value;
    private Boolean temporary;
}
