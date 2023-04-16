package br.com.lucasatolini.attornatus.controller.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class UserVO {
    private Long id;

    @NotNull(message = "Name must not be blank")
    @Size(max = 100, message = "Name should not exceed 100 characters, please use abbreviations")
    private String name;

    @NotNull(message = "Birth date must not be blank")
    private Date birthDate;

    @NotNull(message = "Username must not be blank")
    private String userName;

    @NotNull(message = "Password must not be blank")
    private String password;

    public UserVO() {
    }

    public UserVO(String name, Date birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public UserVO(String name, Date birthDate, String userName, String password) {
        this.name = name;
        this.birthDate = birthDate;
        this.userName = userName;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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
