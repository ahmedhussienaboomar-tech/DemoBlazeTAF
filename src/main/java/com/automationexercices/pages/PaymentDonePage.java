package com.automationexercices.pages;

import com.automationexercices.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class PaymentDonePage {
    private GUIDriver driver;

    PaymentDonePage(GUIDriver driver){
        this.driver=driver;
    }

    private final By succesLabel = By.cssSelector(".title.text-center > b");
    private final By contine=By.cssSelector(".btn.btn-primary");
    private final By downloadInvoice = By.cssSelector(".btn.btn-default.check_out");



    @Step("Click Contine")
    public void clickContine() {
        driver.element().click(contine);
    }


    @Step("Click Download Invoice")
    public void clickDownloadInvoice() {
        driver.element().click(downloadInvoice);
    }


    @Step("Verify Success Label")
    public PaymentDonePage verifySuccessLabel(String expectedMessage) {
        driver.verification().Equals(driver.element().getText(succesLabel),expectedMessage,
                "Payment success message is not as expected");
        return this;
    }
}
