package com.badboyz.general;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by yusuf on 7/17/2017.
 */
public class HashOperation {

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static final String salt = "09@*%n$8.5@tfVN";
    private static final String salt1 = "na=3S6qMLeCbe#-^";
    MessageDigest sha256;
    MessageDigest md5;

    public HashOperation() {
        try {
            md5 = MessageDigest.getInstance("MD5");
            sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String passwordHashing(String username, String text) {

        String salt2 = bytesToHex(md5.digest(username.getBytes()));
        String finalText = salt1 + text + salt2;
        return bytesToHex(sha256.digest(finalText.getBytes()));
    }

    private String bytesToHex(byte[] bytes) {

        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public String generateHash() {
        String text = bytesToHex(md5.digest((new Date()).toString().getBytes()));
        String finalText = text + salt;
        return bytesToHex(sha256.digest(finalText.getBytes()));
    }

}
