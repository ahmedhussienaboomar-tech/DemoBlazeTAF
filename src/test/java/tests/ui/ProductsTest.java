package tests.ui;

import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.pages.ProductsPage;
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
@Story("Product Management")
@Severity(SeverityLevel.CRITICAL)
@Owner("Hussien")
public class ProductsTest extends BaseTest {


    private final String timeStamp = TimeManager.getSimpleTimestamp();



    @Test
    public void searchForProductWithoutLogin() {
        new ProductsPage(driver).navigate()
                .searchProduct(testData.getJsonData("searchedProduct.name"))
                .validateProductDetails(
                        testData.getJsonData("searchedProduct.name"),
                         testData.getJsonData("searchedProduct.price")
                );
    }
    @Test
    public void addProductToCarWithoutLogin() {
        new ProductsPage(driver).navigate()
                .clickOnAddToCart(testData.getJsonData("products1.name")).
                validateItemAddedLabelContains(
                        testData.getJsonData("messages.cartAdded")
                );
    }






    @BeforeClass
    protected void preCondition(){
        testData = new JsonReader("products_data");
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
