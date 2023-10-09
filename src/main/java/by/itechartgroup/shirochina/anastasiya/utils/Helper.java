package by.itechartgroup.shirochina.anastasiya.utils;

import com.microsoft.playwright.Locator;

public class Helper {
    public static Locator getLocatorWithMaxSale(Locator previous, Locator current) {
        if (Integer.valueOf(previous.textContent().replaceAll("[^0-9]+", ""))
                >= Integer.valueOf(current.textContent().replaceAll("[^0-9]+", ""))) {
            return previous;
        } else {
            return current;
        }
    }
    public static Locator getLocatorWithMaxPrice(Locator previous, Locator current) {
        String prev = previous.textContent().replaceAll("[^0-9.]+", "");
        String curr = current.textContent().replaceAll("[^0-9.]+", "");
        if(prev.trim().isEmpty() && curr.trim().isEmpty()) {
            return current;
        } else if (prev.trim().isEmpty() && !curr.trim().isEmpty()) {
            return current;
        } else if (!prev.trim().isEmpty() && curr.trim().isEmpty()) {
            return previous;
        } else if (Double.valueOf(prev) >= Double.valueOf(curr)) {
            return previous;
        } else if (Double.valueOf(prev) < Double.valueOf(curr)) {
            return current;
        }
        return null;
    }
}
