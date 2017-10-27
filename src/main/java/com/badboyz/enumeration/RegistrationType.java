package com.badboyz.enumeration;

/**
 * Created by yusuf on 7/19/2017.
 */
public enum RegistrationType {

    LOCAL(0),
    GOOGLE(1),
    GITHUB(2),
    FACEBOOK(3),
    INSTAGRAM(4);

    private final int value;

    RegistrationType(int value) {
        this.value = value;
    }
}
