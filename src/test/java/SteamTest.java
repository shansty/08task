import by.itechartgroup.shirochina.anastasiya.Categories;
import by.itechartgroup.shirochina.anastasiya.pages.*;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SteamTest extends BaseTest {
    @Test
    public void testSteamWeb() {
        basePage.getBaseUrl();
        mainPage.hoverToCategoriesButton();
        mainPage.clickOnCategory(Categories.ACTION);
        mainPage.getGameBlock().nth(1).scrollIntoViewIfNeeded();
        mainPage.getNewAndTrendingButton().click();

        //Клик на игру с самой большой скидкой или самой большой ценой
        page.waitForLoadState(LoadState.NETWORKIDLE);
        List<String> listOfSales = mainPage.getSalesLocators().allInnerTexts();
        String maxSale = null;
        String salesPrice = null;
        String maxPrice = null;
        Page newPage;
        if (listOfSales.size() > 0) {
            List<String> listOfSalesModified = listOfSales.stream()
                    .map(s -> s.replaceAll("[^0-9]", ""))
                    .collect(Collectors.toList());
            List<Integer> listOfSalesInt = listOfSalesModified.stream().map(x -> Integer.parseInt(x))
                    .collect(Collectors.toList());
            Integer max = listOfSalesInt.stream().max(Integer::compareTo).get();
            maxSale = "-" + String.valueOf(max) + "%";
            salesPrice = mainPage.getPriceLocatorBySale(max).textContent();
            newPage = context.waitForPage(() -> {
                mainPage.getLinkLocatorBySale(max).nth(0).click();
            });

        } else {
            List<String> listOfPrices = mainPage.getPricesLocators().allInnerTexts();
            List<String> listOfPricesModified = listOfPrices.stream()
                    .map(s -> s.replaceAll("[^0-9]", ""))
                    .collect(Collectors.toList());
            List<Double> listOfPricesDouble = listOfPricesModified.stream().map(x -> Double.parseDouble(x))
                    .collect(Collectors.toList());
            Double max = listOfPricesDouble.stream().max(Double::compareTo).get();
            maxPrice = mainPage.getPriceLocatorByPrice(max).textContent();
            newPage = context.waitForPage(() -> {
                mainPage.getLinkLocatorByPrice(max).nth(0).click();
            });
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
            gamePage.getSaleLocatorByDiscount(maxSale).scrollIntoViewIfNeeded();
            assertThat(gamePage.getSaleLocatorByDiscount(maxSale)).containsText(maxSale);
            assertThat(gamePage.getPriceLocatorBySalesPrice(salesPrice)).containsText(salesPrice);

        } else {
            assertThat(gamePage.getPriceLocatorByPrice(maxPrice)).containsText(maxPrice);
        }

        //клик на  кнопке install steam
        BasePage newBasePage = new BasePage(newPage);
        newBasePage.getInstallButton().click();
        newPage.waitForLoadState(LoadState.LOAD);
        DownloadPage downloadPage = new DownloadPage(newPage);

        //скачиваем файл и изменяем его имя
        Download download = newPage.waitForDownload(() -> {
            downloadPage.getInstallButton().nth(0).click();
        });
        Assertions.assertNotNull(download);
        Assertions.assertEquals("SteamSetup.exe", download.suggestedFilename());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss");
        String formattedTime = formatter.format(timestamp.getTime());
        Path destinationPath = Paths.get("Download", formattedTime + download.suggestedFilename());
        download.saveAs(destinationPath);
    }
}
