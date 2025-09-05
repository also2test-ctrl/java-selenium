package web_saucedemo.tests.testng.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import web_saucedemo.enums.AppMenu;
import web_saucedemo.pages.HeaderPage;
import web_saucedemo.pages.LoginPage;
import web_saucedemo.pages.ProductsPage;

public class TC002_AddToCart_Count extends BaseTest {

    // TODO: Data provider
    String dtUsername = "standard_user";
    String dtPassword = "secret_sauce";

    String prod1 = "Sauce Labs Onesie";

    @Test
    private void test() {
        new LoginPage(page).login(dtUsername, dtPassword);
        new HeaderPage(page).navigateToMenu(AppMenu.LOGOUT);
    }

    @Test
    public void TC002_AddToCart_Count() {
        new LoginPage(page).login(dtUsername, dtPassword);

        ProductsPage pgProducts = new ProductsPage(page);
        pgProducts.add(prod1);

        HeaderPage pgHeader = new HeaderPage(page);
        Assert.assertEquals(1, pgHeader.getCartCount());
    }
}
