package by.itechartgroup.shirochina.anastasiya.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DownloadPage extends BasePage {
    private final Locator installButton;
    public DownloadPage(Page page) {
        super(page);
        this.installButton = page.locator("//div[contains(@class, 'about_install')]/a");
    }
    public Locator getInstallButton() {
        return installButton;
    }
}
