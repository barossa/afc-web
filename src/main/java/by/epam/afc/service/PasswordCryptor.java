package by.epam.afc.service;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordCryptor {
    private static final int DEFAULT_CRYPT_COST = 10;

    private PasswordCryptor(){}

    public static String encrypt(char[] password){
        return BCrypt.withDefaults().hashToString(DEFAULT_CRYPT_COST, password);
    }
}
