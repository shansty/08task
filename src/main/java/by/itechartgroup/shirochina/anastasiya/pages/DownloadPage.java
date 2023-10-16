package by.itechartgroup.shirochina.anastasiya.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DownloadPage extends BasePage {
    private final Locator installButton;
    public DownloadPage(Page page) {
        super(page);
        this.installButton = page.locator("//div[@class='about_install win ']/a");
    }
    public Locator getInstallButton() {
        return installButton;
    }
}
