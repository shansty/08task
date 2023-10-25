package by.itechartgroup.shirochina.anastasiya.tests;

import by.itechartgroup.shirochina.anastasiya.Categories;
import by.itechartgroup.shirochina.anastasiya.pages.DownloadPage;
import by.itechartgroup.shirochina.anastasiya.pages.HeaderPage;
import by.itechartgroup.shirochina.anastasiya.pages.AgeConfirmationPage;
import by.itechartgroup.shirochina.anastasiya.pages.GamePage;
import by.itechartgroup.shirochina.anastasiya.utils.PropertiesHelper;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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
        logger.info("Open base url");
        mainPage.getBaseUrl();
        logger.info("Hover to category");
        mainPage.hoverToCategoriesButton();
        logger.info("Click on category");
        mainPage.clickOnCategory(Categories.ACTION);
        logger.debug("Wait for game block");
        mainPage.getGameBlock().waitFor();
        logger.trace("wait for load state");
        page.waitForLoadState();
        logger.debug("Scroll to game block");
        mainPage.getGameBlock().scrollIntoViewIfNeeded();
        logger.info("Click on New and Trending button");
        mainPage.getNewAndTrendingButton().click();

        //Клик на игру с самой большой скидкой или самой большой ценой;
        logger.trace("wait for load state");
        page.waitForLoadState(LoadState.NETWORKIDLE);
        logger.debug("Wait for price and sale block to be visible");
        mainPage.getPriceAndSaleBlock().nth(0).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        logger.debug("Get list with sales");
        List<String> listOfSales = mainPage.getSalesLocators().allInnerTexts();
        String maxSale = null;
        String salesPrice = null;
        String maxPrice = null;
        Page newPage;
        logger.debug("Check is list with sales greater than zero");
        if (listOfSales.size() > 0) {
            logger.debug("list with sales size is " + listOfSales.size());
            List<String> listOfSalesModified = listOfSales.stream()
                    .map(s -> s.replaceAll("[^0-9]", ""))
                    .collect(Collectors.toList());
            List<Integer> listOfSalesInt = listOfSalesModified.stream().map(x -> Integer.parseInt(x))
                    .collect(Collectors.toList());
            System.out.println(listOfSalesInt);
            logger.debug("Find max sale from list");
            Integer max = listOfSalesInt.stream().max(Integer::compareTo).get();
            maxSale = "-" + String.valueOf(max) + "%";
            logger.debug("Max sale is " + maxSale);
            salesPrice = mainPage.getPriceLocatorBySale(max).nth(0).textContent();
            logger.debug("Price on max sale is " + salesPrice);
            logger.info("Click on game with max sale");
            newPage = context.waitForPage(() -> {
                mainPage.getLinkLocatorBySale(max).nth(0).click();
            });
        } else {
            logger.debug("Games on sale not found");
            logger.debug("Get list with prices");
            List<String> listOfPrices = mainPage.getPricesLocators().allInnerTexts();
            List<String> listOfPricesModified = listOfPrices.stream()
                    .map(s -> s.replaceAll("[^0-9.]", "")).filter(x->!x.isEmpty())
                    .collect(Collectors.toList());
            List<Double> listOfPricesDouble = listOfPricesModified.stream().map(x -> Double.parseDouble(x))
                    .collect(Collectors.toList());
            logger.debug("Find max price from list");
            Double max = listOfPricesDouble.stream().max(Double::compareTo).get();
            maxPrice = mainPage.getPriceLocatorByPrice(max).textContent();
            logger.debug("Max price is " + maxPrice);
            logger.info("Click on game with max price");
            newPage = context.waitForPage(() -> {
                mainPage.getLinkLocatorByPrice(max).nth(0).click();
            });
            TestHelper.setPage(newPage);
        }

        //обработка возраста, если она есть
        AgeConfirmationPage ageConfirmationPage = new AgeConfirmationPage(newPage);
        logger.debug("Check is game need age confirmation");
        if (ageConfirmationPage.getNotificationText().isVisible()) {
            logger.debug("Confirmation is need");
            logger.info("Select day of birth");
            ageConfirmationPage.getDayOfBirthSelect().selectOption("16");
            logger.info("Select month of birth");
            ageConfirmationPage.getMonthOfBirthSelect().selectOption("May");
            logger.info("Select year of birth");
            ageConfirmationPage.getYearOfBirthSelect().selectOption("1999");
            logger.info("Click confirm button");
            ageConfirmationPage.getConfirmButton().click();
            newPage.waitForLoadState(LoadState.LOAD);
            TestHelper.setPage(newPage);
        }
        //проверка максимальной скидки или цены
        GamePage gamePage = new GamePage(newPage);
        logger.debug("Check is max sake is  exist");
        if (maxSale != null) {
            logger.debug("Max sale is exist");
            gamePage.getSaleLocatorByDiscount(maxSale).scrollIntoViewIfNeeded();
            logger.info("Assert that sale is correct");
            assertThat(gamePage.getSaleLocatorByDiscount(maxSale)).containsText(maxSale);
            logger.info("Assert that price on sale is correct");
            assertThat(gamePage.getPriceLocatorBySalesPrice(salesPrice)).containsText(salesPrice);

        } else {
            logger.debug("Max sale is not exist");
            logger.info("Assert that price is correct");
            assertThat(gamePage.getPriceLocatorByPrice(maxPrice)).containsText(maxPrice);
        }

        //клик на  кнопке install steam
        HeaderPage headerPage = new HeaderPage(newPage);
        logger.info("Click to install button");
        headerPage.getInstallButton().click();
        logger.debug("Wait for load state");
        newPage.waitForLoadState(LoadState.LOAD);
        DownloadPage downloadPage = new DownloadPage(newPage);

        //скачиваем файл и изменяем его имя
        logger.info("Wait for download game");
        Download download = newPage.waitForDownload(() -> {
            downloadPage.getInstallButton().nth(0).click();
        });
        logger.info("Assert that download is exist");
        Assertions.assertNotNull(download);
        logger.info("Assert that name of file is correct");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        logger.debug("Select the date format");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss");
        String formattedTime = formatter.format(timestamp.getTime());
        logger.debug("Indicate path for saving file and name");
        Path destinationPath = Paths.get(PropertiesHelper.getDownloadDir(), formattedTime + download.suggestedFilename());
        logger.info("Save file");
        download.saveAs(destinationPath);
    }
}