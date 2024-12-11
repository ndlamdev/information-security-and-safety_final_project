package com.lamnguyen.mat_kinh_kimi.config.mail;

import lombok.Getter;

import java.io.*;
import java.util.Properties;

public class SendMailProperties {
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

        if (classLoader == null)
            classLoader = SendMailProperties.class.getClassLoader();


        var url = classLoader.getResource("send_mail.properties");
        if (url == null) throw new NullPointerException();
        pathFile = url.getFile();
    }

    @Getter
    private static final String email = prop.getProperty("send_mail.email");
    @Getter
    private static String password = prop.getProperty("send_mail.password");
}
