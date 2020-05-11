package com.app.validation;

import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Максим Зеленский
 */
public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {

    private UserRepository userRepository;

    @Autowired
    public UniqueLoginValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findByLogin(login) == null;
    }
}
