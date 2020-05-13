package com.app.model.converters;

import com.app.model.Role;
import com.app.model.User;
import com.app.security.JwtUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Максим Зеленский
 */

public class UserToJwtUserConverter implements Converter<User, JwtUser> {
    @Override
    public JwtUser convert(User user) {
        return new JwtUser(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getEmail(),
                user.getFirstName(),
                user.getFamilyName(),
                user.getPatronymic(),
                mapToGrantedAuthorities(user.getRoles())
        );
    }

    private List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
