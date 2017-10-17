package com.autenticacion;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.util.UUID;

public class TokenHelper {

    private final static String SECRET_KEY = "myKey";

    public static String generarToken(String userName) {
        long minutes = System.currentTimeMillis() / 1000 / 60;
        String key = UUID.randomUUID().toString().toUpperCase() + "|" + userName + "|" + minutes;
        StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
        jasypt.setPassword(SECRET_KEY);
        return jasypt.encrypt(key);
    }

    public static boolean tokenValido(String token) {
        try {
            StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
            jasypt.setPassword(SECRET_KEY);
            jasypt.decrypt(token);

        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
