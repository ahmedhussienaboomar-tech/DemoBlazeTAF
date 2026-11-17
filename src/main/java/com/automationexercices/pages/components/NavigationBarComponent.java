package com.automationexercices.pages.components;

import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.pages.*;
import com.automationexercices.utils.dataReader.PropertyReader;
import com.automationexercices.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
/*
*لو نفس الـ Navigation Bar موجود في أكتر من صفحة
* ، بدل ما تكرر الـ Locators والعمليات الخاصة بيه في كل Page، تقدر تمثله في Class منفصل.
*/
public class NavigationBarComponent {

    private final GUIDriver driver;

    public NavigationBarComponent(GUIDriver driver) {
        this.driver = driver;
        //ب. ليه الـ Constructor بيستقبل GUIDriver؟
        //علشان الـ Component تقدر تستخدم نفس الـ Driver اللي بيستخدمه باقي الـ Framework.
    }


    //Locators
    private final By homeButton = By.xpath("//a[.=' Home']");
    private final By productsButton = By.cssSelector("a[href='/products']");
    private final By cartButton = By.xpath("//a[contains (text(),'Cart')] ");
    private final By logoutButton = By.xpath("//a[.=' Logout']");
    private final By signupLoginButton = By.xpath("//a[.=' Signup / Login']");
    private final By testCasesButton = By.xpath("//a[.=' Test Cases ']");
    private final By deleteAccountButton = By.xpath("//a[.=' Delete Account']");
    private final By apiButton = By.xpath("//a[.=' API Testing']");
    private final By videoTutorials = By.xpath("//a[.=' Video Tutorials']");
    private final By contactUsButton = By.xpath("//a[.=' Contact us']");
    private final By homePageLabel = By.cssSelector("h1 > span");
    private final By userLabel = By.tagName("b");

    //Actions
    @Step("Navigate to Home Page")
    public NavigationBarComponent navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb"));
        return this;
    }

    @Step("Click on Home Button")
    public NavigationBarComponent clickHomeButton() {
        driver.element().click(homeButton);
        return this;
    }

    @Step("Click on Products Button")
    public ProductsPage clickProductsButton() {
        driver.element().click(productsButton);
        return new ProductsPage(driver);
    }

    @Step("Click on Cart Button")
    public CartPage clickCartButton() {
        driver.element().click(cartButton);
        return new CartPage(driver);
    }

    @Step("Click on Sign Up/Login Button")
    public SignupLoginPage clickSignUpLoginButton() {
        driver.element().click(signupLoginButton);
        return new SignupLoginPage(driver);
    }

    @Step("Click on Logout Button")
    public LogoutPage clickLogoutButton() {
        driver.element().click(logoutButton);
        return new LogoutPage(driver);
    }

    @Step("Click on Contact Us Button")
    public ContactUsPage clickContactUsButton() {
        driver.element().click(contactUsButton);
        return new ContactUsPage(driver);
    }

    @Step("Click on Test Cases Button")
    public TestCasesPage clickTestCasesButton() {
        driver.element().click(testCasesButton);
        return new TestCasesPage(driver);
    }

    @Step("Click on Delete Account Button")
    public DeleteAccountPage clickDeleteAccountButton() {
        driver.element().click(deleteAccountButton);
        return new DeleteAccountPage(driver);
    }


    // validation methods
    @Step("Verify Homepage Label")
    public NavigationBarComponent verifyHomepage() {
        driver.verification().isElementVisible(homePageLabel);
        return this;
    }

    @Step("Verify User Name Label")
    public NavigationBarComponent verifyUserLabel(String expectedUserName) {
        String actualUserName = driver.element().getText(userLabel);
        LogsManager.info("Verifying user name label: " + actualUserName);
        driver.verification().Equals(actualUserName, expectedUserName, "User name does not match. Expected: "
                + expectedUserName + ", but got: " + actualUserName);
        return this;
    }

}
