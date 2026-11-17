package com.automationexercices.pages;

import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class OrderPaymentPage {
    private GUIDriver driver;
    public OrderPaymentPage(GUIDriver driver) {
        this.driver=driver;
    }

    private final String orderPaymentEndPoint = "/payment";


    //locators

    private final By nameOnCard = By.xpath("//input[@name='name_on_card']");
    private final By cardNumber = By.xpath("//input[@name='card_number']");
    private final By cvc = By.xpath("//input[@name='cvc']");
    private final By expiryMonth = By.xpath("//input[@name='expiry_month']");
    private final By expiryYear = By.xpath("//input[@name='expiry_year']");
    private final By payAndConfirmOrderButton = By.id("submit");
    private final By SuccessPayment = By.cssSelector("#success_message > .alert-success.alert");
    //

    @Step("Fill payment details")
    public PaymentDonePage filldetails(String nameOnCard, String cardNumber,String cvc,
                                        String expiryMonth,String expiryYear){
        driver.element().type(this.nameOnCard,nameOnCard)
                .type(this.cardNumber,cardNumber)
                .type(this.cvc,cvc)
                .type(this.expiryMonth,expiryMonth)
                .type(this.expiryYear,expiryYear).click(payAndConfirmOrderButton);
        return new PaymentDonePage(driver);
    }

    //actions
    @Step("Navigate to checkout page")
    public OrderPaymentPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + orderPaymentEndPoint);
        return this;
    }



    //Validations
    @Step("Verify payment success")
    public OrderPaymentPage verifyPayment(String expectedMessage){
        driver.verification().Equals(driver.element().getText(SuccessPayment),expectedMessage,
                "Payment success message is not as expected");
        return this;
    }


}
