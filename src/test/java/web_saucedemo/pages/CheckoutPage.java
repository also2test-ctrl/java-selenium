package web_saucedemo.pages;

import com.microsoft.playwright.Page;
import web_saucedemo.contexts.CheckoutYourInfoData;

public class CheckoutPage extends BasePage {

    private final String txtFName = "#first-name";
    private final String txtLName = "#last-name";
    private final String txtZip = "#postal-code";
    private final String btnContinue = "#continue";
    private final String btnFinish = "#finish";

    protected CheckoutPage(Page page) {
        super(page);
    }

    public boolean isCheckoutComplete() {
        return page.locator("#checkout_complete_container").isVisible();
    }

    public CheckoutPage setInformation(CheckoutYourInfoData data) {
        page.locator(txtFName).fill(data.getFirstName());
        page.locator(txtLName).fill(data.getLastName());
        page.locator(txtZip).fill(data.getZip());
        page.locator(btnContinue).click();
        return this;
    }

    public CheckoutPage fillYourInfo(CheckoutYourInfoData data) {
        page.locator(txtFName).fill(data.getFirstName());
        page.locator(txtLName).fill(data.getLastName());
        page.locator(txtZip).fill(data.getZip());
        return this;
    }

    public CheckoutPage clickContinue() {
        page.locator(btnContinue).click();
        return this;
    }

    public CheckoutPage finish() {
        page.locator(btnFinish).click();
        return this;
    }
}
