package com.trevorism.secure;

import java.io.IOException;
import java.util.Properties;

public class PropertiesProvider {

    private final String fileName;
    private final Properties properties = new Properties();

    public PropertiesProvider() {
        this("secrets.properties");
    }

    public PropertiesProvider(String fileName) {
        this.fileName = fileName;
        try {
            properties.load(PropertiesProvider.class.getClassLoader().getResourceAsStream(fileName));
        } catch (IOException ignored) {

        }
    }

    public String getProperty(String name){
        return properties.getProperty(name);
    }
}
