package automation.selenium;

import automation.enums.Browsers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserFactory {

    public static WebDriver launch(Browsers browser) {
        if (browser.equals(Browsers.CHROME)) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--user-data-dir=/tmp/chrome-test-" + System.currentTimeMillis());
            return new ChromeDriver(options);
        } else if (browser.equals(Browsers.FIREFOX)) {
            return new FirefoxDriver();
        } else if (browser.equals(Browsers.EDGE)) {
            return new EdgeDriver();
        }

        // default
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--user-data-dir=/tmp/chrome-test-" + System.currentTimeMillis());
        return new ChromeDriver(options);
    }
}
