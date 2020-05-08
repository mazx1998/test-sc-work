package com.app.repository;

import com.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Максим Зеленский
 */
public interface UserRepository extends JpaRepository<User, UUID> {
    public User findByLogin(String login);
}
