package com.app.service;

import com.app.model.User;

import java.util.UUID;

/**
 * @author Максим Зеленский
 */

public interface UserService {
    User findByLogin(String login);

    User findById(UUID id);

    User createOrUpdate(User user);

    void deleteById(UUID id);
}
