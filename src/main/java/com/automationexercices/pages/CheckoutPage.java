package com.automationexercices.pages;

import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CheckoutPage {
    private final GUIDriver driver;
    public CheckoutPage(GUIDriver driver) {
        this.driver = driver;
    }

    // var
    private final String checkoutEndPoint = "/checkout";

    // locators

    private final By placeOrderButton = By.xpath("//div /a[@href='/payment']");

    //delivery
    private final By deliveryName = By.cssSelector("#address_delivery > .address_firstname.address_lastname");
    private final By deliveryCompany = By.cssSelector("#address_delivery > .address_address1.address_address2:nth-of-type(3)");
    private final By deliveryAddress1 = By.cssSelector("#address_delivery > .address_address1.address_address2:nth-of-type(4)");
    private final By deliveryAddress2 = By.cssSelector("#address_delivery > .address_address1.address_address2:nth-of-type(5)");
    private final By deliveryCountry = By.cssSelector("#address_delivery > .address_country_name");
    private final By deliveryPhone = By.cssSelector("#address_delivery > .address_phone");
    private final By deliveryCityStateZipCode = By.cssSelector("#address_delivery > .address_city.address_state_name.address_postcode");

    //billing
    private final By billingName = By.cssSelector("#address_invoice > .address_firstname.address_lastname");
    private final By billingCompany = By.cssSelector("#address_invoice > .address_address1.address_address2:nth-of-type(3)");
    private final By billingAddress1 = By.cssSelector("#address_invoice > .address_address1.address_address2:nth-of-type(4)");
    private final By billingAddress2 = By.cssSelector("#address_invoice > .address_address1.address_address2:nth-of-type(5)");
    private final By billingCountry = By.cssSelector("#address_invoice > .address_country_name");
    private final By billingPhone = By.cssSelector("#address_invoice > .address_phone");
    private final By billingCityStateZipCode = By.cssSelector("#address_invoice > .address_city.address_state_name.address_postcode");



    //actions
    @Step("Navigate to checkout page")
    public CheckoutPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + checkoutEndPoint);
        return this;
    }

    @Step("Click place order button")
    public OrderPaymentPage clickPlaceOrder(){
        driver.element().click(placeOrderButton);
        return new OrderPaymentPage(driver);
    }

    //Validations

    @Step("Verify delivery Address")
    public CheckoutPage verifyDeliveryDetails(String title, String fName ,String lName ,String company ,String address1
            ,String address2 , String city , String state , String zipCode , String country , String phone ){
        driver.validation().Equals(driver.element().getText(deliveryName), title + ". " + fName + " " + lName, "Delivery name is not as expected")
                .Equals(driver.element().getText(deliveryCompany), company, "Delivery company is not as expected")
                .Equals(driver.element().getText(deliveryAddress1), address1, "Delivery address1 is not as expected")
                .Equals(driver.element().getText(deliveryAddress2), address2, "Delivery address2 is not as expected")
                .Equals(driver.element().getText(deliveryCityStateZipCode), city + " " + state + " " + zipCode, "Delivery city/state/zip is not as expected")
                .Equals(driver.element().getText(deliveryCountry), country, "Delivery country is not as expected")
                .Equals(driver.element().getText(deliveryPhone), phone, "Delivery phone is not as expected");
        return this;
    }

    @Step("Verify billing Address")
    public CheckoutPage verifyBillingDetails(String title, String fName ,String lName ,String company ,String address1
            ,String address2 , String city , String state , String zipCode , String country , String phone ){
        driver.validation().Equals(driver.element().getText(billingName), title + ". " + fName + " " + lName, "Billing name is not as expected")
                .Equals(driver.element().getText(billingCompany), company, "Billing company is not as expected")
                .Equals(driver.element().getText(billingAddress1), address1, "Billing address1 is not as expected")
                .Equals(driver.element().getText(billingAddress2), address2, "Billing address2 is not as expected")
                .Equals(driver.element().getText(billingCityStateZipCode),  city + " " + state + " " + zipCode,    "Billing city/state/zip is not as expected")
                .Equals(driver.element().getText(billingCountry), country, "Billing country is not as expected")
                .Equals(driver.element().getText(billingPhone), phone, "Billing phone is not as expected");
        return this;
    }

}
