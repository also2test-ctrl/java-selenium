package web_saucedemo.tests.testng.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import web_saucedemo.pages.HeaderPage;
import web_saucedemo.pages.LoginPage;
import web_saucedemo.pages.ProductsPage;
import web_saucedemo.pages.ShoppingCartPage;
import java.util.Arrays;
import java.util.List;

public class TC014_CartCounterValidation extends BaseTest {

    String dtUsername = "standard_user";
    String dtPassword = "secret_sauce";

    String product1 = "Sauce Labs Backpack";
    String product2 = "Sauce Labs Bike Light";
    String product3 = "Sauce Labs Bolt T-Shirt";
    String product4 = "Sauce Labs Fleece Jacket";
    String product5 = "Sauce Labs Onesie";

    @Test
    public void TC014_CartCounterAccuracy() {
        new LoginPage(page).login(dtUsername, dtPassword);
        
        ProductsPage productsPage = new ProductsPage(page);
        HeaderPage headerPage = new HeaderPage(page);
        
        Assert.assertEquals(headerPage.getCartCount(), 0, "Initial cart count should be 0");
        
        List<String> products = Arrays.asList(product1, product2, product3, product4, product5);
        
        for (int i = 0; i < products.size(); i++) {
            productsPage.add(products.get(i));
            Assert.assertEquals(headerPage.getCartCount(), i + 1, 
                "Cart count should be " + (i + 1) + " after adding product " + (i + 1));
            
            page.waitForTimeout(200);
            Assert.assertEquals(headerPage.getCartCount(), i + 1, 
                "Cart count should remain " + (i + 1) + " after brief wait");
        }
        
        ShoppingCartPage cartPage = new ShoppingCartPage(page).open();
        int cartItemCount = page.locator(".cart_item").count();
        Assert.assertEquals(cartItemCount, products.size(), 
            "Cart item count should match header counter");
        
        page.navigate("https://www.saucedemo.com/inventory.html");
        Assert.assertEquals(headerPage.getCartCount(), products.size(), 
            "Cart counter should persist after navigation");
    }

    @Test
    public void TC013_CartCounterAfterRemoval() {
        new LoginPage(page).login(dtUsername, dtPassword);
        
        ProductsPage productsPage = new ProductsPage(page);
        HeaderPage headerPage = new HeaderPage(page);
        
        List<String> products = Arrays.asList(product1, product2, product3, product4);
        
        for (String product : products) {
            productsPage.add(product);
        }
        Assert.assertEquals(headerPage.getCartCount(), products.size(), 
            "Cart count should be " + products.size() + " after adding all products");
        
        productsPage.remove(product2);
        Assert.assertEquals(headerPage.getCartCount(), products.size() - 1, 
            "Cart count should decrease by 1 after removing product");
        
        productsPage.remove(product4);
        Assert.assertEquals(headerPage.getCartCount(), products.size() - 2, 
            "Cart count should decrease by 2 after removing second product");
        
        ShoppingCartPage cartPage = new ShoppingCartPage(page).open();
        page.locator(".cart_item").filter(new com.microsoft.playwright.Locator.FilterOptions().setHasText(product1))
            .locator("button[data-test*='remove']").click();
        
        page.waitForTimeout(500);
        Assert.assertEquals(headerPage.getCartCount(), products.size() - 3, 
            "Cart count should decrease after removing from cart page");
        
        page.navigate("https://www.saucedemo.com/inventory.html");
        productsPage.remove(product3);
        Assert.assertEquals(headerPage.getCartCount(), 0, 
            "Cart count should be 0 after removing all products");
    }
}
