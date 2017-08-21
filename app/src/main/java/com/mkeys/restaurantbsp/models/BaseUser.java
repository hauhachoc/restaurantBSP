package com.mkeys.restaurantbsp.models;

/**
 * Created by hautran on 21/08/17.
 */

public class BaseUser {

    public String email;
    public String password;

    public BaseUser() {
    }

    public BaseUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

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
