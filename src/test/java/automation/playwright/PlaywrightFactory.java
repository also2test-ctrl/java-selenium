package automation.playwright;

import automation.enums.Browsers;
import com.microsoft.playwright.*;

public class PlaywrightFactory {
    
    public static Browser launch(Browsers browser) {
        Playwright playwright = Playwright.create();
        
        if (browser.equals(Browsers.CHROME)) {
            return playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        } else if (browser.equals(Browsers.FIREFOX)) {
            return playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(true));
        } else if (browser.equals(Browsers.EDGE)) {
            return playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(true));
        }
        
        // default
        return playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }
}
