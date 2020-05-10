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

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

/**
 * @author Максим Зеленский
 */

@Service
public class UserServiceImpl implements UserService {
    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private Validator validator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository, Validator validator) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.validator = validator;
    }

    @Override
    public User findByLogin(String login) {
        log.info("IN findByLogin: finding user with login - {}", login);
        return userRepository.findByLogin(login);// return null if not found
    }

    @Override
    public User createOrUpdate(User user) {
        // Check role
        if (user.getRoles() == null) {
            Role userRole = roleRepository.findByName("USER");
            user.setRoles(Collections.singletonList(userRole));
        }

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        User createdOrUpdatedUser = null;
        // User fields validation
        if (violations.size() == 0) {
            // Check unique of login
            final String userLogin = user.getLogin();
            if (userRepository.findByLogin(userLogin) == null) {
                createdOrUpdatedUser = userRepository.save(user);
                log.info("IN createOrUpdate: user with login {} successfully created/updated",
                        createdOrUpdatedUser.getLogin());
            } else {
                log.error("IN createOrUpdate: login {} already exists", userLogin);
            }
        } else {
            log.error("IN createOrUpdate: user was'not created/updated ->");
            violations.forEach(violation -> {
                log.error(violation.getMessage());
            });
        }

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
