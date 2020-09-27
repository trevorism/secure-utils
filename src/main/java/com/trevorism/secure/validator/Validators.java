package com.trevorism.secure.validator;

import java.util.Arrays;
import java.util.List;

public class Validators {

    public static final AuthorizationValidator passwordValidator = new PasswordValidator();
    public static final AuthorizationValidator bearerTokenValidator = new BearerTokenValidator();
    public static final AuthorizationValidator cookieValidator = new CookieValidator();
    public static final AuthorizationValidator localhostTokenValidator = new LocalhostTokenValidator();

    public static List<AuthorizationValidator> allValidators = Arrays.asList(passwordValidator, bearerTokenValidator, cookieValidator, localhostTokenValidator);

    public static void removeValidator(AuthorizationValidator authorizationValidator){
        allValidators.remove(authorizationValidator);
    }

    public static void addValidator(AuthorizationValidator authorizationValidator){
        allValidators.add(authorizationValidator);
    }


}
