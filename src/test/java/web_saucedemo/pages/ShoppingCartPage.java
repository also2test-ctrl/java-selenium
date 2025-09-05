package web_saucedemo.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ShoppingCartPage extends BasePage {

    private final String lstProduct = ".cart_item";
    private final String lstProduct_title = "[data-test*='title-link']";
    private final String btnCart = "#shopping_cart_container";

    public ShoppingCartPage(Page page) {
        super(page);
    }

    private Locator getProduct(String title) {
        return page.locator(lstProduct)
                .filter(new Locator.FilterOptions().setHasText(title));
    }

    public boolean isProductInCart(String title) {
        return getProduct(title).isVisible();
    }

    public ShoppingCartPage open() {
        page.locator(btnCart).click();
        return this;
    }

    public CheckoutPage checkout() {
        page.locator("#checkout").click();
        return new CheckoutPage(page);
    }
}
