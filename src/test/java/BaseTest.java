import by.itechartgroup.shirochina.anastasiya.pages.CategoriesPage;
import by.itechartgroup.shirochina.anastasiya.pages.NewAndTrendingPage;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

public class BaseTest {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;
    CategoriesPage categoriesPage;
    NewAndTrendingPage newAndTrendingPage;

    @BeforeAll
    public static void launchBrowser() throws IOException {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @AfterAll
    public static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() throws Exception {
        context =  browser.newContext(new Browser.NewContextOptions().setLocale("en-US"));
        page = context.newPage();
        categoriesPage = new CategoriesPage(page);
        newAndTrendingPage = new NewAndTrendingPage(page);
    }

    @AfterEach
    void closeContext() {
        context.close();
    }
}
