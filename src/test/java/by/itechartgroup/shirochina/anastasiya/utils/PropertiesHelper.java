package by.itechartgroup.shirochina.anastasiya.utils;

import org.apache.logging.log4j.Level;

import java.io.IOException;

public class PropertiesHelper {
    private static void readProperty(String property) throws IOException {
        String propertyName = System.getProperty(property);
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("application.properties"));
        if (propertyName != null) {
            System.setProperty(property, propertyName);
        }
    }
    public static String getBrowser() throws IOException {
        readProperty("browser.name");
        return System.getProperty("browser.name");
    }
    public static boolean getBrowserHeadless() throws IOException {
        readProperty("browser.headless");
        return Boolean.parseBoolean(System.getProperty("browser.headless"));
    }
    public static Level getLogLevel() throws IOException {
        readProperty("log.level");
        return Level.valueOf(System.getProperty("log.level"));
    }
    public static String getDirName() throws IOException {
        readProperty("log.dir");
        return System.getProperty("log.dir");
    }
    public static String getFileName() throws IOException {
        readProperty("log.file.name");
        return System.getProperty("log.file.name");
    }
    public static boolean getPreserve() throws IOException {
        readProperty("log.file.preserve");
        return Boolean.parseBoolean(System.getProperty("log.file.preserve"));
    }
    public static boolean getEnabled() throws IOException {
        readProperty("log.file.enabled");
        return Boolean.parseBoolean(System.getProperty("log.file.enabled"));
    }
    public static String getDownloadDir() throws IOException {
        readProperty("download.dir");
        return System.getProperty("download.dir");
    }
}
