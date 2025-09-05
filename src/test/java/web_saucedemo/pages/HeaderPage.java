package web_saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import web_saucedemo.config.EnvironmentVariables;
import web_saucedemo.enums.AppMenu;

import java.time.Duration;

public class HeaderPage extends BasePage {

    By btnMenu = By.id("react-burger-menu-btn");
    By navMenu = By.xpath("//nav[contains(@class, 'bm-item-list')]/a[contains(@class,'bm-item')]");
    By lblCart = By.id("shopping_cart_container");

    public HeaderPage(WebDriver driver) {
        super(driver);
    }

    public HeaderPage navigateToMenu(AppMenu menu) {
        driver.findElement(btnMenu).click();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvironmentVariables.WAIT_MAX));
        wait.until(ExpectedConditions.presenceOfElementLocated(navMenu));
        
        By menuSelector;
        switch (menu.value()) {
            case "Logout":
                menuSelector = By.id("logout_sidebar_link");
                break;
            case "All Items":
                menuSelector = By.id("inventory_sidebar_link");
                break;
            case "About":
                menuSelector = By.id("about_sidebar_link");
                break;
            case "Reset App State":
                menuSelector = By.id("reset_sidebar_link");
                break;
            default:
                throw new IllegalArgumentException("Unknown menu item: " + menu.value());
        }
        
        WebElement menuItem = wait.until(ExpectedConditions.presenceOfElementLocated(menuSelector));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", menuItem);
        return this;
    }

    public int getCartCount() {
        String count = driver.findElement(lblCart).getText();
        return count.isEmpty() ? 0: Integer.parseInt(count);
    }
}
