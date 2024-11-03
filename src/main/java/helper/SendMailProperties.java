package helper;

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
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void setUp() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        if (classLoader == null) {
            classLoader = SendMailProperties.class.getClassLoader();
        }

        pathFile = classLoader.getResource("send_mail.properties").getFile();
    }

    private static String
            email = prop.getProperty("send_mail.email"),
            password = prop.getProperty("send_mail.password");


    public static String getEmail() {
        return email;
    }

    public static String getPassword() {
        return password;
    }

}
