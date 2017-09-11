package com.trevorism.secure;

import javax.ws.rs.core.HttpHeaders;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author tbrooks
 */
public class PasswordProvider {

    public static final String AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION;
    public static final String PASSWORD = get();
    private static final String SECURE_FILE_NAME = "secure.txt";

    private static String get(){
        InputStream secure = SecureRequestFilter.class.getClassLoader().getResourceAsStream(SECURE_FILE_NAME);
        if (secure == null) {
            return null;
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(secure));
            return reader.readLine();
        }
        catch (Exception e){
            return null;
        }
        finally {
            try {
                secure.close();
            }catch (Exception ignored){

            }
        }
    }
}
