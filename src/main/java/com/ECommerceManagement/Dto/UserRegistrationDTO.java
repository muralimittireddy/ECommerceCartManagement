package com.ECommerceManagement.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {
    private String username;
    private String password;
    private Set<String> roles;

    // Getters and Setters
}
