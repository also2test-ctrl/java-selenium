package web_saucedemo.contexts;

public class CheckoutYourInfoData {

    private String firstName;
    private String lastName;
    private String zip;

    public CheckoutYourInfoData() {}

    public CheckoutYourInfoData(String firstName, String lastName, String zip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.zip = zip;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
