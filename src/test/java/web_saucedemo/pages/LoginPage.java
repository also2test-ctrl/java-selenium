package web_saucedemo.pages;

import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {

    private static final String ERR_LOCKED_OUT = "Epic sadface: Sorry, this user has been locked out.";

    private final String txtUsername = "#user-name";
    private final String txtPassword = "#password";
    private final String btnLogin = "#login-button";
    private final String lblErrMsg = "[data-test='error']";

    public LoginPage(Page page) {
        super(page);
    }

    public boolean isAt() {
        return page.locator(txtUsername).isVisible();
    }

    public boolean isUserLockedOut() {
        String error = page.locator(lblErrMsg).textContent();
        return ERR_LOCKED_OUT.equals(error);
    }

    public void login(String username, String password) {
        page.locator(txtUsername).fill(username);
        page.locator(txtPassword).fill(password);
        page.locator(btnLogin).click();
    }
}
