package web_saucedemo.pages;

import com.microsoft.playwright.Page;
import web_saucedemo.enums.AppMenu;

public class HeaderPage extends BasePage {

    private final String btnMenu = "#react-burger-menu-btn";
    private final String navMenu = ".bm-item";
    private final String lblCart = "#shopping_cart_container";

    public HeaderPage(Page page) {
        super(page);
    }

    public HeaderPage navigateToMenu(AppMenu menu) {
        page.locator(btnMenu).click();
        page.locator(navMenu)
                .filter(new com.microsoft.playwright.Locator.FilterOptions().setHasText(menu.value()))
                .click();
        return this;
    }

    public int getCartCount() {
        String count = page.locator(lblCart + " .shopping_cart_badge").textContent();
        return count == null || count.isEmpty() ? 0 : Integer.parseInt(count);
    }
}
