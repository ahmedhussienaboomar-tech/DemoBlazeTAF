package com.automationexercices.pages;

import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.pages.components.NavigationBarComponent;
import com.automationexercices.utils.dataReader.PropertyReader;
import com.automationexercices.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductsPage {
    private final GUIDriver driver;
    private final NavigationBarComponent navigationBarComponent;
    public ProductsPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBarComponent = new NavigationBarComponent(driver);
    }

    //URL
    private final String productsPage = "/products";

    //Locators
    private By searchField = By.xpath("//input[@name='search']");
    private By searchButton = By.id("submit_search");
    private By itemAddedToCartLabel = By.xpath("//h4[@class='modal-title w-100']");
    private By viewCartButton = By.cssSelector("p > [href='/view_cart']");
    private By continueShoppingButton = By.cssSelector(".modal-footer > button");

    //dynamic Locators
    private By productName(String productName) {
        return By.xpath(
                "//div[contains(@class,'product-image-wrapper')]"+"//div[contains(@class,'overlay-content')]"+"/p[normalize-space(.)='"+ productName +"']"
        );
    }

    private By productPrice(String productName) {
        return By.xpath(
                "//div[contains(@class,'product-image-wrapper')]"+"//div[contains(@class,'overlay-content')]"
                        +"/p[normalize-space(.)='"+ productName +"']"+"/preceding-sibling::h2"
        );
    }

    private By hoverProduct(String productName){
        return By.xpath("//div[@class='productinfo text-center']/p[.='"+ productName +"']");
    }

    private By addProductToCart(String productName) {
        return By.xpath(
                "//div[contains(@class,'product-image-wrapper')]"
                        + "[.//p[normalize-space(.)='"+ productName +"']]"
                        + "//a[contains(@class,'add-to-cart')]"
        );
    }

    private By viewProduct(String productName){
        return By.xpath("//div[contains(@class,'product-image-wrapper')][.//p[normalize-space()='"+ productName +"']]//a[contains(@href,'/product_details/')]");
    }


    //actions

    @Step("Navigate to products page")
    public ProductsPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb")+ productsPage);
        return new ProductsPage(driver);
    }

    @Step("Search for a product {productName}")
    public ProductsPage searchProduct(String productName){
        driver.element().type(searchField, productName)
                .click(searchButton);
        return this;
    }

    @Step("Add product to cart {productName}")
    public ProductsPage clickOnAddToCart(String productName) {
        driver.element().hoverAndClick(
                hoverProduct(productName),
                addProductToCart(productName)
        );
        return this;
    }

    @Step("click View product {productName}")
    public ProductsDetailsPage clickOnViewProduct(String productName){
        driver.element().click(viewProduct(productName));
        return new ProductsDetailsPage(driver);
    }

    //ToDo: add validation for item added to cart label
    @Step("Click on view cart")
    public CartPage clickOnViewCart(){
        driver.element().click(viewCartButton);
        return new CartPage(driver);
    }

    @Step("Click on continue shopping")
    public ProductsPage clickOnContinueShopping(){
        driver.element().click(continueShoppingButton);
        return this;
    }



    //validation
    @Step("Valid product details for {productName} with price {productPrice}")
    public ProductsPage validateProductDetails(
            String productName, String productPrice){
        driver.element().hover(hoverProduct(productName));

        String actualProductName = driver.element()
                .getText(productName(productName));
        String actualProductPrice = driver.element()
                .getText(productPrice(productName));

        LogsManager.info("Actual product name: " + actualProductName);
        LogsManager.info("Actual product price: " + actualProductPrice);

        driver.validation().Equals(
                actualProductName, productName, "Product name validation failed");
        driver.validation().Equals(
                actualProductPrice, productPrice, "Product price validation failed");

        return this;
    }

    @Step("Validate item added label contains{expectedText}")
    public ProductsPage validateItemAddedLabelContains(String expectedText)
    {
        String actualText = driver.element().getText(itemAddedToCartLabel);
        LogsManager.info("Actual item added label: " + actualText);
        driver.verification().Equals(actualText, expectedText, "Item added label validation failed");
        return this;
    }

}
