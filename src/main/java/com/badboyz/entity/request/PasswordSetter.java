package com.badboyz.entity.request;

import java.io.Serializable;

/**
 * Created by yusuf on 7/19/2017.
 */
public class PasswordSetter implements Serializable {

    private String hash;
    private String password;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
