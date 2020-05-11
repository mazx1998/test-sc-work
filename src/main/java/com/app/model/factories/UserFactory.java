package com.app.model.factories;

import com.app.dto.UserDto;
import com.app.model.Role;
import com.app.model.User;

import java.util.List;

/**
 * @author Максим Зеленский
 */

public final class UserFactory {

    public static User create(UserDto userDto) {
        User resultUser = new User();
        resultUser.setEmail(userDto.getEmail());
        resultUser.setLogin(userDto.getLogin());
        resultUser.setPassword(userDto.getPassword());
        resultUser.setFirstName(userDto.getFirstName());
        resultUser.setFamilyName(userDto.getFamilyName());
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
