package by.itechartgroup.shirochina.anastasiya.tests;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

public class TestHelper {
    private static BrowserContext context;
    private static Page page;
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
}
