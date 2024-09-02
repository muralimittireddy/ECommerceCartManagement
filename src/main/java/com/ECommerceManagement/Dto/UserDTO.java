package com.ECommerceManagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
    public class UserDTO {
        private Long id;
        private String uname;
        private Set<RoleDTO> roles;
        private CartDTO cart;

        // Getters and Setters
    }


