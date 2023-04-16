package br.com.lucasatolini.attornatus.controller.vo;

import jakarta.validation.constraints.NotNull;

public class LoginVO {
    @NotNull(message = "Username must not be blank")
    private String userName;

    @NotNull(message = "Password must not be blank")
    private String password;

    public LoginVO() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
