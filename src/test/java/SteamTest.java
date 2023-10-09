import by.itechartgroup.shirochina.anastasiya.Categories;
import by.itechartgroup.shirochina.anastasiya.pages.*;
import by.itechartgroup.shirochina.anastasiya.utils.Helper;
import by.itechartgroup.shirochina.anastasiya.utils.Navigation;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SteamTest extends BaseTest {
    @Test
    public void testSteamWeb() {
        page.navigate("https://store.steampowered.com/");
        page.hover(categoriesPage.getCategoriesButton());
        page.locator(Navigation.getLocatorByCategoriesName(Categories.ACTION)).click();
        page.waitForLoadState(LoadState.LOAD);
        page.mouse().wheel(0, 3000);
        newAndTrendingPage.getNewAndTrendingButton().click();

        //Клик на игру с самой большой скидкой или самой большой ценой
        page.waitForLoadState(LoadState.NETWORKIDLE);
        List<Locator> listOfSalesLocators = newAndTrendingPage.getSalesLocators().all();
        String maxSale = null;
        String maxPrice = null;
        Page newPage;
        if (listOfSalesLocators.size() > 0) {
            Optional<Locator> maxSaleLocator = listOfSalesLocators.stream().reduce((x, y) -> Helper.getLocatorWithMaxSale(x, y));
            maxSale = maxSaleLocator.get().textContent();
            page.waitForLoadState(LoadState.NETWORKIDLE);
            newPage = context.waitForPage(() -> {
                maxSaleLocator.get().locator(newAndTrendingPage.getAdditionToLocator()).click();
            });
            newPage.waitForLoadState(LoadState.LOAD);
        } else {
            List<Locator> listOfPricesLocators = newAndTrendingPage.getPricesLocators().all();
            Optional<Locator> maxPriceLocator = listOfPricesLocators.stream().reduce((x, y) -> Helper.getLocatorWithMaxPrice(x, y));
            maxPrice = maxPriceLocator.get().textContent();
            page.waitForLoadState(LoadState.NETWORKIDLE);
            newPage = context.waitForPage(() -> {
                maxPriceLocator.get().locator(newAndTrendingPage.getAdditionToLocator()).click();
            });
            newPage.waitForLoadState(LoadState.LOAD);
        }

        //обработка возраста, если она есть
        AgeConfirmationPage ageConfirmationPage = new AgeConfirmationPage(newPage);
        if (ageConfirmationPage.getNotificationText().isVisible()) {
            ageConfirmationPage.getDayOfBirthSelect().selectOption("16");
            ageConfirmationPage.getMonthOfBirthSelect().selectOption("May");
            ageConfirmationPage.getYearOfBirthSelect().selectOption("1999");
            ageConfirmationPage.getConfirmButton().click();
            newPage.waitForLoadState(LoadState.LOAD);
        }

        //проверка максимальной скидки или цены
        GamePage gamePage = new GamePage(newPage);
        if (maxSale != null) {
            if (gamePage.getSaleLocator().nth(0).isVisible()) {
                assertThat(gamePage.getSaleLocator().nth(0)).containsText(maxSale);
            } else {
                assertThat(gamePage.getBundleBaseSale().nth(0)).containsText(maxSale);
            }
        } else {
            assertThat(gamePage.getPricelocator().nth(0)).containsText(maxPrice);
        }

        //клик на  кнопке install steam
        gamePage.getInstallButton().click();
        newPage.waitForLoadState(LoadState.LOAD);
        DownloadPage downloadPage = new DownloadPage(newPage);

        //скачиваем файл и изменяем его имя
        Download download = newPage.waitForDownload(() -> {
            downloadPage.getInstallButton().nth(0).click();
        });
        Assertions.assertTrue(download!=null);
        Assertions.assertEquals("SteamSetup.exe", download.suggestedFilename());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss");
        String formattedTime = formatter.format(timestamp.getTime());
        Path destinationPath = Paths.get("Download", formattedTime + download.suggestedFilename());
        download.saveAs(destinationPath);
    }
}
