package by.itechartgroup.shirochina.anastasiya.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class LoggerHelper {
    private static LoggerContext contextLoger;
    private static Configuration config;
    private static LoggerConfig loggerConfig;

    public static void installLogger() {
        contextLoger = (LoggerContext) LogManager.getContext(false);
        config = contextLoger.getConfiguration();
        loggerConfig = config.getLoggerConfig("by.itechartgroup.shirochina.anastasiya.tests");
        loggerConfig.setLevel(PropertiesHelper.getLogLevel());
        String logDirectory;
        String logFileName;
        if (!PropertiesHelper.getDirName().isEmpty()) {
            logDirectory = PropertiesHelper.getDirName();
        } else {
            logDirectory = "logs";
        }
        if (!PropertiesHelper.getFileName().isEmpty()) {
            logFileName = PropertiesHelper.getFileName();
        } else {
            logFileName = "testSteamWeb.log";
        }
        boolean append = PropertiesHelper.getPreserve();
        if (append == true) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss");
            String formattedTime = formatter.format(timestamp.getTime());
            logFileName = logFileName + formattedTime;
        }
        boolean enabled = PropertiesHelper.getEnabled();
        if (enabled == true) {
            FileAppender fa = FileAppender.newBuilder().withName("NewLogToFile").withAppend(append).withFileName(String.valueOf(new File(logDirectory, logFileName)))
                    .withLayout(PatternLayout.newBuilder().withPattern("%-5p %d  [%t] %C{2} (%F:%L) - %m%n").build())
                    .setConfiguration(contextLoger.getConfiguration()).build();
            loggerConfig.addAppender(fa, null, null);
            config.addAppender(fa);
            contextLoger.updateLoggers();
            fa.start();
        } else {
        }
    }
}
