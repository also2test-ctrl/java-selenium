package web_saucedemo.tests.testng.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import web_saucedemo.pages.HeaderPage;
import web_saucedemo.pages.LoginPage;
import web_saucedemo.pages.ProductsPage;
import web_saucedemo.pages.ShoppingCartPage;
import java.util.Arrays;
import java.util.List;

public class TC006_ShoppingCartStability extends BaseTest {

    String dtUsername = "standard_user";
    String dtPassword = "secret_sauce";

    String product1 = "Sauce Labs Backpack";
    String product2 = "Sauce Labs Bike Light";
    String product3 = "Sauce Labs Bolt T-Shirt";
    String product4 = "Sauce Labs Fleece Jacket";
    String product5 = "Sauce Labs Onesie";
    String product6 = "Test.allTheThings() T-Shirt (Red)";

    @Test
    public void TC006_AddMultipleProducts() {
        new LoginPage(page).login(dtUsername, dtPassword);
        
        ProductsPage productsPage = new ProductsPage(page);
        HeaderPage headerPage = new HeaderPage(page);
        
        productsPage.add(product1);
        Assert.assertEquals(headerPage.getCartCount(), 1, "Cart count should be 1 after adding first product");
        
        productsPage.add(product2);
        Assert.assertEquals(headerPage.getCartCount(), 2, "Cart count should be 2 after adding second product");
        
        productsPage.add(product3);
        Assert.assertEquals(headerPage.getCartCount(), 3, "Cart count should be 3 after adding third product");
        
        ShoppingCartPage cartPage = new ShoppingCartPage(page).open();
        Assert.assertTrue(cartPage.isProductInCart(product1), "Product 1 should be in cart");
        Assert.assertTrue(cartPage.isProductInCart(product2), "Product 2 should be in cart");
        Assert.assertTrue(cartPage.isProductInCart(product3), "Product 3 should be in cart");
    }

    @Test
    public void TC006_AddRemoveProducts() {
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
    public void TC006_AddAllProducts() {
        new LoginPage(page).login(dtUsername, dtPassword);
        
        ProductsPage productsPage = new ProductsPage(page);
        HeaderPage headerPage = new HeaderPage(page);
        
        List<String> allProducts = Arrays.asList(product1, product2, product3, product4, product5, product6);
        
        for (int i = 0; i < allProducts.size(); i++) {
            productsPage.add(allProducts.get(i));
            Assert.assertEquals(headerPage.getCartCount(), i + 1, 
                "Cart count should be " + (i + 1) + " after adding product " + (i + 1));
        }
        
        ShoppingCartPage cartPage = new ShoppingCartPage(page).open();
        for (String product : allProducts) {
            Assert.assertTrue(cartPage.isProductInCart(product), 
                "Product " + product + " should be in cart");
        }
    }

    @Test
    public void TC006_CartPersistenceAcrossPages() {
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
    }

    @Test
    public void TC006_EmptyCartBehavior() {
        new LoginPage(page).login(dtUsername, dtPassword);
        
        HeaderPage headerPage = new HeaderPage(page);
        ShoppingCartPage cartPage = new ShoppingCartPage(page);
        
        Assert.assertEquals(headerPage.getCartCount(), 0, "Cart should be empty initially");
        
        cartPage.open();
        
        Assert.assertTrue(page.url().contains("cart.html"), "Should be on cart page");
        
        page.navigate("https://www.saucedemo.com/inventory.html");
        Assert.assertEquals(headerPage.getCartCount(), 0, "Cart should remain empty");
    }

    @Test
    public void TC006_DuplicateProductAddition() {
        new LoginPage(page).login(dtUsername, dtPassword);
        
        ProductsPage productsPage = new ProductsPage(page);
        HeaderPage headerPage = new HeaderPage(page);
        
        productsPage.add(product1);
        Assert.assertEquals(headerPage.getCartCount(), 1, "Cart count should be 1 after first add");
        
        page.waitForTimeout(500); // Wait for UI update
        Assert.assertEquals(headerPage.getCartCount(), 1, "Cart count should remain 1 (no duplicates allowed)");
        
        ShoppingCartPage cartPage = new ShoppingCartPage(page).open();
        Assert.assertTrue(cartPage.isProductInCart(product1), "Product should be in cart");
        
        int cartItemCount = page.locator(".cart_item").count();
        Assert.assertEquals(cartItemCount, 1, "Should have exactly 1 item in cart");
    }

    @Test
    public void TC006_CartCounterAccuracy() {
        new LoginPage(page).login(dtUsername, dtPassword);
        
        ProductsPage productsPage = new ProductsPage(page);
        HeaderPage headerPage = new HeaderPage(page);
        
        Assert.assertEquals(headerPage.getCartCount(), 0, "Initial cart count should be 0");
        
        productsPage.add(product1);
        Assert.assertEquals(headerPage.getCartCount(), 1);
        
        productsPage.add(product2);
        Assert.assertEquals(headerPage.getCartCount(), 2);
        
        productsPage.add(product3);
        Assert.assertEquals(headerPage.getCartCount(), 3);
        
        productsPage.remove(product2);
        Assert.assertEquals(headerPage.getCartCount(), 2);
        
        productsPage.remove(product1);
        Assert.assertEquals(headerPage.getCartCount(), 1);
        
        productsPage.remove(product3);
        Assert.assertEquals(headerPage.getCartCount(), 0);
    }
}
