package com.ownproject.userservice.payload.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {


    private String username;
    private Boolean enabled;
  //  private String fullName;

    private  String firstName;
    private String lastName;
    private String email;

    private List<CredentialsDTO> credentials=new ArrayList<>();


}
