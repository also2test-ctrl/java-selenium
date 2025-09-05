package web_saucedemo.tests.testng.testcases;

import automation.enums.Browsers;
import automation.playwright.PlaywrightFactory;
import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import web_saucedemo.config.EnvironmentVariables;

public abstract class BaseTest {

    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    private Browsers browserType = Browsers.CHROME;
    private String url = "https://www.saucedemo.com/";

    @BeforeMethod
    public void setup() {
        browser = PlaywrightFactory.launch(browserType);
        context = browser.newContext();
        page = context.newPage();
        page.setDefaultTimeout(EnvironmentVariables.WAIT_IMPLICIT * 1000);
        page.navigate(url);
    }

    @AfterMethod
    public void tearDown() {
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
    }
}
