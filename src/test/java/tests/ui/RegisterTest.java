package tests.ui;

import com.automationexercices.apis.UserManagementAPI;
import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.drivers.UITest;
import com.automationexercices.pages.SignupLoginPage;
import com.automationexercices.pages.SignupPage;
import com.automationexercices.pages.components.NavigationBarComponent;
import com.automationexercices.utils.TimeManager;
import com.automationexercices.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;

@Epic("Automation Exercies")
@Feature("UI User Management")
@Story("User Registration")
@Severity(SeverityLevel.CRITICAL)
@Owner("Hussien")
@UITest
public class RegisterTest extends BaseTest {

    String timestamp = TimeManager.getSimpleTimestamp();
    // test methods

    @Description("Verify User can signup with valid data")
    @Test(priority = 1)
    public void validSignUpTC(){
        new SignupLoginPage(driver).
                navigate()
                .enterSignUpName(testData.getJsonData("name"))
                .enterSignUpEmail(
                        testData.getJsonData("email")
                                + timestamp
                                + "@gmail.com"
                )
                .clickSignUpButton();
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
                .clickContinueButton();
        new UserManagementAPI().deleteAccount(
                testData.getJsonData("email") + timestamp + "@gmail.com",
                testData.getJsonData("password")
        ).verifyUserDeletedSuccessfully();


    }


    @Description("Verify User can not signup with valid data")
    @Test(priority = 2)
    public void invalidSignUpTC() {
        new UserManagementAPI().createRegisterUserAccount(
                testData.getJsonData("name"),
                testData.getJsonData("email")
                        + timestamp
                        + "@gmail.com",
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
        ).verifyUserCreatedSuccessfully();
    new SignupLoginPage(driver)
                .navigate()
                .enterSignUpName(testData.getJsonData("name"))
                .enterSignUpEmail(
                        testData.getJsonData("email")
                                + timestamp
                                + "@gmail.com"
                )
                .clickSignUpButton().verifyRegisterErrorMessage(testData.getJsonData("messages.error"));


    }


    // Configurations

    @BeforeClass
    public void Precondition() {
        testData = new JsonReader("register-data");
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
