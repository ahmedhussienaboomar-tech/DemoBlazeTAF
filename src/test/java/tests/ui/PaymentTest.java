package tests.ui;

import com.automationexercices.apis.UserManagementAPI;
import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.drivers.UITest;
import com.automationexercices.pages.*;
import com.automationexercices.utils.TimeManager;
import com.automationexercices.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tests.BaseTest;

@Epic("Automation Exercies")
@Feature("UI User Management")
@Story("User Payment")
@Severity(SeverityLevel.CRITICAL)
@Owner("Hussien")
@UITest
public class PaymentTest extends BaseTest {
    private final String timestamp = TimeManager.getSimpleTimestamp();


    @Test
    public void registerNewAccount() {
        new SignupLoginPage(driver).
                navigate()
                .enterSignUpName(testData.getJsonData("name"))
                .enterSignUpEmail(
                        testData.getJsonData("email")
                                + timestamp
                                + "@gmail.com").clickSignUpButton();
        new SignupPage(driver)
                .fillRegisterationForm(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("password"),
                        testData.getJsonData("day"),
                        testData.getJsonData("month"),
                        testData.getJsonData("year"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("companyName"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("country"),
                        testData.getJsonData("state"),
                        testData.getJsonData("city"),
                        testData.getJsonData("zipCode"),
                        testData.getJsonData("mobileNumber")
                )
                .clickCreateAccountButton()
                .verifyAccountCreatedLabel()
                .clickContinueButton().clickLogoutButton();
    }

    @Test(dependsOnMethods="registerNewAccount")
    public void loginToAccount() {
        new SignupLoginPage(driver).navigate()
                .enterValidLoginEmail(testData.getJsonData("email") + timestamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .navigationBarComponent.verifyUserLabel(
                        testData.getJsonData("name")
                );

    }
    @Test(dependsOnMethods= {"registerNewAccount","loginToAccount"})
    public void addProductToCart() {
        new ProductsPage(driver).navigate()
                .clickOnAddToCart(testData.getJsonData("product.name")).
                validateItemAddedLabelContains(
                        testData.getJsonData("messages.cartAdded")
                ).clickOnViewCart().verifyProductDetailsOnCart(
                        testData.getJsonData("product.name"),
                        testData.getJsonData("product.price"),
                        testData.getJsonData("product.quantity"),
                        testData.getJsonData("product.totalPrice")
                );

    }

    @Test(dependsOnMethods= {"registerNewAccount","loginToAccount","addProductToCart"})
    public void checkout() {
        new CartPage(driver).clickProceedToCheckout().verifyDeliveryDetails(
                testData.getJsonData("titleMale"),
                testData.getJsonData("firstName"),
                testData.getJsonData("lastName"),
                testData.getJsonData("companyName"),
                testData.getJsonData("address1"),
                testData.getJsonData("address2"),
                testData.getJsonData("city"),
                testData.getJsonData("state"),
                testData.getJsonData("zipCode"),
                testData.getJsonData("country"),
                testData.getJsonData("mobileNumber")
        ).verifyBillingDetails(
                testData.getJsonData("titleMale"),
                testData.getJsonData("firstName"),
                testData.getJsonData("lastName"),
                testData.getJsonData("companyName"),
                testData.getJsonData("address1"),
                testData.getJsonData("address2"),
                testData.getJsonData("city"),
                testData.getJsonData("state"),
                testData.getJsonData("zipCode"),
                testData.getJsonData("country"),
                testData.getJsonData("mobileNumber")
        );

    }

    @Test(dependsOnMethods= {"registerNewAccount","loginToAccount"
            ,"addProductToCart","checkout"})
    public void validPayment() {
        new CheckoutPage(driver).clickPlaceOrder().filldetails(
                testData.getJsonData("card.nameOnCard"),
                testData.getJsonData("card.cardNumber"),
                testData.getJsonData("card.cvc"),
                testData.getJsonData("card.expiryMonth"),
                testData.getJsonData("card.expiryYear")
        ).verifySuccessLabel(testData.getJsonData("messages.paymentSuccessLabel"));

    }

    @Test(dependsOnMethods= {"registerNewAccount","loginToAccount"
            ,"addProductToCart","checkout","validPayment"})
    public void deleteAccount(){
        new UserManagementAPI().deleteAccount(
                testData.getJsonData("email") + timestamp + "@gmail.com",
                testData.getJsonData("password")
        ).verifyUserDeletedSuccessfully();
    }








    // Configurations

    @BeforeClass
    public void setUp() {
        testData = new JsonReader("payment_data");
        driver = new GUIDriver();
    }



    @AfterClass
    public void tearDown() {
        driver.quitDriver();
    }
}
