package web_saucedemo.tests.testng.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import web_saucedemo.pages.HeaderPage;
import web_saucedemo.pages.LoginPage;
import web_saucedemo.pages.ProductsPage;
import web_saucedemo.pages.ShoppingCartPage;
import java.util.Arrays;
import java.util.List;

public class TC018_CartSizeLimit extends BaseTest {

    String dtUsername = "standard_user";
    String dtPassword = "secret_sauce";

    String product1 = "Sauce Labs Backpack";
    String product2 = "Sauce Labs Bike Light";
    String product3 = "Sauce Labs Bolt T-Shirt";
    String product4 = "Sauce Labs Fleece Jacket";
    String product5 = "Sauce Labs Onesie";
    String product6 = "Test.allTheThings() T-Shirt (Red)";

    @Test
    public void TC018_CartSizeLimit() {
        new LoginPage(page).login(dtUsername, dtPassword);
        
        ProductsPage productsPage = new ProductsPage(page);
        HeaderPage headerPage = new HeaderPage(page);
        
        List<String> allProducts = Arrays.asList(product1, product2, product3, product4, product5, product6);
        
        for (int i = 0; i < allProducts.size(); i++) {
            productsPage.add(allProducts.get(i));
            Assert.assertEquals(headerPage.getCartCount(), i + 1, 
                "Cart count should increment for each product added");
        }
        
        Assert.assertEquals(headerPage.getCartCount(), allProducts.size(), 
            "Cart should contain all available products");
        
        ShoppingCartPage cartPage = new ShoppingCartPage(page).open();
        int cartItemCount = page.locator(".cart_item").count();
        Assert.assertEquals(cartItemCount, allProducts.size(), 
            "Cart should display all products without limit issues");
        
        for (String product : allProducts) {
            Assert.assertTrue(cartPage.isProductInCart(product), 
                "Product " + product + " should be in cart");
        }
        
        page.navigate("https://www.saucedemo.com/inventory.html");
        Assert.assertEquals(headerPage.getCartCount(), allProducts.size(), 
            "Cart count should persist with maximum items");
        
        for (String product : allProducts) {
            productsPage.remove(product);
        }
        
        Assert.assertEquals(headerPage.getCartCount(), 0, 
            "Cart should be empty after removing all products");
    }
}
