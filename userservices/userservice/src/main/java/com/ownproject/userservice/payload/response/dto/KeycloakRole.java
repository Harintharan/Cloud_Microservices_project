package com.ownproject.userservice.payload.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class KeycloakRole  {

private String id;
    private String name;
private String description;
private Boolean composite;
private Boolean clientRole;

private String containerId;

private Map<String ,Object> attributes;




}
