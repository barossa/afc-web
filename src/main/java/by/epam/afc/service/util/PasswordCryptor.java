package by.epam.afc.service.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * The type Password cryptor.
 */
public class PasswordCryptor {
    private static final PasswordCryptor instance = new PasswordCryptor();
    private static final int DEFAULT_CRYPT_COST = 10;

    private PasswordCryptor() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static PasswordCryptor getInstance() {
        return instance;
    }

    /**
     * Encrypt password string.
     *
     * @param password the password
     * @return the string
     */
    public String encrypt(char[] password) {
        return BCrypt.withDefaults().hashToString(DEFAULT_CRYPT_COST, password);
    }

    /**
     * Verify password boolean.
     *
     * @param password the password
     * @param hash     the hash
     * @return the boolean
     */
    public boolean verify(char[] password, char[] hash) {
        BCrypt.Result result = BCrypt.verifyer().verify(password, hash);
        return result.verified;
    }
}
