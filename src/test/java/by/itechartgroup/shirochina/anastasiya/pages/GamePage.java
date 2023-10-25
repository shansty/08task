package by.itechartgroup.shirochina.anastasiya.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class GamePage extends BasePage{
    public GamePage(Page page) {
        super(page);
    }

    public Locator getSaleLocatorByDiscount(String sale) {
        sale = sale.replaceAll("[^0-9.]+", "");
        return page.locator("//div[@class='discount_block game_purchase_discount']//div[@class = 'discount_pct' and contains(text()," + sale + ")]");
    }
    public Locator getPriceLocatorBySalesPrice(String salesPrice) {
        salesPrice = salesPrice.replaceAll("[^0-9.,]+", "");
        return page.locator("//div[@class='discount_block game_purchase_discount']//div[@class = 'discount_final_price' and contains(text(),'" + salesPrice + "')]");
    }
    public Locator getPriceLocatorByPrice(String price) {
        price = price.replaceAll("[^0-9.]+", "");
        return page.locator("//div[@class = 'game_purchase_price price' and contains(text()," + price + ")]");
    }
}
