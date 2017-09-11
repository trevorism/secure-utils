package com.trevorism.secure;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author tbrooks
 */
public class PasswordProviderTest {

    @Test
    public void testValues(){
        assertEquals("Authorization", PasswordProvider.AUTHORIZATION_HEADER);
        assertEquals("test", PasswordProvider.PASSWORD);

    }

}