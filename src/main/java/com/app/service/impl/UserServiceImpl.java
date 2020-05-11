package com.app.service.impl;

import com.app.model.Role;
import com.app.model.User;
import com.app.repository.RoleRepository;
import com.app.repository.UserRepository;
import com.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

/**
 * @author Максим Зеленский
 */

@Service
public class UserServiceImpl implements UserService {
    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findByLogin(String login) {
        log.info("IN findByLogin: finding user with login - {}", login);
        return userRepository.findByLogin(login);// return null if not found
    }

    @Override
    public User findById(UUID id) {
        log.info("IN findById: finding user with id - {}", id);
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User createOrUpdate(User user) {
        // Check roles
        if (user.getRoles() == null) {
            Role userRole = roleRepository.findByName("USER");
            user.setRoles(Collections.singletonList(userRole));
        }

        User createdOrUpdatedUser = userRepository.save(user);
        log.info("IN createOrUpdate: user with login {} successfully created/updated",
                createdOrUpdatedUser.getLogin());
        return createdOrUpdatedUser;
    }


    @Override
    public void deleteById(UUID id) {
        User userToRemove = userRepository.findById(id).orElse(null);

        if (userToRemove == null) {
            log.error("IN deleteById: User with id {} is not exists", id);
        } else {
            userRepository.deleteById(id);
            log.info("IN deleteById: User with id {} successfully deleted", id);
        }
    }
}
