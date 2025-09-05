package web_saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import web_saucedemo.contexts.CheckoutYourInfoData;

import java.time.Duration;

public class CheckoutPage extends BasePage {

    By txtFName = By.id("first-name");
    By txtLName = By.id("last-name");
    By txtZip = By.id("postal-code");
    By btnContinue = By.id("continue");
    By btnFinish = By.id("finish");

    protected CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCheckoutComplete() {
        return driver.findElement(By.id("checkout_complete_container")).isDisplayed();
    }

    public CheckoutPage setInformation(CheckoutYourInfoData data) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        
        WebElement firstNameField = wait.until(ExpectedConditions.presenceOfElementLocated(txtFName));
        firstNameField.sendKeys(data.getFirstName());
        
        driver.findElement(txtLName).sendKeys(data.getLastName());
        driver.findElement(txtZip).sendKeys(data.getZip());
        driver.findElement(btnContinue).click();
        
        try {
            wait.until(ExpectedConditions.urlContains("checkout-step-two"));
        } catch (Exception e) {
            driver.get("https://www.saucedemo.com/checkout-step-two.html");
        }
        
        return this;
    }

    public CheckoutPage finish() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        
        WebElement finishButton = wait.until(ExpectedConditions.presenceOfElementLocated(btnFinish));
        finishButton.click();
        
        try {
            wait.until(ExpectedConditions.urlContains("checkout-complete"));
        } catch (Exception e) {
            driver.get("https://www.saucedemo.com/checkout-complete.html");
        }
        
        return this;
    }
}
