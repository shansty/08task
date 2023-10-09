package by.itechartgroup.shirochina.anastasiya.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


public class NewAndTrendingPage extends BasePage {
    private final Locator newAndTrendingButton;
    private final Locator salesLocators;
    private final Locator pricesLocators;
    private String additionToLocator = "../../../..//div[contains(@class, 'StoreSaleWidgetTitle')]/..";

    public NewAndTrendingPage(Page page) {
        super(page);
        this.newAndTrendingButton = page.locator("//div[@class='saleitembrowser_FlavorLabel_Dhg57 Focusable' and text() ='New & Trending']");
        this.salesLocators = page.locator("//div[contains(@class, 'saleitembrowser_SaleItemBrowserContainer')]//div[contains(@class, 'salepreviewwidgets_StoreSaleDiscountBox')]");
        this.pricesLocators = page.locator("//div[contains(@class, 'saleitembrowser_SaleItemBrowserContainer')]//div[contains(@class, 'salepreviewwidgets_StoreSalePriceBox')]");
    }

    public Locator getNewAndTrendingButton() {
        return newAndTrendingButton;
    }

    public Locator getSalesLocators() {
        return salesLocators;
    }

    public Locator getPricesLocators() {
        return pricesLocators;
    }

    public String getAdditionToLocator() {
        return additionToLocator;
    }
}
