package com.trevorism.secure;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SecureTest {

    @Test
    public void testSecureHasDefault(){
        Secure secure = new SecuredMethod().getClass().getMethods()[0].getAnnotation(Secure.class);
        assertEquals(Roles.USER, secure.value());
        assertFalse(secure.allowInternal());
        assertFalse(secure.authorizeAudience());
        assertTrue(secure.permissions().isEmpty());
    }

    public class SecuredMethod {
        @Secure()
        public void doSomething() {

        }
    }
}
