package by.itechartgroup.shirochina.anastasiya.utils;

import by.itechartgroup.shirochina.anastasiya.Categories;

public class Navigation {

    public static String getLocatorByCategoriesName(Categories categories) {
        return "//div[@class='popup_menu_subheader popup_genre_expand_header responsive_hidden']/a[contains(text(), '" + categories.getValue() + "')]";
    }
}
