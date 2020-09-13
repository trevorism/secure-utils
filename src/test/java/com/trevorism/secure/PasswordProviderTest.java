package com.trevorism.secure;

import org.junit.Test;

import javax.ws.rs.core.HttpHeaders;

import static org.junit.Assert.*;

/**
 * @author tbrooks
 */
public class PasswordProviderTest {

    @Test
    public void testValues(){
        assertEquals("Authorization", HttpHeaders.AUTHORIZATION);
        assertEquals("test", PasswordProvider.getInstance().getPassword());
        assertEquals("signingtest", PasswordProvider.getInstance().getSigningKey());
    }

}