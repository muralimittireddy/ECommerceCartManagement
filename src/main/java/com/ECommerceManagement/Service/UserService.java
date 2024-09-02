package com.ECommerceManagement.Service;


import com.ECommerceManagement.Dto.RoleDTO;
import com.ECommerceManagement.Dto.UserDTO;
import com.ECommerceManagement.Dto.UserRegistrationDTO;
import com.ECommerceManagement.Entities.Role;
import com.ECommerceManagement.Entities.User;
import com.ECommerceManagement.Repository.RoleRepository;
import com.ECommerceManagement.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public UserDTO createUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setPassword(userRegistrationDTO.getPassword());

        Set<Role> roles = new HashSet<>();
        for (String roleName : userRegistrationDTO.getRoles()) {
            Role role = roleRepository.findByRole(roleName)
                    .orElseThrow(() -> new IllegalArgumentException("Role " + roleName + " does not exist"));
            roles.add(role);
        }

        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        return convertToUserDTO(savedUser);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        return convertToUserDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUname(user.getUsername());
        userDTO.setRoles(user.getRoles().stream()
                .map(role -> new RoleDTO(role.getRoleId(), role.getRole()))
                .collect(Collectors.toSet()));
        return userDTO;
    }
}

