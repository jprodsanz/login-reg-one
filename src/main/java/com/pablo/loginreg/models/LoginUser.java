package com.pablo.loginreg.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


// this exists in order to have an object to bind fo the form
public class LoginUser {
    @NotBlank
    @Email(message="Invalid email address")
    private String email;
    @NotBlank // don't give the min away
    private String password;

    public LoginUser() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
