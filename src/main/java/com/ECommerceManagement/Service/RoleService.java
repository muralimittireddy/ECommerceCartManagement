package com.ECommerceManagement.Service;

import com.ECommerceManagement.Dto.RoleDTO;
import com.ECommerceManagement.Entities.Role;
import com.ECommerceManagement.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public RoleDTO addRole(RoleDTO roleDTO) {
        if (roleRepository.findByRole(roleDTO.getRole()).isPresent()) {
            throw new IllegalArgumentException("Role already exists");
        }
        Role role = new Role();
        role.setRole(roleDTO.getRole());
        Role savedRole = roleRepository.save(role);
        return convertToRoleDTO(savedRole);
    }

    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(this::convertToRoleDTO)
                .collect(Collectors.toList());
    }

    private RoleDTO convertToRoleDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleId(role.getRoleId());
        roleDTO.setRole(role.getRole());
        return roleDTO;
    }
}
