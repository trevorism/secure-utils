package com.trevorism.secure;

import org.junit.Test;

import static org.junit.Assert.*;

public class PropertiesProviderTest {

    @Test
    public void getProperty() {
        PropertiesProvider provider = new ClasspathBasedPropertiesProvider();
        assertEquals("5555-44-123456-99", provider.getProperty("clientId"));
    }

    @Test
    public void getPropertyFromAnotherFile() {
        PropertiesProvider provider = new ClasspathBasedPropertiesProvider("another.properties");
        assertEquals("12312-456-32424", provider.getProperty("clientId"));
    }
}