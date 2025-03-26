package com.jingjiegao.rs.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * The type Database properties loader.
 */
public class DatabasePropertiesLoader implements PropertiesLoader {
    private static final Logger logger = LogManager.getLogger(DatabasePropertiesLoader.class);

    @Override
    public Properties loadProperties() {
        Properties properties = new Properties();

        try {
            properties.load(this.getClass().getResourceAsStream("/database.properties"));
        } catch (IOException ioe) {
            logger.error("Cannot load the properties file", ioe);
        } catch (Exception e) {
            logger.error("Exception in loadProperties method: {}", e.getMessage(), e);
        }
        return properties;
    }
}
