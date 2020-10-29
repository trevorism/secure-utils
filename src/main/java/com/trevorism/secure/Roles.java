package com.trevorism.secure;

import java.util.Arrays;
import java.util.List;

public class Roles {

    public static final String ADMIN = "admin";
    public static final String INTERNAL = "internal";
    public static final String SYSTEM = "system";
    public static final String USER = "user";

    public static final List<String> allRoles = Arrays.asList(ADMIN, SYSTEM, USER, INTERNAL);

    public static boolean validate(String role){
        return allRoles.contains(role);
    }
}
