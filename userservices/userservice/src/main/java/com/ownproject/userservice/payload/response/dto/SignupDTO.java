package com.ownproject.userservice.payload.response.dto;

import com.netflix.spectator.impl.StepDouble;
import com.ownproject.userservice.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupDTO {


  //  private String fullName;
    private String fullName;

    private String email;
    private String password;
    private String username;
    private UserRole role;

}
