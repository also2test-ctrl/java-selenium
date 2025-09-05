package web_saucedemo.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProductsPage extends BasePage {

    private final String drpSort = "[data-test='product_sort_container']";
    private final String lstProduct = ".inventory_item";
    private final String lstProduct_title = "[data-test*='title-link']";
    private final String lstProduct_add = "button[data-test*='add-to-cart']";
    private final String lstProduct_remove = "button[data-test*='remove']";

    public ProductsPage(Page page) {
        super(page);
    }

    private Locator getProduct(String title) {
        return page.locator(lstProduct)
                .filter(new Locator.FilterOptions().setHasText(title));
    }

    public ProductsPage sort(String orderBy) {
        page.locator(drpSort).selectOption(orderBy);
        return this;
    }

    public ProductsPage add(String title) {
        getProduct(title)
                .locator(lstProduct_add)
                .click();
        return this;
    }

    public ProductsPage remove(String title) {
        getProduct(title)
                .locator(lstProduct_remove)
                .click();
        return this;
    }
}
