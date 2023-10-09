package by.itechartgroup.shirochina.anastasiya.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DownloadPage extends BasePage {
    private final Locator installButton;
    public DownloadPage(Page page) {
        super(page);
        this.installButton = page.locator("//a[@href='https://cdn.cloudflare.steamstatic.com/client/installer/SteamSetup.exe']");
    }
    public Locator getInstallButton() {
        return installButton;
    }
}
