package by.itechartgroup.shirochina.anastasiya.tests;

import by.itechartgroup.shirochina.anastasiya.pages.BasePage;
import by.itechartgroup.shirochina.anastasiya.pages.MainPage;
import by.itechartgroup.shirochina.anastasiya.utils.BrowserHelper;
import by.itechartgroup.shirochina.anastasiya.utils.LoggerHelper;
import by.itechartgroup.shirochina.anastasiya.utils.PropertiesHelper;
import com.microsoft.playwright.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

public class BaseTest {
    static Playwright playwright;
    static Browser browser;
    static Logger logger;
    BrowserContext context;
    Page page;
    MainPage categoriesPage;
    BasePage basePage;
    MainPage mainPage;

    @BeforeAll
    public static void launchBrowser() throws IOException {
        playwright = Playwright.create();
        PropertiesHelper.readProperty();
        LoggerHelper.installLogger();
        logger = LogManager.getLogger();
        if (!BrowserHelper.getBrowserSetting(playwright).equals(null)) {
            browser = BrowserHelper.getBrowserSetting(playwright);
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
