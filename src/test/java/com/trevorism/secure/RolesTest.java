package com.trevorism.secure;

import org.junit.Test;

import static org.junit.Assert.*;

public class RolesTest {

    @Test
    public void validate() {
        assertTrue(Roles.validate(Roles.ADMIN));
        assertTrue(Roles.validate(Roles.SYSTEM));
        assertTrue(Roles.validate(Roles.USER));
        assertTrue(Roles.validate(Roles.INTERNAL));

        assertFalse(Roles.validate(""));
        assertFalse(Roles.validate("blah"));
        assertFalse(Roles.validate(null));

    }
}