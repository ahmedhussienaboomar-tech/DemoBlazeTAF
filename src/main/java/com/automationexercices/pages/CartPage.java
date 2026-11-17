package com.automationexercices.pages;

import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.pages.components.NavigationBarComponent;
import com.automationexercices.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CartPage {
    GUIDriver driver   ;
    NavigationBarComponent navigationBarComponent;
    public CartPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBarComponent = new NavigationBarComponent(driver);
    }

    // variables
    private final String cartPage = "/view_cart";

    // locators

    private final By emailFailed =By.id("susbscribe_email");
    private final By subscribeButton = By.id("subscribe");
    private final By cartCheckout = By.cssSelector(".btn.btn-default.check_out");
    private final By subscribeMessage = By.cssSelector(".alert-success.alert");
    private final By checkoutMessage = By.cssSelector(".modal-title.w-100");


    // dynamic locators

   private By productName(String productName){
       return By.xpath("(//h4 /a[.='"+ productName +"'])[1]");
   }
    private By productPrice(String productName){
        return By.xpath("(//h4 /a[.='"+ productName +"'] //following::td[@class='cart_price']/p)[1]");
    }
    private By productQuantity(String productName){
        return By.xpath("(//h4 /a[.='"+ productName +"'] //following::td[@class='cart_quantity']/button)[1]");
    }
    private By productTotal(String productName){
        return By.xpath("(//h4 /a[.='"+ productName +"'] //following::td[@class='cart_total']/p)[1]");
    }
    private By removeProductButton(String productName){
        return By.xpath("(//h4 /a[.='"+ productName +"'] //following::td[@class='cart_delete']/a)[1]");
    }

    // Actions

    @Step("Navigate to cart page")
    public CartPage navigate() {
       driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb")+ cartPage);
       return this;
    }

    @Step("Click proceed to checkout")
    public CheckoutPage clickProceedToCheckout() {
        driver.element().click(cartCheckout);
        return new CheckoutPage(driver);
    }

    @Step("Remove product from cart")
    public CartPage removeProduct(String productName) {
        driver.element().click(removeProductButton(productName));
        return this;
    }

    @Step("Click proceed to Subscription")
    public CartPage subscripion(String email){
       driver.element().type(this.emailFailed,email)
               .click(this.subscribeButton);
       return this;
    }




    // validations

    @Step("Verify product details on cart")
    public CartPage verifyProductDetailsOnCart(String productName, String productPrice,
                                               String productQuantity, String productTotal){
       String actualProductName = driver.element().getText(productName(productName));
       String actualProductPrice = driver.element().getText(productPrice(productName));
       String actualProductQuantity = driver.element().getText(productQuantity(productName));
       String actualProductTotal = driver.element().getText(productTotal(productName));
       driver.validation().Equals(actualProductName, productName, "Product name on cart is not as expected")
               .Equals(actualProductPrice, productPrice, "Product price on cart is not as expected")
               .Equals(actualProductQuantity, productQuantity, "Product quantity on cart is not as expected")
               .Equals(actualProductTotal, productTotal, "Product total on cart is not as expected");
       return this;

    }

    @Step("Verify subscription success")
    public CartPage verifysubscriptionsuccess(String expectedMessage){
       String actualMessage = driver.element().getText(subscribeMessage);
       driver.verification().Equals(actualMessage, expectedMessage, "Subscription message is not as expected");
       return this;
    }

    @Step("Verify proceed to checkout")
    public CartPage verifyProceedToCheckout(String expectedMessage){
        String actualMessage = driver.element().getText(checkoutMessage);
        driver.verification().Equals(actualMessage, expectedMessage, "Checkout message is not as expected");
        return this;
    }


}
