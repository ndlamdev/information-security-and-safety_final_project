package com.lamnguyen.mat_kinh_kimi.config.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBProperties {
    public static Properties prop = new Properties();
    private static String pathFile;

    static {
        setUp();

        File file = new File(pathFile);
        if (file.exists()) {
            try {
                prop.load(new FileInputStream(file));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void setUp() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        if (classLoader == null) {
            classLoader = DBProperties.class.getClassLoader();
        }

        var url = classLoader.getResource("db.properties");
        if (url == null) throw new NullPointerException();
        pathFile = url.getFile();
    }

    private static final String HOST = prop.getProperty("db.host");
    private static final String PORT = prop.getProperty("db.port");
    private static final String USERNAME = prop.getProperty("db.username");
    private static final String PASSWORD = prop.getProperty("db.password");
    private static final String NAME = prop.getProperty("db.tableName");

    public static String getDbHost() {
        return HOST;
    }

    public static String getDbPort() {
        return PORT;
    }

    public static String getUsername() {
        return USERNAME;
    }

    public static String getPassword() {
        return PASSWORD;
    }

    public static String getDbName() {
        return NAME;
    }
}
