package by.itechartgroup.shirochina.anastasiya.utils;

import org.apache.logging.log4j.Level;

import java.io.IOException;

public class PropertiesHelper {
    public static void readProperty() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("application.properties"));
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