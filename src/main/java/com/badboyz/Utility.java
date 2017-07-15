package com.badboyz;

/**
 * Created by yusuf on 4/26/2016.
 */
public class Utility {

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


    public static String SIGN_UP_EMAIL_SENDER = "xxxxx@gmail.com";
    public static String SIGN_UP_EMAIL_SUBJECT = "You just signed up!";
    public static String SIGN_UP_EMAIL_BODY = "Hi,\nPlease visit our site to log in with your credentials.";

    public static int TOKEN_EXPIRATION_IN_MINUTES = 60 * 8;
}
