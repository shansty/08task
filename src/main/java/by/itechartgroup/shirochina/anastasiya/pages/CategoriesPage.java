package by.itechartgroup.shirochina.anastasiya.pages;

import com.microsoft.playwright.Page;

public class CategoriesPage extends BasePage{
    private String categoriesButton = "//div[@id='genre_tab']";

    public CategoriesPage(Page page) {
        super(page);
    }
    public String getCategoriesButton() {
        return categoriesButton;
    }
}
