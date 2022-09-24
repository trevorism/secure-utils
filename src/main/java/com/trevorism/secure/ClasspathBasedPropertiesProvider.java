package com.trevorism.secure;

import java.io.IOException;
import java.util.Properties;

public class ClasspathBasedPropertiesProvider implements PropertiesProvider {
    private final Properties properties = new Properties();

    public ClasspathBasedPropertiesProvider() {
        this("secrets.properties");
    }

    public ClasspathBasedPropertiesProvider(String fileName) {
        try {
            properties.load(PropertiesProvider.class.getClassLoader().getResourceAsStream(fileName));
        } catch (IOException ignored) {

        }
    }

    @Override
    public String getProperty(String name) {
        return properties.getProperty(name);
    }
}
