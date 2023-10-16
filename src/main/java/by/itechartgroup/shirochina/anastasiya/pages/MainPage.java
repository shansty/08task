package by.itechartgroup.shirochina.anastasiya.pages;

import by.itechartgroup.shirochina.anastasiya.Categories;
import by.itechartgroup.shirochina.anastasiya.utils.Navigation;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MainPage extends BasePage {
    private final Locator newAndTrendingButton;
    private final Locator salesLocators;
    private final Locator pricesLocators;
    private String categoriesButton = "//div[@id='genre_tab']";
    private final Locator gameBlock;

    public MainPage(Page page) {
        super(page);
        this.gameBlock = page.locator("//div[@class='partnersaledisplay_SaleSection_2NfLq eventbbcodeparser_SaleSectionCtn_2Xrw_ SaleSectionForCustomCSS']");
        this.newAndTrendingButton = page.locator("//div[@class='saleitembrowser_FlavorLabel_Dhg57 Focusable' and text() ='New & Trending']");
        this.salesLocators = page.locator("//div[contains(@class, 'saleitembrowser_SaleItemBrowserContainer')]//div[contains(@class, 'salepreviewwidgets_StoreSaleDiscountBox')]");
        this.pricesLocators = page.locator("//div[contains(@class, 'saleitembrowser_SaleItemBrowserContainer')]//div[contains(@class, 'salepreviewwidgets_StoreSalePriceBox')]");
    }

    public void hoverToCategoriesButton() {
        page.hover(categoriesButton);
    }

    public void clickOnCategory(Categories categories) {
        page.locator(Navigation.getLocatorByCategoriesName(categories)).click();
    }

    public Locator getGameBlock() {
        return gameBlock;
    }

    public Locator getNewAndTrendingButton() {
        return newAndTrendingButton;
    }

    public Locator getSalesLocators() {
        return salesLocators;
    }

    public Locator getPriceLocatorBySale(int sale) {
        return page.locator("//div[contains(@class, 'salepreviewwidgets_StoreSalePriceActionWidgetContainer')][.//div[contains(@class, 'salepreviewwidgets_StoreSaleDiscountBox') and contains(text()," + sale + ")]]//div[contains(@class, 'salepreviewwidgets_StoreSalePriceBox')]");
    }

    public Locator getLinkLocatorBySale(int sale) {
        return page.locator("//div[contains(@class, 'salepreviewwidgets_SaleItemBrowserRow')][.//div[contains(@class, 'salepreviewwidgets_StoreSaleDiscountBox') and contains(text()," + sale + ")]]//a");
    }

    public Locator getPriceLocatorByPrice(double price) {
        return page.locator("//div[contains(@class, 'salepreviewwidgets_StoreSalePriceBox') and contains(text()," + price + ")]");
    }

    public Locator getLinkLocatorByPrice(double price) {
        return page.locator("//div[contains(@class, 'salepreviewwidgets_SaleItemBrowserRow')][.//div[contains(@class, 'salepreviewwidgets_StoreOriginalPrice') and contains(text()," + price + ")]//a");
    }

    public Locator getPricesLocators() {
        return pricesLocators;
    }

}
