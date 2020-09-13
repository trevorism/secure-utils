package com.trevorism.secure;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author tbrooks
 */
public class PasswordProvider {

    private final String password;
    private final String signingKey;

    private static final String SECURE_FILE_NAME = "secure.txt";
    private static final String SIGNING_FILE_NAME = "signing.txt";

    private static PasswordProvider INSTANCE = null;

    public static PasswordProvider getInstance() {
        if(INSTANCE == null){
            INSTANCE = new PasswordProvider();
        }
        return INSTANCE;
    }

    private PasswordProvider() {
        password = findPassword();
        signingKey = findSigningKey();
    }

    /**
     * @return Returns the secure password
     */
    public String getPassword() {
        return password;
    }

    public String getSigningKey() {
        return signingKey;
    }

    private String findPassword() {
        return getFileContent(SECURE_FILE_NAME);
    }

    private String findSigningKey() {
        return getFileContent(SIGNING_FILE_NAME);
    }

    private String getFileContent(String secureFileName) {
        InputStream secure = PasswordProvider.class.getClassLoader().getResourceAsStream(secureFileName);
        if (secure == null) {
            return null;
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(secure));
            return reader.readLine();
        } catch (Exception e) {
            return null;
        } finally {
            try {
                secure.close();
            } catch (Exception ignored) {

            }
        }
    }


}
