package com.jingjiegao.rs.util;

import java.util.Properties;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The interface Properties loader.
 */
public interface PropertiesLoader{

    /**
     * The constant logger.
     */
    Logger logger = LogManager.getLogger(PropertiesLoader.class);

    /**
     * Load properties properties.
     *
     * @param propertiesFilePath the properties file path
     * @return the properties
     */
    default Properties loadProperties(String propertiesFilePath) {
        Properties properties = new Properties();

        try {
            properties.load(this.getClass().getResourceAsStream(propertiesFilePath));
        } catch (IOException ioe) {
            logger.error("Cannot load the properties file", ioe);
        } catch (Exception e) {
            logger.error("Exception in loadProperties method: {}", e.getMessage(), e);
        }

        return properties;
    }
}