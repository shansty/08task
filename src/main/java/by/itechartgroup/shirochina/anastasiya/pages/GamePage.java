package by.itechartgroup.shirochina.anastasiya.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class GamePage extends BasePage{
    private final Locator saleLocator;
    private final Locator pricelocator;
    private final Locator installButton;
    private final Locator bundleBaseSale;
    public GamePage(Page page) {
        super(page);
        this.saleLocator = page.locator("//div[@id='game_area_purchase']//div[@class = 'discount_pct']");
        this.bundleBaseSale = page.locator("//div[@id='game_area_purchase']//div[@class='bundle_base_discount']");
        this.pricelocator = page.locator("//div[@class = 'game_purchase_price price']");
        this.installButton = page.locator("//a[@class='header_installsteam_btn header_installsteam_btn_green']");
    }
    public Locator getSaleLocator() {
        return saleLocator;
    }

    public Locator getPricelocator() {
        return pricelocator;
    }

    public Locator getInstallButton() {
        return installButton;
    }

    public Locator getBundleBaseSale() {
        return bundleBaseSale;
    }
}
