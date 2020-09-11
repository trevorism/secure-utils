package com.trevorism.secure;

import org.junit.Test;

import static org.junit.Assert.*;

public class RolesTest {

    @Test
    public void validate() {
        assertTrue(Roles.validate(Roles.GLOBAL_ADMIN));
        assertTrue(Roles.validate(Roles.TENANT_ADMIN));
        assertTrue(Roles.validate(Roles.CREATE));
        assertTrue(Roles.validate(Roles.READ));
        assertTrue(Roles.validate(Roles.UPDATE));
        assertTrue(Roles.validate(Roles.DELETE));
        assertTrue(Roles.validate(Roles.EXECUTE));

        assertFalse(Roles.validate(""));
        assertFalse(Roles.validate("blah"));
        assertFalse(Roles.validate(null));

    }
}