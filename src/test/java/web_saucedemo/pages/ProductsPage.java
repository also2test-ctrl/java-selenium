package web_saucedemo.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.List;
import java.util.ArrayList;

public class ProductsPage extends BasePage {

    private final String drpSort = ".product_sort_container";
    private final String lstProduct = ".inventory_item";
    private final String lstProduct_title = "[data-test*='title-link']";
    private final String lstProduct_price = ".inventory_item_price";
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
        page.locator(drpSort).waitFor();
        page.locator(drpSort).selectOption(orderBy);
        page.waitForTimeout(1000);
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

    public List<String> getProductNames() {
        List<String> productNames = new ArrayList<>();
        Locator productTitles = page.locator(lstProduct_title);
        int count = productTitles.count();
        for (int i = 0; i < count; i++) {
            productNames.add(productTitles.nth(i).textContent());
        }
        return productNames;
    }

    public List<Double> getProductPrices() {
        List<Double> productPrices = new ArrayList<>();
        Locator priceElements = page.locator(lstProduct_price);
        int count = priceElements.count();
        for (int i = 0; i < count; i++) {
            String priceText = priceElements.nth(i).textContent().replace("$", "");
            productPrices.add(Double.parseDouble(priceText));
        }
        return productPrices;
    }
}
