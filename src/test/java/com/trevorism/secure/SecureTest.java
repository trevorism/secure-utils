package com.trevorism.secure;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SecureTest {

    @Test
    public void testSecureHasDefault(){
        Secure secure = new SecuredMethod().getClass().getMethods()[0].getAnnotation(Secure.class);
        assertEquals(Roles.USER, secure.value());
        assertEquals(false, secure.allowInternal());
        assertEquals(false, secure.authorizeAudience());
    }

    public class SecuredMethod {
        @Secure()
        public void doSomething() {

        }
    }
}
