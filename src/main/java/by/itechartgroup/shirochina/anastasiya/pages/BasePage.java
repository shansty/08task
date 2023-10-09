package by.itechartgroup.shirochina.anastasiya.pages;

import com.microsoft.playwright.Page;

public class BasePage {
    private Page page;
    public BasePage(Page page) {
        this.page = page;
    }
}
