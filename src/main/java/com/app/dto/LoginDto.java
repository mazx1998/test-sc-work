package com.app.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Максим Зеленский
 */

public class LoginDto {

    @NotNull(message = "Please provide a login")
    private String login;

    @NotNull(message = "Please provide a password")
    private String password;

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
}
