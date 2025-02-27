package edu.matc.util;

import java.io.IOException;
import java.util.Properties;

public class DatabasePropertiesLoader implements PropertiesLoader {
    @Override
    public Properties loadProperties() {
        Properties properties = new Properties();

        try {
            properties.load(this.getClass().getResourceAsStream("/database.properties"));
        } catch (IOException ioe) {
            System.out.println("Database.loadProperties()...Cannot load the properties file");
            ioe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Database.loadProperties()..." + e);
            e.printStackTrace();
        }
        return properties;
    }
}
