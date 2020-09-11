package com.trevorism.secure;

import java.util.Arrays;
import java.util.List;

public class Roles {

    public static final String GLOBAL_ADMIN = "super_admin";
    public static final String TENANT_ADMIN = "admin";
    public static final String CREATE = "creator";
    public static final String READ = "reader";
    public static final String UPDATE = "updater";
    public static final String DELETE = "deleter";
    public static final String EXECUTE = "executor";

    public static final List<String> allRoles = Arrays.asList(GLOBAL_ADMIN, TENANT_ADMIN, CREATE, READ, UPDATE, DELETE, EXECUTE);

    public static boolean validate(String role){
        return allRoles.contains(role);
    }
}
