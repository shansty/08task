package by.itechartgroup.shirochina.anastasiya.utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class BrowserHelper {

    public static Browser getBrowserSetting(Playwright playwright) {
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(PropertiesHelper.getBrowserHeadless());
        String browserName = PropertiesHelper.getBrowser();
        System.out.println(browserName + "DB");
        switch (browserName) {
            case "chromium":
                System.out.println("ch");
                return playwright.chromium().launch(launchOptions);
            case "firefox":
                System.out.println("fr");
                return playwright.firefox().launch(launchOptions);
            case "webkit":
                System.out.println("wk");
                return playwright.webkit().launch(launchOptions);
            default:
                return null;
        }
    }
}
