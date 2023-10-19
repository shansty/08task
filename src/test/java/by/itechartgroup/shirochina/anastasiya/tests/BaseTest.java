package by.itechartgroup.shirochina.anastasiya.tests;

import by.itechartgroup.shirochina.anastasiya.pages.BasePage;
import by.itechartgroup.shirochina.anastasiya.pages.MainPage;
import com.microsoft.playwright.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class BaseTest {
    static Playwright playwright;
    static Browser browser;
    static Logger logger;
    static LoggerContext contextLoger;
    static Configuration config;
    static LoggerConfig loggerConfig;
    BrowserContext context;
    Page page;
    MainPage categoriesPage;
    BasePage basePage;
    MainPage mainPage;

    @BeforeAll
    public static void launchBrowser() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("application.properties"));
        logger = LogManager.getLogger();
        contextLoger = (LoggerContext) LogManager.getContext(false);
        config = contextLoger.getConfiguration();
        loggerConfig = config.getLoggerConfig("by.itechartgroup.shirochina.anastasiya.tests");
        loggerConfig.setLevel(Level.valueOf(System.getProperty("log.level")));
        String logDirectory;
        String logFileName;
        if (!System.getProperty("log.dir").isEmpty()) {
            logDirectory = System.getProperty("log.dir");
        } else {
            logDirectory = "logs";
        }
        if (!System.getProperty("log.file.name").isEmpty()) {
            logFileName = System.getProperty("log.file.name");
        } else {
            logFileName = "testSteamWeb.log";
        }
        boolean append = Boolean.parseBoolean(System.getProperty("log.file.preserve"));
        if(append == true) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss");
            String formattedTime = formatter.format(timestamp.getTime());
            logFileName = logFileName + formattedTime;
        }
        FileAppender fa = FileAppender.newBuilder().withName("NewLogToFile").withAppend(append).withFileName(new File(logDirectory, logFileName).toString())
                .withLayout(PatternLayout.newBuilder().withPattern("%-5p %d  [%t] %C{2} (%F:%L) - %m%n").build())
                .setConfiguration(contextLoger.getConfiguration()).build();
        loggerConfig.addAppender(fa, null, null);
        contextLoger.updateLoggers();

        playwright = Playwright.create();
        if (System.getProperty("browser.name").equals("chrome")) {
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(System.getProperty("browser.headless"))));
        } else if (System.getProperty("browser.name").equals("firefox")) {
            browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(System.getProperty("browser.headless"))));
        } else if (System.getProperty("browser.name").equals("webkit")) {
            browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(System.getProperty("browser.headless"))));
        } else {
            logger.error("Browser name not found");
        }
    }


    @AfterAll
    public static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        logger.info("Test started");
        context =  browser.newContext(new Browser.NewContextOptions().setLocale("en-US"));
        page = context.newPage();
        basePage = new BasePage(page);
        mainPage = new MainPage(page);
        categoriesPage = new MainPage(page);
    }

    @AfterEach
    void closeContext() {
        logger.info("Test ended");
        context.close();
    }
}
