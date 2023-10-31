package by.itechartgroup.shirochina.anastasiya.utils;

import org.apache.logging.log4j.Level;

import java.io.IOException;

public class PropertiesHelper {
    public static void readProperty() throws IOException {
        System.out.println("DEBUG browser name");
        System.out.println(System.getProperty("browser.name"));

        String browserName = System.getProperty("browser.name");
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("application.properties"));

        if (browserName != null) {
            System.setProperty("browser.name", browserName);
        }

        System.out.println("DEBUG browser name2");
        System.out.println(System.getProperty("browser.name"));

    }
    public static String getBrowser() {
        return System.getProperty("browser.name");
    }
    public static boolean getBrowserHeadless() {
        return Boolean.parseBoolean(System.getProperty("browser.headless"));
    }
    public static Level getLogLevel() {
        return Level.valueOf(System.getProperty("log.level"));
    }
    public static String getDirName() {
        return System.getProperty("log.dir");
    }
    public static String getFileName() {
        return System.getProperty("log.file.name");
    }
    public static boolean getPreserve() {
        return Boolean.parseBoolean(System.getProperty("log.file.preserve"));
    }
    public static boolean getEnabled() {
        return Boolean.parseBoolean(System.getProperty("log.file.enabled"));
    }
    public static String getDownloadDir() {
        return System.getProperty("download.dir");
    }
}
