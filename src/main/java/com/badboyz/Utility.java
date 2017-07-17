package com.badboyz;

/**
 * Created by yusuf on 4/26/2016.
 */
public class Utility {

    public static String MAIN_LINK = "http://localhost:8080";

    public static int SUCCESS_CODE = 100;
    public static String SUCCESS_MSG = "Success";

    public static int SERIALIZATION_PROBLEM_CODE = 101;
    public static String SERIALIZATION_PROBLEM_MSG = "Serialization problem";

    public static int INVALID_EMAIL_CODE = 102;
    public static String INVALID_EMAIL_MSG = "Invalid Email";

    public static int INVALID_NAME_CODE = 103;
    public static String INVALID_NAME_MSG = "Invalid Name";

    public static int MIN_PASSWORD_LENGTH = 6;
    public static int INSUFFICIENT_PASSWORD_CODE = 104;
    public static String INSUFFICIENT_PASSWORD_MSG = "Insufficient Password";

    public static int LOGIN_UNSUCCESSFUL_CODE = 105;
    public static String LOGIN_UNSUCCESSFUL_MSG = "Login attempt is unsuccessful";

    public static int USER_NOT_EXIST_CODE = 106;
    public static String USER_NOT_EXIST_MSG = "User is not logged in";

    public static int INVALID_CREDENTIALS_CODE = 107;
    public static String INVALID_CREDENTIALS_MSG = "Invalid Credentials";

    public static int LINK_EXPIRED_CODE = 108;
    public static String LINK_EXPIRED_MSG = "The link you are tyring to use is expired. Please start over";


    public static String EMAIL_SENDER = "xxxxx@gmail.com";

    public static String SIGN_UP_EMAIL_SUBJECT = "You just signed up!";
    public static String SIGN_UP_EMAIL_BODY = "Hi,\nWelcome to our paradise!\n" +
            "Please visit following link to activate your account:\n" +
            MAIN_LINK + "/activation/";

    public static String UNLOCK_EMAIL_SUBJECT = "Your account is unlocked!";
    public static String UNLOCK_EMAIL_BODY = "Hello\nYour account is successfully unlocked.\n" +
            "For login page:\n" +
            MAIN_LINK + "/login/";

    public static String ACTIVATE_EMAIL_SUBJECT = "Your account is activated!";
    public static String ACTIVATE_EMAIL_BODY = "Hi there!\nYour account is just activated by a system administrator.\n" +
            "Have fun..";

    public static int TOKEN_EXPIRATION_IN_MINUTES = 60 * 8;
}
