package com.app.model.factories;

import com.app.dto.RegisterUserDto;
import com.app.model.Role;
import com.app.model.User;

import java.util.List;

/**
 * @author Максим Зеленский
 */

public final class UserFactory {

    public static User create(RegisterUserDto registerUserDto) {
        User resultUser = new User();
        resultUser.setEmail(registerUserDto.getEmail());
        resultUser.setLogin(registerUserDto.getLogin());
        resultUser.setPassword(registerUserDto.getPassword());
        resultUser.setFirstName(registerUserDto.getFirstName());
        resultUser.setFamilyName(registerUserDto.getFamilyName());
        return resultUser;
    }

    public static User create(String email, String login, String password,
                              String firstName, String familyName, List<Role> roles) {
        User resultUser = new User();
        resultUser.setEmail(email);
        resultUser.setLogin(login);
        resultUser.setPassword(password);
        resultUser.setFirstName(firstName);
        resultUser.setFamilyName(familyName);
        resultUser.setRoles(roles);
        return  resultUser;
    }

}
