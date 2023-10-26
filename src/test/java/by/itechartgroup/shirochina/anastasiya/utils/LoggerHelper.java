package by.itechartgroup.shirochina.anastasiya.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class LoggerHelper {
    private static String logFileName;

    public static void installLogger() {
        LoggerContext contextLogger = (LoggerContext) LogManager.getContext(false);
        Configuration config = contextLogger.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig("by.itechartgroup.shirochina.anastasiya.tests");
        loggerConfig.setLevel(PropertiesHelper.getLogLevel());
        String logDirectory;
        if (!PropertiesHelper.getDirName().isEmpty()) {
            logDirectory = PropertiesHelper.getDirName();
        } else {
            logDirectory = "logs";
        }
        if (!PropertiesHelper.getFileName().isEmpty()) {
            logFileName = PropertiesHelper.getFileName();
            setLogFileName(logFileName);
        } else {
            logFileName = "testSteamWeb.log";
            setLogFileName(logFileName);
        }
        boolean append = PropertiesHelper.getPreserve();
        if (append) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss");
            String formattedTime = formatter.format(timestamp.getTime());
            logFileName = logFileName + formattedTime;
            setLogFileName(logFileName);
        }
        boolean enabled = PropertiesHelper.getEnabled();
        if (enabled) {
            FileAppender fa = FileAppender.newBuilder().withName("NewLogToFile").withAppend(append).withFileName(String.valueOf(new File(logDirectory, logFileName)))
                    .withLayout(PatternLayout.newBuilder().withPattern("%-5p %d  [%t] %C{2} (%F:%L) - %m%n").build())
                    .setConfiguration(contextLogger.getConfiguration()).build();
            loggerConfig.addAppender(fa, null, null);
            config.addAppender(fa);
            contextLogger.updateLoggers();
            fa.start();
        }
    }
    public static String getLogFileName() {
        return logFileName;
    }

    public static void setLogFileName(String logFileName) {
        LoggerHelper.logFileName = logFileName;
    }
}
