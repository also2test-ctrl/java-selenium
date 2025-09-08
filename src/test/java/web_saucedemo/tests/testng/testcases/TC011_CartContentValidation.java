package web_saucedemo.tests.testng.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import web_saucedemo.pages.HeaderPage;
import web_saucedemo.pages.LoginPage;
import web_saucedemo.pages.ProductsPage;
import web_saucedemo.pages.ShoppingCartPage;
import java.util.List;

public class TC011_CartContentValidation extends BaseTest {

    String dtUsername = "standard_user";
    String dtPassword = "secret_sauce";

    String product1 = "Sauce Labs Backpack";
    String product2 = "Sauce Labs Bike Light";
    String product3 = "Sauce Labs Bolt T-Shirt";

    @Test
    public void TC011_CartContentsValidation() {
        new LoginPage(page).login(dtUsername, dtPassword);
        
        ProductsPage productsPage = new ProductsPage(page);
        HeaderPage headerPage = new HeaderPage(page);
        
        productsPage.add(product1);
        productsPage.add(product2);
        Assert.assertEquals(headerPage.getCartCount(), 2, "Cart count should be 2");
        
        ShoppingCartPage cartPage = new ShoppingCartPage(page).open();
        
        Assert.assertTrue(cartPage.isProductInCart(product1), "Product 1 should be in cart");
        Assert.assertTrue(cartPage.isProductInCart(product2), "Product 2 should be in cart");
        
        List<String> cartItemTitles = page.locator(".cart_item .inventory_item_name").allTextContents();
        Assert.assertTrue(cartItemTitles.contains(product1), "Cart should contain product 1 title");
        Assert.assertTrue(cartItemTitles.contains(product2), "Cart should contain product 2 title");
        
        List<String> cartItemPrices = page.locator(".cart_item .inventory_item_price").allTextContents();
        Assert.assertTrue(cartItemPrices.size() > 0, "Cart should display product prices");
        for (String price : cartItemPrices) {
            Assert.assertTrue(price.startsWith("$"), "Price should start with $ symbol");
            Assert.assertTrue(price.length() > 1, "Price should have value after $ symbol");
        }
        
        int cartItemCount = page.locator(".cart_item").count();
        Assert.assertEquals(cartItemCount, 2, "Should have exactly 2 items in cart");
    }

    @Test
    public void TC012_EmptyCartValidation() {
        new LoginPage(page).login(dtUsername, dtPassword);
        
        HeaderPage headerPage = new HeaderPage(page);
        ShoppingCartPage cartPage = new ShoppingCartPage(page);
        
        Assert.assertEquals(headerPage.getCartCount(), 0, "Cart should be empty initially");
        
        cartPage.open();
        Assert.assertTrue(page.url().contains("cart.html"), "Should be on cart page");
        
        int cartItemCount = page.locator(".cart_item").count();
        Assert.assertEquals(cartItemCount, 0, "Cart should have no items");
        
        Assert.assertTrue(page.locator("#checkout").isVisible(), "Checkout button should be visible");
        Assert.assertTrue(page.locator("#continue-shopping").isVisible(), "Continue shopping button should be visible");
        
        page.navigate("https://www.saucedemo.com/inventory.html");
        Assert.assertEquals(headerPage.getCartCount(), 0, "Cart should remain empty after navigation");
    }

    @Test
    public void TC013_CartPersistence() {
        new LoginPage(page).login(dtUsername, dtPassword);
        
        ProductsPage productsPage = new ProductsPage(page);
        HeaderPage headerPage = new HeaderPage(page);
        
        productsPage.add(product1);
        productsPage.add(product2);
        Assert.assertEquals(headerPage.getCartCount(), 2, "Cart count should be 2");
        
        ShoppingCartPage cartPage = new ShoppingCartPage(page).open();
        Assert.assertTrue(cartPage.isProductInCart(product1), "Product 1 should persist in cart");
        Assert.assertTrue(cartPage.isProductInCart(product2), "Product 2 should persist in cart");
        
        page.navigate("https://www.saucedemo.com/inventory.html");
        Assert.assertEquals(headerPage.getCartCount(), 2, "Cart count should persist after navigation");
        
        productsPage.add(product3);
        Assert.assertEquals(headerPage.getCartCount(), 3, "Cart count should be 3 after adding another product");
        
        cartPage.open();
        Assert.assertTrue(cartPage.isProductInCart(product1), "Product 1 should still be in cart");
        Assert.assertTrue(cartPage.isProductInCart(product2), "Product 2 should still be in cart");
        Assert.assertTrue(cartPage.isProductInCart(product3), "Product 3 should be in cart");
        
        page.navigate("https://www.saucedemo.com/inventory-item.html?id=4");
        page.navigate("https://www.saucedemo.com/inventory.html");
        Assert.assertEquals(headerPage.getCartCount(), 3, "Cart should persist across multiple page navigations");
    }
}
