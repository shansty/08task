package by.itechartgroup.shirochina.anastasiya.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AgeConfirmationPage extends BasePage {
    private final Locator notificationText;
    private final Locator dayOfBirthSelect;
    private final Locator monthOfBirthSelect;
    private final Locator yearOfBirthSelect;
    private final Locator confirmButton;

    public AgeConfirmationPage(Page page) {
        super(page);
        this.notificationText = page.locator("//div[text() = 'Please enter your birth date to continue:']");
        this.dayOfBirthSelect = page.locator("//select[@name = 'ageDay']");
        this.monthOfBirthSelect = page.locator("//select[@name = 'ageMonth']");
        this.yearOfBirthSelect = page.locator("//select[@name = 'ageYear']");
        this.confirmButton = page.locator("//a[@id = 'view_product_page_btn']");
    }

    public Locator getNotificationText() {
        return notificationText;
    }

    public Locator getDayOfBirthSelect() {
        return dayOfBirthSelect;
    }

    public Locator getMonthOfBirthSelect() {
        return monthOfBirthSelect;
    }

    public Locator getYearOfBirthSelect() {
        return yearOfBirthSelect;
    }

    public Locator getConfirmButton() {
        return confirmButton;
    }
}
