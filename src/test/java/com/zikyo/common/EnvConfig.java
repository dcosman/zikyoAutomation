package com.zikyo.common;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvConfig extends Properties {
    private static final Logger LOGGER = Logger.getLogger(EnvConfig.class);

    public static final String RESOURCES_PATH = "src/test/resources/";
    private static final String INIT_CONFIG_PATH = RESOURCES_PATH + "defaultTestConfig.properties";
    private static Properties initProperties;

    public EnvConfig() {
        try {
            testEnvironment = System.getProperty(ENV_PROPERTY, DEFAULT_ENV);
            browser = System.getProperty(BROWSER_PROPERTY, DEFAULT_BROWSER);
            browserConfigPath = browser + ".properties";
            testConfigPath = RESOURCES_PATH + "environments/" + testEnvironment + "/test-config.properties";

            FileInputStream fileInputStream = new FileInputStream(testConfigPath);
            load(fileInputStream);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    static {
        try {
            FileInputStream initPropertiesStream = new FileInputStream(INIT_CONFIG_PATH);
            initProperties = new Properties();
            initProperties.load(initPropertiesStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String testEnvironment;
    public static String testConfigPath;
    public static String browser;
    public static String browserConfigPath;

    public static final String ENV_PROPERTY = "env";
    public static final String BROWSER_PROPERTY = "browser";
    public static final String DEFAULT_ENV = initProperties.getProperty(ENV_PROPERTY);
    public static final String DEFAULT_BROWSER = initProperties.getProperty(BROWSER_PROPERTY);

    static EnvConfig envConfig = new EnvConfig();

    public String getProperty(String key, boolean log) {
        String property = System.getProperty(key, super.getProperty(key));
        if (log)
            LOGGER.debug("getProperty: " + key + " = " + property);
        return property;
    }

    public String getProperty(String key) {
        return getProperty(key, false);
    }

}
