package com.automationexercices.pages;

import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.pages.components.NavigationBarComponent;
import com.automationexercices.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupLoginPage {
    private final GUIDriver driver;
    private final String loginPageUrl = "/login";

    public NavigationBarComponent navigationBarComponent;
    public SignupLoginPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBarComponent = new NavigationBarComponent(driver);
    }

    // Locators

    private final By LoginEmailInput = By.xpath("//input[@data-qa='login-email']");
    private final By LoginPasswordInput = By.xpath("(//input[@type='password' ] )[1]");
    private final By LoginButton = By.cssSelector("button[data-qa='login-button']");

    private final By SignupNameInput = By.cssSelector("input[data-qa='signup-name']");
    private final By emailInput = By.cssSelector("input[data-qa='signup-email']");
    private final By SignupButton = By.cssSelector("button[data-qa='signup-button']");

    private final By SignupLabel = By.xpath("//h2[contains(text(),'New User Signup!')]");
    private final By LoginError = By.cssSelector(".login-form  p");
    private final By RegisterError = By.cssSelector(".signup-form  p");


    // Actions
    @Step("Navigate to SignUp / login page")
    public SignupLoginPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") +loginPageUrl);
        return this;
    }

    @Step("Enter valid login email: {email}")
    public SignupLoginPage enterValidLoginEmail(String email){
        driver.element().type(LoginEmailInput, email);
        return this ;
    }

    @Step("Enter  login Password")
    public SignupLoginPage enterLoginPassword(String password){
        driver.element().type(LoginPasswordInput, password);
        return this ;
    }
    @Step("Click login Button")
    public SignupLoginPage clickLoginButton(){
        driver.element().click(LoginButton);
        return this ;
    }

    @Step("Enter SignUp Name")
    public SignupLoginPage enterSignUpName(String name){
        driver.element().type(SignupNameInput, name);
        return this ;
    }

    @Step("Enter SignUp Email")
    public SignupLoginPage enterSignUpEmail(String email){
        driver.element().type(this.emailInput, email);
        return this ;
    }

    @Step("Click SignUp Button")
    public SignupLoginPage clickSignUpButton(){
        driver.element().click(SignupButton);
        return this ;
    }

    @Step("Verify new user Signup Label is visible")
    public SignupLoginPage verifyNewUserSignupVisible(){
        driver.verification().isElementVisible(SignupLabel);
        return this;
    }

    @Step("Verify login error message {errorExpected}")
    public SignupLoginPage verifyLoginErrorMessage(String errorExpected){
        String actualErrorMessage = driver.element().getText(LoginError);
        driver.verification().Equals(actualErrorMessage, errorExpected, "Login error message is not as expected");
        return this;
    }

    @Step("Verify register error message {errorExpected}")
    public SignupLoginPage verifyRegisterErrorMessage(String errorExpected){
        String actualErrorMessage = driver.element().getText(RegisterError);
        driver.verification().Equals(actualErrorMessage, errorExpected, "Register error message is not as expected");
        return this;
    }






}
