package com.trevorism.secure;

import org.junit.Test;

import static org.junit.Assert.*;

public class PropertiesProviderTest {

    @Test
    public void getProperty() {
        PropertiesProvider provider = new PropertiesProvider();
        assertEquals("5555-44-123456-99", provider.getProperty("clientId"));
    }
}