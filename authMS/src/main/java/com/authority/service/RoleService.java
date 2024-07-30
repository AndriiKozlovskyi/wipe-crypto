package com.authority.service;

import com.authority.entity.Role;
import com.authority.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role getRoleByRoleType(String roleName) {
        List<Role> roles = roleRepository.findAll();

        Role _role = null;
        for(Role role : roles) {
            if (role.getName().equals(roleName)) {
                _role = role;
            }
        }
        return _role;
    }

}
