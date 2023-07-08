package com.pearlyadana.rakhapuraapp.model.request;

import java.io.Serializable;

public class Login implements Serializable {

    private String userID;

    private String password;

    public Login() {
    }

    public Login(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
