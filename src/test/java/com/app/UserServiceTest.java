package com.app;

import com.app.model.Role;
import com.app.model.User;
import com.app.repository.RoleRepository;
import com.app.repository.UserRepository;
import com.app.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Collections;

/**
 * @author Максим Зеленский
 */

@SpringBootTest
class UserServiceTest {
    private UserService userService;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceTest(UserService userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Test
    void createTest() {
        // Arrange role
        Role role = new Role();
        role.setName("TEST_ROLE");
        roleRepository.save(role);
        // Arrange user
        User testUser = new User();
        testUser.setEmail("test@mail.ru");
        testUser.setLogin("test login");
        testUser.setPassword("password");
        testUser.setFirstName("first name");
        testUser.setFamilyName("family name");
        testUser.setRoles(Collections.singletonList(roleRepository.findByName("TEST_ROLE")));

        User createdUser = userService.createOrUpdate(testUser);

        Assert.isTrue(createdUser != null, "Created user cant't be null");
        userRepository.delete(testUser);
        roleRepository.delete(role);
    }

    @Test
    void updateTest() {
        // Arrange role
        Role role = new Role();
        role.setName("TEST_ROLE");
        roleRepository.save(role);
        // Arrange user
        User testUser = new User();
        testUser.setEmail("test@mail.ru");
        testUser.setLogin("test login");
        testUser.setPassword("password");
        testUser.setFirstName("first name");
        testUser.setFamilyName("family name");
        testUser.setRoles(Collections.singletonList(roleRepository.findByName("TEST_ROLE")));
        userRepository.save(testUser);
        // Get user
        User userToUpdate = userRepository.findByLogin(testUser.getLogin());
        userToUpdate.setLogin("updated login");

        User updatedUser = userService.createOrUpdate(userToUpdate);

        Assert.isTrue(updatedUser.getLogin().equals("updated login"),
                "New user login must be equal to 'updated login'");
        userRepository.delete(updatedUser);
        roleRepository.delete(role);
    }

    @Test
    void findByLoginTest() {
        // Arrange role
        Role role = new Role();
        role.setName("TEST_ROLE");
        roleRepository.save(role);
        // Arrange user
        User testUser = new User();
        testUser.setEmail("test@mail.ru");
        testUser.setLogin("test login");
        testUser.setPassword("password");
        testUser.setFirstName("first name");
        testUser.setFamilyName("family name");
        testUser.setRoles(Collections.singletonList(roleRepository.findByName("TEST_ROLE")));
        userRepository.save(testUser);
        // Get user
        User foundUser = userService.findByLogin(testUser.getLogin());

        Assert.isTrue(foundUser != null,
                "Found user must be not null");
        userRepository.delete(testUser);
        roleRepository.delete(role);
    }

    @Test
    void deleteByIdTest() {
        // Arrange role
        Role role = new Role();
        role.setName("TEST_ROLE");
        roleRepository.save(role);
        // Arrange user
        String userLogin = "test login";
        User testUser = new User();
        testUser.setEmail("test@mail.ru");
        testUser.setLogin(userLogin);
        testUser.setPassword("password");
        testUser.setFirstName("first name");
        testUser.setFamilyName("family name");
        testUser.setRoles(Collections.singletonList(roleRepository.findByName("TEST_ROLE")));
        userRepository.save(testUser);

        userService.deleteById(testUser.getId());

        Assert.isTrue(userRepository.findByLogin(userLogin) == null,
                "User must not be found");
        roleRepository.delete(role);
    }
}
