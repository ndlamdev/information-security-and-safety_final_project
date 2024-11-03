package db;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

public class DBProperties {
    public static Properties prop = new Properties();
    private static String pathFile;

    static {
        setUp();

        File file = new File(pathFile);
        if (file.exists()) {
            try {
                prop.load(new FileInputStream(file));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void setUp(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        if (classLoader == null) {
            classLoader = DBProperties.class.getClassLoader();
        }

        pathFile = classLoader.getResource("db.properties").getFile();
    }

    private static String host = prop.getProperty("db.host"),
            port = prop.getProperty("db.port"),
            username = prop.getProperty("db.username"),
            password = prop.getProperty("db.password"),
            name = prop.getProperty("db.tableName");

    public static String getDbHost() {
        return host;
    }

    public static String getDbPort() {
        return port;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getDbName() {
        return name;
    }
}
