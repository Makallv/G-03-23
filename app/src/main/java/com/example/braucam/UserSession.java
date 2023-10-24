package com.example.braucam;

import java.io.Serializable;
import java.util.Date;

public class UserSession {
    private String userName;
    private String email;
    private int id;


    public UserSession(String userName, String email, int id) {
        this.userName = userName;
        this.email = email;
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }
    public String getEmail() {
        return this.email;
    }
    public int getId() {
        return this.id;
    }
}
