package by.itechartgroup.shirochina.anastasiya.utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class BrowserHelper {

    public static Browser getBrowserSetting(Playwright playwright) {
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(PropertiesHelper.getBrowserHeadless());
        String browserName = PropertiesHelper.getBrowser();
        switch (browserName) {
            case "chrome":
                return playwright.chromium().launch(launchOptions);
            case "firefox":
                return playwright.firefox().launch(launchOptions);
            case "webkit":
                return playwright.webkit().launch(launchOptions);
            default:
                return null;
        }
    }
}
