package com.app;

import com.app.model.User;
import com.app.repository.RoleRepository;
import com.app.repository.UserRepository;
import com.app.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Максим Зеленский
 */

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTests {

    private MockMvc mockMvc;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private JwtTokenProvider tokenProvider;

    @Autowired
    public SecurityTests(MockMvc mockMvc, UserRepository userRepository, RoleRepository roleRepository, JwtTokenProvider tokenProvider) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenProvider = tokenProvider;
    }

    @Test
    void usersTest() throws Exception {
        // USER role must been created
        // Arrange user
        User testUser = new User();
        testUser.setEmail("test@mail.ru");
        testUser.setLogin("testUser");
        testUser.setPassword("password");
        testUser.setFirstName("first name");
        testUser.setFamilyName("family name");
        testUser.setRoles(Collections.singletonList(roleRepository.findByName("USER")));
        userRepository.save(testUser);

        String contentInJson = "{" +
                "\"pageNumber\":0," +
                "\"pageSize\":10," +
                "\"userId\":\"" + testUser.getId() +
                "\"}";

        mockMvc.perform(MockMvcRequestBuilders.get("/users/seasonServices/getAll")
                .content(contentInJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer_" +
                        tokenProvider.createToken(testUser.getLogin(), testUser.getRoles())))
                .andExpect(status().isOk());

        userRepository.delete(testUser);
    }

    @Test
    void adminTest() throws Exception{
        // USER role must been created
        // Arrange admin
        User testAdmin = new User();
        testAdmin.setEmail("test@mail.ru");
        testAdmin.setLogin("testAdmin");
        testAdmin.setPassword("password");
        testAdmin.setFirstName("first name");
        testAdmin.setFamilyName("family name");
        testAdmin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN"),
                roleRepository.findByName("USER")));
        userRepository.save(testAdmin);

        String contentInJson = "{" +
                "\"pageNumber\":0," +
                "\"pageSize\":10" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/seasonServices/getAll")
                //.with(user(testAdmin.getLogin()))
                .content(contentInJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer_" +
                        tokenProvider.createToken(testAdmin.getLogin(), testAdmin.getRoles())))
                .andExpect(status().isOk());

        userRepository.delete(testAdmin);
    }

    @Test
    void permitAllTest() throws Exception {
        // USER role must been created
        // Arrange user
        User testUser = new User();
        testUser.setEmail("test@mail.ru");
        testUser.setLogin("testUser");
        testUser.setPassword("$2a$10$H1blzaO3FN3PjTpzRJkVh.YZLYmpN.acqFJdmMXGEsNksQ7ijIoFi");
        testUser.setFirstName("first name");
        testUser.setFamilyName("family name");
        testUser.setRoles(Collections.singletonList(roleRepository.findByName("USER")));
        userRepository.save(testUser);

        String contentInJson = "{" +
                "\"login\": \"" + testUser.getLogin() + "\","+
                "\"password\": \"password\"" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .content(contentInJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        userRepository.delete(testUser);
    }
}
