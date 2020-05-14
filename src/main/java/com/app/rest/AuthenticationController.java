package com.app.rest;

import com.app.model.User;
import com.app.model.dto.LoginDto;
import com.app.model.dto.RegisterUserDto;
import com.app.rest.factories.ResponseErrorEntityFactory;
import com.app.security.JwtTokenProvider;
import com.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Максим Зеленский
 */

@RestController
@RequestMapping("/auth/")
@Validated
public class AuthenticationController {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    private final UserService userService;
    private final ConversionService conversionService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationController(UserService userService, ConversionService conversionService,
                                    AuthenticationManager authenticationManager,
                                    JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.conversionService = conversionService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /* Consumes json example:
    *
       {
            "login": "test",
            "password": "test",
            "email": "mazx@gmail.com",
            "firstName": "Вася",
            "familyName": "Петров",
            "patronymic": "Петрович"
        }
    *
    * */
    @PostMapping("register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterUserDto newUser) {
        log.info("Registering user {}", newUser.getLogin());
        User result = conversionService.convert(newUser, User.class);
        userService.createOrUpdate(result);
        return ResponseEntity.ok().build();
    }

    /* Consumes json example:
    *
        {
            "login": "test",
            "password": "test"
        }
    *
    * */
    @PostMapping("login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDto requestDto) {
        try {
            String username = requestDto.getLogin();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, requestDto.getPassword())
            );
            User user = userService.findByLogin(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("userId", user.getId());
            response.put("token", token);

            log.info("User {} logged in", username);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            log.error("User can't log in");
            return ResponseErrorEntityFactory.create(HttpStatus.UNAUTHORIZED,
                    Collections.singletonList("Invalid username or password"));
        }
    }
}