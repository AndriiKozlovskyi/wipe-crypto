package com.authority.repository;

import com.authority.entity.Role;
import com.authority.entity.User;
import com.authority.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleType name);
}
