package by.itechartgroup.shirochina.anastasiya.tests;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class TestHelper {
    private static BrowserContext context;
    private static Page page;
    private static Playwright playwright;

    public static BrowserContext getContext() {
        return context;
    }

    public static void setContext(BrowserContext context) {
        TestHelper.context = context;
    }

    public static Page getPage() {
        return page;
    }

    public static void setPage(Page page) {
        TestHelper.page = page;
    }

    public static Playwright getPlaywright() {
        return playwright;
    }

    public static void setPlaywright(Playwright playwright) {
        TestHelper.playwright = playwright;
    }
}
