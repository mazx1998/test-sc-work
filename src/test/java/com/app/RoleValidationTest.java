package com.app;

import com.app.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author Максим Зеленский
 */

@SpringBootTest
class RoleValidationTest {

    @Autowired
    private Validator validator;

    @Test
    void invalidTest() {
        // Role with null field
        Role role = new Role();

        Set<ConstraintViolation<Role>> violations = validator.validate(role);

        Assert.isTrue(violations.size() > 0, "Violations size must be more than 0");
    }

    @Test
    void validTest() {
        // Role with filled fields
        Role role = new Role();
        role.setName("TEST_ROLE");

        Set<ConstraintViolation<Role>> violations = validator.validate(role);

        Assert.isTrue(violations.size() == 0, "Violations size must be 0");
    }
}
