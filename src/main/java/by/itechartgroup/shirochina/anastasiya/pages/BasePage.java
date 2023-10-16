package by.itechartgroup.shirochina.anastasiya.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BasePage {
    private String url = "https://store.steampowered.com/";
    private final Locator installButton;
    public Page page;
    public BasePage(Page page) {
        this.page = page;
        this.installButton = page.locator("//a[@class='header_installsteam_btn header_installsteam_btn_green']");
    }
    public void getBaseUrl() {
        page.navigate(url);
    }
    public Locator getInstallButton() {
        return installButton;
    }
}
