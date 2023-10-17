package by.itechartgroup.shirochina.anastasiya.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HeaderPage extends BasePage{
    private final Locator installButton;
    public HeaderPage(Page page) {
        super(page);
        this.installButton = page.locator("//a[@class='header_installsteam_btn header_installsteam_btn_green']");
    }
    public Locator getInstallButton() {
        return installButton;
    }
}
