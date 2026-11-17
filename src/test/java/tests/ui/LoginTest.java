package tests.ui;

import com.automationexercices.apis.UserManagementAPI;
import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.drivers.UITest;
import com.automationexercices.pages.SignupLoginPage;
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
@Story("User Login")
@Severity(SeverityLevel.CRITICAL)
@Owner("Hussien")
@UITest
public class LoginTest extends BaseTest {

    private final String timestamp = TimeManager.getSimpleTimestamp();

    @Description("User can with valid credentials")
    @Test
    public void validLoginTc(){

        new UserManagementAPI().createRegisterUserAccount(
                testData.getJsonData("name"),
                testData.getJsonData("email")
                        + timestamp
                        + "@gmail.com",
                testData.getJsonData("password"),
                testData.getJsonData("firstName"),
                testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver).navigate()
                .enterValidLoginEmail(testData.getJsonData("email") + timestamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .navigationBarComponent.verifyUserLabel(
                        testData.getJsonData("name")
                );

        new UserManagementAPI().deleteAccount(
                testData.getJsonData("email") + timestamp + "@gmail.com",
                testData.getJsonData("password")
        ).verifyUserDeletedSuccessfully();
    }

    @Description("User cannot login with invalid email")
    @Test
    public void inValidLoginEmailTc(){

        new UserManagementAPI().createRegisterUserAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email")
                                + timestamp
                                + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver).navigate()
                .enterValidLoginEmail(testData.getJsonData("email") + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .verifyLoginErrorMessage(
                        testData.getJsonData("messages.error")
                );

        new UserManagementAPI().deleteAccount(
                testData.getJsonData("email") + timestamp + "@gmail.com",
                testData.getJsonData("password")
        ).verifyUserDeletedSuccessfully();
    }

    @Description("User cannot login with invalid password")
    @Test
    public void inValidLoginPasswordTc(){

        new UserManagementAPI().createRegisterUserAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email")
                                + timestamp
                                + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver).navigate()
                .enterValidLoginEmail(testData.getJsonData("email") + timestamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password") + timestamp)
                .clickLoginButton()
                .verifyLoginErrorMessage(
                        testData.getJsonData("messages.error")
                );

        new UserManagementAPI().deleteAccount(
                testData.getJsonData("email") + timestamp + "@gmail.com",
                testData.getJsonData("password")
        ).verifyUserDeletedSuccessfully();
    }







    @BeforeClass
    protected void preCondition(){
        testData = new JsonReader("login-data");
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
