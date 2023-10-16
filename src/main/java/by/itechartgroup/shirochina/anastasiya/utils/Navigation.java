package by.itechartgroup.shirochina.anastasiya.utils;

import by.itechartgroup.shirochina.anastasiya.Categories;

public class Navigation {
    private static String searchCategoriesByTextLocator = "//div[@class='popup_menu_subheader popup_genre_expand_header responsive_hidden']/a[contains(text(), '";

    public static String getLocatorByCategoriesName(Categories categories) {
        return searchCategoriesByTextLocator + categories.getValue() + "')]";
    }
}
