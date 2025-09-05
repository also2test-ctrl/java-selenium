# java-playwright
This project demonstrates the use of Microsoft Playwright with Java to automate web application testing using the Page Object Model pattern and TestNG framework.

## 🚀 Getting Started

### 🚧 Prerequisites
Before you can run this project, you must have the following software installed:

- Java Development Kit (JDK) version 11 or later
- Apache Maven 3.6 or later
- Internet connection (for Playwright browser downloads)

### 🔗 Dependencies
This project uses the following main dependencies:

- Microsoft Playwright for Java version 1.40.0
- TestNG version 7.7.0

### 🛠️ Installation
1. Clone this repository to your local machine:
   ```sh
   git clone https://github.com/also2test-ctrl/java-selenium.git
   cd java-selenium
   ```

2. Install Maven dependencies and download Playwright browsers:
   ```sh
   mvn clean install
   mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
   ```

### 🌐 Application Under Test
* https://www.saucedemo.com/
  Note: This website is used for testing purposes only. Testing activities are for demonstration purposes.

### 👨🏼‍🔬 Tests
This project contains 4 sample test cases demonstrating Playwright automation capabilities:

#### 🧪 Test Cases
- `TC001_AddToCart_Checkout`: Adds products to cart and verifies successful checkout
- `TC002_AddToCart_Count`: Adds product to cart and verifies cart counter accuracy
- `TC003_LockedOutUser`: Tests invalid login and verifies error message display
- `TC004_Logout`: Tests successful login and logout functionality

#### 📝 Test Plans
- `TS-001_SmokeTest.xml`: Basic functionality smoke test suite
- `TS-002_Regression.xml`: Comprehensive regression test suite with parallel execution

### 🏃🏽 How to Run Tests

#### 🚦 Running Individual Test Cases
1. Navigate to the project directory:
   ```sh
   cd java-selenium
   ```
2. Run a specific test case:
   ```sh
   mvn test -Dtest=<test_case_name>
   ```
   Example: `mvn test -Dtest=TC004_Logout`

#### 🚦 Running Test Suites
1. Run smoke test suite:
   ```sh
   mvn test -DsuiteXmlFiles=src/test/java/web_saucedemo/tests/testng/testsuites/TS-001_SmokeTest.xml
   ```
2. Run regression test suite:
   ```sh
   mvn test -DsuiteXmlFiles=src/test/java/web_saucedemo/tests/testng/testsuites/TS-002_Regression.xml
   ```

### 🎭 Playwright Features
- **Cross-browser testing**: Chrome, Firefox, Edge support
- **Modern locators**: CSS selectors and data-test attributes
- **Auto-waiting**: Built-in smart waiting for elements
- **Browser contexts**: Isolated test execution
- **Headless/headed modes**: Configurable browser visibility

### 🏗️ Project Structure
```
src/test/java/
├── automation/
│   ├── enums/Browsers.java
│   └── playwright/PlaywrightFactory.java
└── web_saucedemo/
    ├── config/EnvironmentVariables.java
    ├── contexts/CheckoutYourInfoData.java
    ├── enums/AppMenu.java
    ├── pages/
    │   ├── BasePage.java
    │   ├── LoginPage.java
    │   ├── ProductsPage.java
    │   ├── ShoppingCartPage.java
    │   ├── CheckoutPage.java
    │   └── HeaderPage.java
    └── tests/testng/
        ├── testcases/
        │   ├── BaseTest.java
        │   ├── TC001_AddToCart_Checkout.java
        │   ├── TC002_AddToCart_Count.java
        │   ├── TC003_LockedOutUser.java
        │   └── TC004_Logout.java
        └── testsuites/
            ├── TS-001_SmokeTest.xml
            └── TS-002_Regression.xml
```

