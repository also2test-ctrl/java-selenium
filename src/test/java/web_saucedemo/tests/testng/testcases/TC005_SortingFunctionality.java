package web_saucedemo.tests.testng.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import web_saucedemo.pages.LoginPage;
import web_saucedemo.pages.ProductsPage;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class TC005_SortingFunctionality extends BaseTest {

    String dtUsername = "standard_user";
    String dtPassword = "secret_sauce";

    @Test
    public void TC005_SortByNameAtoZ() {
        new LoginPage(page).login(dtUsername, dtPassword);
        ProductsPage productsPage = new ProductsPage(page);
        
        page.waitForSelector(".inventory_item");
        productsPage.sort("az");
        List<String> actualNames = productsPage.getProductNames();
        
        List<String> expectedNames = new ArrayList<>(actualNames);
        Collections.sort(expectedNames);
        
        Assert.assertEquals(actualNames, expectedNames, "Products should be sorted by name A to Z");
    }

    @Test
    public void TC005_SortByNameZtoA() {
        new LoginPage(page).login(dtUsername, dtPassword);
        ProductsPage productsPage = new ProductsPage(page);
        
        page.waitForSelector(".inventory_item");
        productsPage.sort("za");
        List<String> actualNames = productsPage.getProductNames();
        
        List<String> expectedNames = new ArrayList<>(actualNames);
        Collections.sort(expectedNames, Collections.reverseOrder());
        
        Assert.assertEquals(actualNames, expectedNames, "Products should be sorted by name Z to A");
    }

    @Test
    public void TC005_SortByPriceLowToHigh() {
        new LoginPage(page).login(dtUsername, dtPassword);
        ProductsPage productsPage = new ProductsPage(page);
        
        page.waitForSelector(".inventory_item");
        productsPage.sort("lohi");
        List<Double> actualPrices = productsPage.getProductPrices();
        
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);
        
        Assert.assertEquals(actualPrices, expectedPrices, "Products should be sorted by price low to high");
    }

    @Test
    public void TC005_SortByPriceHighToLow() {
        new LoginPage(page).login(dtUsername, dtPassword);
        ProductsPage productsPage = new ProductsPage(page);
        
        page.waitForSelector(".inventory_item");
        productsPage.sort("hilo");
        List<Double> actualPrices = productsPage.getProductPrices();
        
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices, Collections.reverseOrder());
        
        Assert.assertEquals(actualPrices, expectedPrices, "Products should be sorted by price high to low");
    }
}
