package com.automationexercices.pages;

import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.pages.components.NavigationBarComponent;
import com.automationexercices.utils.dataReader.PropertyReader;
import com.automationexercices.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductsDetailsPage {
    GUIDriver driver  ;
    NavigationBarComponent navigationBarComponent;
    public ProductsDetailsPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBarComponent = new NavigationBarComponent(driver);
    }

    private final String productsDetailsPage ="/product_details/4";

    //Locators

    private final By productPrice = By.cssSelector(".product-information > span > span");
    private final By avelaility = By.xpath("//p[contains(normalize-space(.), 'In Stock')]");
    private final By name = By.id("name");
    private final By email = By.id("email");
    private final By reviewTextArea =By.id("review");
    private final By submitReview = By.id("button-review");
    private final By reviewAddedMessage =By.cssSelector("#review-section span");

    // Actions

    @Step("Navigate to productsDetailspage")
    public ProductsDetailsPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + productsDetailsPage);
        return this;
    }

    //Actions

    @Step("Add Review")
    public ProductsDetailsPage addReview(String name, String email , String review) {
        driver.element().type(this.name, name);
        driver.element().type(this.email, email);
        driver.element().type(this.reviewTextArea, review);
        driver.element().click(this.submitReview);
        return this;
    }

    //validation

    @Step("validate review add successfully")
    public ProductsDetailsPage validReviewAddSuccessflly(String expectedMessage){
        String actualMessage = driver.element().getText(reviewAddedMessage);
        LogsManager.info("Validating review added . Expected: " + expectedMessage + ", Actual: " + actualMessage);
        driver.verification().Equals(actualMessage, expectedMessage, "Review added message validation failed.");
        return this;
    }

    @Step("validate product Details ")
    public ProductsDetailsPage verifyProductDetails(String expectedPrice,String expectedAvailability){
        String actualPrice =driver.element().getText(productPrice);
        String actualAvailability = driver.element().getText(avelaility);
        LogsManager.info("Validating product details. Expected Price: " + expectedPrice + ", Actual Price: " + actualPrice);
        LogsManager.info("Validating product details. Expected Availability: " + expectedAvailability + ", Actual Availability: " + actualAvailability);
        driver.validation().Equals(actualPrice, expectedPrice, "Product price validation failed.");
        driver.validation().Equals(actualAvailability, expectedAvailability, "Product availability validation failed.");
        return this;
    }




}
