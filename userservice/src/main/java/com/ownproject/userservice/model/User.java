package com.ownproject.userservice.model;

import com.ownproject.userservice.domain.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String fullName;
    @NotBlank(message = "email is madentory")
    @Email(message = "Email should be valid")
    private String email;
    private String phone;
    @NotBlank(message = "username is mandatory")
    private String userName;


    @Column(nullable = false)
    private UserRole role;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private String updateAt;
    @NotBlank(message = "password is mandentory")

    private String password;


}
