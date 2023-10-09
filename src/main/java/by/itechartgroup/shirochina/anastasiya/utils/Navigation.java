package by.itechartgroup.shirochina.anastasiya.utils;

import by.itechartgroup.shirochina.anastasiya.Categories;

public class Navigation {
    private static String searchCategoriesByTextLocator = "//div[@class='popup_menu_subheader popup_genre_expand_header responsive_hidden']/a[contains(text(), '";

    public static String getLocatorByCategoriesName(Categories categories) {
        switch (categories) {
            case ACTION:
                return searchCategoriesByTextLocator + Categories.ACTION.getValue() + "')]";
            case ROLE_PLAYING:
                return searchCategoriesByTextLocator + Categories.ROLE_PLAYING.getValue() + "')]";
            case STRATEGY:
                return searchCategoriesByTextLocator + Categories.STRATEGY.getValue() + "')]";
            case ADVENTURE:
                return searchCategoriesByTextLocator + Categories.ADVENTURE.getValue() + "')]";
            case SIMULATION:
                return searchCategoriesByTextLocator + Categories.SIMULATION.getValue() + "')]";
            case SPORTS_AND_RACING:
                return searchCategoriesByTextLocator + Categories.SPORTS_AND_RACING.getValue() + "')]";
            default:
                throw new IllegalArgumentException("Неверная категория: " + categories);
        }
    }
}
