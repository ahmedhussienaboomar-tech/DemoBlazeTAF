package tests.ui;

import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.drivers.UITest;
import com.automationexercices.pages.CartPage;
import com.automationexercices.pages.ProductsPage;
import com.automationexercices.pages.components.NavigationBarComponent;
import com.automationexercices.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;

@Epic("Automation Exercies")
@Feature("UI User Management")
@Story("Cart")
@Severity(SeverityLevel.CRITICAL)
@Owner("Hussien")
@UITest
public class CartTest extends BaseTest {


    @Test
    public void verifyProductDetailsOnCartWithoutLogin(){
        new ProductsPage(driver).navigate()
                .clickOnAddToCart(testData.getJsonData("products1.name")).
                validateItemAddedLabelContains(
                        testData.getJsonData("messages.cartAdded"))
             .clickOnViewCart()
             .verifyProductDetailsOnCart(
                     testData.getJsonData("products1.name"),
                     testData.getJsonData("products1.price"),
                     testData.getJsonData("products1.quantity"),
                     testData.getJsonData("products1.totalPrice")
             );
    }
    @Test
    public void verifySubscribtionWithoutLogin(){
        new CartPage(driver).navigate().subscripion(testData.getJsonData("email"))
              .verifysubscriptionsuccess(testData.getJsonData("messages.subscriptionSuccess"));
    }

//    @Test
//    public void verifySubscribtion(){
//        new ProductsPage(driver).navigate().clickOnAddToCart(testData.getJsonData("products1.name"))
//                .validateItemAddedLabelContains(
//                        testData.getJsonData("messages.cartAdded"))
//        . clickOnViewCart().subscripion(testData.getJsonData("email"))
//                .verifysubscriptionsuccess(testData.getJsonData("messages.subscriptionSuccess"));
//    }
//

//    @Test
//    public void verifyCheckoutWithoutLogin(){
//        new ProductsPage(driver).navigate()
//                .clickOnAddToCart(testData.getJsonData("products1.name"))
//                .validateItemAddedLabelContains(
//                        testData.getJsonData("messages.cartAdded"))
//                .clickOnViewCart().clickProceedToCheckout()
//                .verifyProceedToCheckout(testData.getJsonData("messages.checkoutMessage"));
//    }

    @BeforeClass
    protected void preCondition(){
        testData = new JsonReader("cart_data");
    }

    @BeforeMethod
    public void setUp() {
        driver = new GUIDriver();
        new NavigationBarComponent(driver).navigate();

    }

    @AfterMethod
    public void tearDown() {
        driver.quitDriver();
    }
}
