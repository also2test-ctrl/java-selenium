package web_saucedemo.tests.testng.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import web_saucedemo.pages.HeaderPage;
import web_saucedemo.pages.LoginPage;
import web_saucedemo.pages.ProductsPage;
import web_saucedemo.pages.ShoppingCartPage;
import java.util.Arrays;
import java.util.List;

public class TC007_ProductManagement extends BaseTest {

    String dtUsername = "standard_user";
    String dtPassword = "secret_sauce";

    String product1 = "Sauce Labs Backpack";
    String product2 = "Sauce Labs Bike Light";
    String product3 = "Sauce Labs Bolt T-Shirt";
    String product4 = "Sauce Labs Fleece Jacket";

    @Test
    public void TC005_RemoveFromCart() {
        new LoginPage(page).login(dtUsername, dtPassword);
        
        ProductsPage productsPage = new ProductsPage(page);
        HeaderPage headerPage = new HeaderPage(page);
        
        productsPage.add(product1);
        productsPage.add(product2);
        Assert.assertEquals(headerPage.getCartCount(), 2, "Cart count should be 2 after adding products");
        
        productsPage.remove(product1);
        Assert.assertEquals(headerPage.getCartCount(), 1, "Cart count should be 1 after removing one product");
        
        ShoppingCartPage cartPage = new ShoppingCartPage(page).open();
        Assert.assertFalse(cartPage.isProductInCart(product1), "Removed product should not be in cart");
        Assert.assertTrue(cartPage.isProductInCart(product2), "Remaining product should still be in cart");
        
        page.navigate("https://www.saucedemo.com/inventory.html");
        productsPage.remove(product2);
        Assert.assertEquals(headerPage.getCartCount(), 0, "Cart count should be 0 after removing all products");
    }

    @Test
    public void TC008_RemoveFromCartPage() {
        new LoginPage(page).login(dtUsername, dtPassword);
        
        ProductsPage productsPage = new ProductsPage(page);
        HeaderPage headerPage = new HeaderPage(page);
        
        productsPage.add(product1);
        productsPage.add(product2);
        productsPage.add(product3);
        Assert.assertEquals(headerPage.getCartCount(), 3, "Cart count should be 3 after adding products");
        
        ShoppingCartPage cartPage = new ShoppingCartPage(page).open();
        Assert.assertTrue(cartPage.isProductInCart(product1), "Product 1 should be in cart");
        Assert.assertTrue(cartPage.isProductInCart(product2), "Product 2 should be in cart");
        Assert.assertTrue(cartPage.isProductInCart(product3), "Product 3 should be in cart");
        
        page.locator(".cart_item").filter(new com.microsoft.playwright.Locator.FilterOptions().setHasText(product2))
            .locator("button[data-test*='remove']").click();
        
        page.waitForTimeout(500);
        Assert.assertEquals(headerPage.getCartCount(), 2, "Cart count should be 2 after removing from cart page");
        Assert.assertFalse(cartPage.isProductInCart(product2), "Removed product should not be in cart");
        Assert.assertTrue(cartPage.isProductInCart(product1), "Product 1 should still be in cart");
        Assert.assertTrue(cartPage.isProductInCart(product3), "Product 3 should still be in cart");
    }

    @Test
    public void TC007_AddMultipleProducts() {
        new LoginPage(page).login(dtUsername, dtPassword);
        
        ProductsPage productsPage = new ProductsPage(page);
        HeaderPage headerPage = new HeaderPage(page);
        
        List<String> products = Arrays.asList(product1, product2, product3, product4);
        
        for (int i = 0; i < products.size(); i++) {
            productsPage.add(products.get(i));
            Assert.assertEquals(headerPage.getCartCount(), i + 1, 
                "Cart count should be " + (i + 1) + " after adding product " + (i + 1));
        }
        
        ShoppingCartPage cartPage = new ShoppingCartPage(page).open();
        for (String product : products) {
            Assert.assertTrue(cartPage.isProductInCart(product), 
                "Product " + product + " should be in cart");
        }
        
        int cartItemCount = page.locator(".cart_item").count();
        Assert.assertEquals(cartItemCount, products.size(), 
            "Should have exactly " + products.size() + " items in cart");
    }

    @Test
    public void TC008_AddSameProductMultipleTimes() {
        new LoginPage(page).login(dtUsername, dtPassword);
        
        ProductsPage productsPage = new ProductsPage(page);
        HeaderPage headerPage = new HeaderPage(page);
        
        productsPage.add(product1);
        Assert.assertEquals(headerPage.getCartCount(), 1, "Cart count should be 1 after first add");
        
        page.waitForTimeout(500);
        Assert.assertEquals(headerPage.getCartCount(), 1, "Cart count should remain 1 (no duplicates allowed)");
        
        ShoppingCartPage cartPage = new ShoppingCartPage(page).open();
        Assert.assertTrue(cartPage.isProductInCart(product1), "Product should be in cart");
        
        int cartItemCount = page.locator(".cart_item").count();
        Assert.assertEquals(cartItemCount, 1, "Should have exactly 1 item in cart");
        
        page.navigate("https://www.saucedemo.com/inventory.html");
        page.waitForTimeout(500);
        Assert.assertEquals(headerPage.getCartCount(), 1, "Cart count should persist after navigation");
    }
}
