package by.itechartgroup.shirochina.anastasiya.utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class BrowserHelper {

    public static Browser getBrowserSetting(Playwright playwright) {
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(PropertiesHelper.getBrowserHeadless());
        String browserName = PropertiesHelper.getBrowser();
        if (browserName.equals("chrome")) {
            return playwright.chromium().launch(launchOptions);
        } else if (browserName.equals("firefox")) {
            return playwright.firefox().launch(launchOptions);
        } else if (browserName.equals("webkit")) {
            return playwright.webkit().launch(launchOptions);
        } else {
            return null;
        }
    }
}
