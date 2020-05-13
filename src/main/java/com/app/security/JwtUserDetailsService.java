package com.app.security;

import com.app.model.User;
import com.app.service.UserService;
import com.app.service.impl.ProvidedServicesServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Максим Зеленский
 */

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private Logger log = LoggerFactory.getLogger(ProvidedServicesServiceImpl.class);

    private final UserService userService;
    private final ConversionService conversionService;

    @Autowired
    public JwtUserDetailsService(UserService userService, ConversionService conversionService) {
        this.userService = userService;
        this.conversionService = conversionService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByLogin(username);
        if (user == null) {
            log.error("IN loadUserByUsername - user with username: {} not found", username);
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        JwtUser jwtUser = conversionService.convert(user, JwtUser.class);
        log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
        return jwtUser;
    }
}
