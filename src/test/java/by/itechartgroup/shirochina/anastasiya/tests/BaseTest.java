package by.itechartgroup.shirochina.anastasiya.tests;

import by.itechartgroup.shirochina.anastasiya.pages.BasePage;
import by.itechartgroup.shirochina.anastasiya.pages.MainPage;
import by.itechartgroup.shirochina.anastasiya.utils.BrowserHelper;
import by.itechartgroup.shirochina.anastasiya.utils.LoggerHelper;
import by.itechartgroup.shirochina.anastasiya.utils.PropertiesHelper;
import com.microsoft.playwright.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;


@ExtendWith(MyTestWatcher.class)
public class BaseTest {
    static Playwright playwright;
    static Browser browser;
    static Logger logger;
    static BrowserContext context;
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
        browser = BrowserHelper.getBrowserSetting(playwright);
        if (!browser.equals(null)) {
            logger.info("Browser is initialized");
        } else {
            logger.error("Browser name not found");
        }
    }
    @AfterAll
    public static void closeBrowser() {
        context.close();
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        logger.info("Test started");
        context =  browser.newContext(new Browser.NewContextOptions().setLocale("en-US"));
        context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true).setSources(true));
        page = context.newPage();
        basePage = new BasePage(page);
        mainPage = new MainPage(page);
        categoriesPage = new MainPage(page);
        TestHelper.setContext(context);
        TestHelper.setPage(page);
    }

    @AfterEach
    void closeContext() {
        logger.info("Test ended");
    }
}
