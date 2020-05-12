package com.app.dto;

import com.app.validation.UniqueLogin;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author Максим Зеленский
 */

public class RegisterUserDto {
    @NotBlank(message = "Please provide a login")
    @UniqueLogin
    private String login;

    @NotBlank(message = "Please provide a password")
    private String password;

    @NotBlank(message = "Please provide a email")
    @Email(message = "Please provide correct email")
    private String email;

    @NotBlank(message = "Please provide a first name")
    private String firstName;

    @NotBlank(message = "Please provide a family name")
    private String familyName;

    private String patronymic;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
}
