package by.itechartgroup.shirochina.anastasiya.utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import org.apache.logging.log4j.Logger;

public class BrowserHelper {

    public static Browser getBrowserSetting(Playwright playwright) {
        Browser browser = null;
        if (PropertiesHelper.getBrowser().equals("chrome")) {
            return browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(PropertiesHelper.getBrowserHeadless()));
        } else if (PropertiesHelper.getBrowser().equals("firefox")) {
            return browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(PropertiesHelper.getBrowserHeadless()));
        } else if (PropertiesHelper.getBrowser().equals("webkit")) {
            return browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(PropertiesHelper.getBrowserHeadless()));
        } else {
            return browser;
        }
    }
}
