package com.automationexercices.pages;

import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.pages.components.NavigationBarComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupPage {
    private final GUIDriver driver;

    public SignupPage(GUIDriver driver) {
        this.driver = driver;
    }

    // Locator

    private final By SignupPasswordInput = By.id("password");
    private final By day =By.id("days");
    private final By month =By.id("months");
    private final By year =By.id("years");
    private final By newsletter =By.id("newsletter");
    private final By specialOffers =By.id("optin");
    private final By firstName =By.id("first_name");
    private final By lastName =By.id("last_name");
    private final By company =By.id("company");
    private final By address1 =By.id("address1");
    private final By address2 =By.id("address2");
    private final By country =By.id("country");
    private final By state =By.id("state");
    private final By city =By.id("city");
    private final By zipCode =By.id("zipcode");
    private final By mobileNumber =By.id("mobile_number");
    private final By createAccount =By.xpath("(//button[@type='submit'])[1]");
    private final By accountCreatedLabel =By.xpath("//b[contains(text(),'Account Created!')]");
    private final By continueButton =By.xpath("//a[@data-qa='continue-button']");


    //dynamic locator
    @Step("choose title {title}") /// dynamic locator for radio button
    public void chooseTitle(String title) {
        By titleLocator = By.cssSelector("input[value='"+title+"']");
        driver.element().click(titleLocator);
    }

    // actions




    @Step("Fill Registeration Form")
    public SignupPage fillRegisterationForm(
            String title,
            String password,
            String day,
            String month,
            String year,
            String firstName,
            String lastName,
            String company,
            String address1,
            String address2,
            String country,
            String state,
            String city,
            String zipCode,
            String mobileNumber)
    {
        chooseTitle(title);
        driver.element().type(this.SignupPasswordInput, password);
        driver.element().selectFromDropdown(this.day, day);
        driver.element().selectFromDropdown(this.month, month);
        driver.element().selectFromDropdown(this.year, year);
        driver.element().click(this.newsletter);
        driver.element().click(this.specialOffers);
        driver.element().type(this.firstName, firstName);
        driver.element().type(this.lastName, lastName);
        driver.element().type(this.company, company);
        driver.element().type(this.address1, address1);
        driver.element().type(this.address2, address2);
        driver.element().selectFromDropdown(this.country, country);
        driver.element().type(this.state, state);
        driver.element().type(this.city, city);
        driver.element().type(this.zipCode, zipCode);
        driver.element().type(this.mobileNumber, mobileNumber);
        return this;
    }

    @Step("Click create account button")
    public SignupPage clickCreateAccountButton(){
        driver.element().click(createAccount);
        return this;
    }

    @Step("Click continue button")
    public NavigationBarComponent clickContinueButton(){
        driver.element().click(continueButton);
        return new NavigationBarComponent(driver);
    }

    // verifications
    @Step("Verify account created label")
    public SignupPage verifyAccountCreatedLabel(){
        driver.verification().isElementVisible(accountCreatedLabel);
        return this;
    }


}
