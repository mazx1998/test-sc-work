package com.app;

import com.app.model.Role;
import com.app.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.Set;


/**
 * @author Максим Зеленский
 */

@SpringBootTest
class UserValidationTest {

    @Autowired
    private Validator validator;

    @Test
    void invalidTest_1() {
        // User with null fields
        User user = new User();

        Set<ConstraintViolation<User>> violations =  validator.validate(user);

        Assert.isTrue(violations.size() > 0, "Violations size must be more than 0");
    }

    @Test
    void invalidTest_2() {
        // Half filled user
        User user = new User();
        user.setEmail("email");
        user.setLogin("login");
        user.setPassword("password");

        Set<ConstraintViolation<User>> violations =  validator.validate(user);

        Assert.isTrue(violations.size() > 0, "Violations size must be more than 0");
    }

    @Test
    void validTest() {
        // Test role for user
        Role role = new Role();
        role.setName("TEST_ROLE");
        // Filled user
        User user = new User();
        user.setEmail("email");
        user.setLogin("login");
        user.setPassword("password");
        user.setFirstName("first name");
        user.setFamilyName("family name");
        user.setRoles(Collections.singletonList(role));

        Set<ConstraintViolation<User>> violations =  validator.validate(user);

        Assert.isTrue(violations.size() == 0, "Violations size must be 0");
    }
}
