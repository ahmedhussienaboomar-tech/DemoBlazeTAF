package tests.ui;

import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.pages.ProductsPage;
import com.automationexercices.pages.components.NavigationBarComponent;
import com.automationexercices.utils.dataReader.JsonReader;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;

public class ProductsDetailsTest extends BaseTest {


    @Test
    public void verifyProductDetailsWithoutLogin(){
        new ProductsPage(driver).navigate().clickOnViewProduct(testData.getJsonData("product.name")).verifyProductDetails(
                testData.getJsonData("product.price"),
                testData.getJsonData("product.availability")
        );
    }

    @Test
    public void verifyReviewWithoutLogin(){
        new ProductsPage(driver).navigate().clickOnViewProduct(
                testData.getJsonData("product.name"))
                .addReview(testData.getJsonData("review.name"),
                testData.getJsonData("review.email"),
                testData.getJsonData("review.text")
        ).validReviewAddSuccessflly(
                testData.getJsonData("review.message")
        );
    }




    @BeforeClass
    protected void preCondition(){
        testData = new JsonReader("products_details_data");
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
