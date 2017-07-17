package com.badboyz.entity.request;

import java.io.Serializable;

/**
 * Created by yusuf on 7/16/2017.
 */
public class Token implements Serializable {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
