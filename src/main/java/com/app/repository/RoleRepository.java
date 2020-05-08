package com.app.repository;

import com.app.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Максим Зеленский
 */
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(String name);
}
