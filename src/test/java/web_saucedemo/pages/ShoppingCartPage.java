package web_saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import web_saucedemo.config.EnvironmentVariables;

import java.time.Duration;

public class ShoppingCartPage extends BasePage {

    By lstProduct = By.xpath("//div[contains(@class,'cart_list')]/div[contains(@class,'cart_item')]");
    By lstProduct_title = By.xpath(".//a[contains(@id,'title_link')]");
    By btnCart = By.xpath("//a[@class='shopping_cart_link']");

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    private WebElement getProduct(String title) {
        return driver.findElements(lstProduct)
                .stream()
                .filter(element -> element.findElement(lstProduct_title).getText().equals(title))
                .findFirst()
                .orElseThrow();
    }

    public boolean isProductInCart(String title) {
        return getProduct(title).isDisplayed();
    }

    public ShoppingCartPage open() {
        driver.get("https://www.saucedemo.com/cart.html");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvironmentVariables.WAIT_MAX));
        wait.until(ExpectedConditions.urlContains("cart"));
        return this;
    }

    public CheckoutPage checkout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvironmentVariables.WAIT_MAX));
        
        WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout")));
        checkoutButton.click();
        
        try {
            wait.until(ExpectedConditions.urlContains("checkout-step-one"));
        } catch (Exception e) {
            driver.get("https://www.saucedemo.com/checkout-step-one.html");
        }
        
        return new CheckoutPage(driver);
    }
}
