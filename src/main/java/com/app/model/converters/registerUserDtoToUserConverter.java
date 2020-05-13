package com.app.model.converters;

import com.app.model.dto.RegisterUserDto;
import com.app.model.User;
import org.springframework.core.convert.converter.Converter;

/**
 * @author Максим Зеленский
 */
public class registerUserDtoToUserConverter implements Converter<RegisterUserDto, User> {
    @Override
    public User convert(RegisterUserDto registerUserDto) {
        User resultUser = new User();
        resultUser.setEmail(registerUserDto.getEmail());
        resultUser.setLogin(registerUserDto.getLogin());
        resultUser.setPassword(registerUserDto.getPassword());
        resultUser.setFirstName(registerUserDto.getFirstName());
        resultUser.setFamilyName(registerUserDto.getFamilyName());
        return resultUser;
    }
}
