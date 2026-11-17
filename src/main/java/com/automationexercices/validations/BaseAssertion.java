package com.automationexercices.validations;

import com.automationexercices.utils.WaitManager;
import com.automationexercices.utils.actions.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
/*
*ليه عملت BaseAssertion؟
بدل ما تكرر نفس كود التحقق داخل Validation وVerification، إنت جمعت الوظائف المشتركة في Class واحدة.
مثلًا، الكلاسين محتاجين:
*/
public abstract class BaseAssertion {
    /*
    * ليه abstract؟
لأن BaseAssertion مش المفروض تحدد بنفسها هل التحقق هيكون Hard أو Soft. انت اورث وحدد مع نفسك
     */
    protected WebDriver driver;
    protected WaitManager waitManager;
    protected ElementActions elementActions;

    protected BaseAssertion(){} // Default constructor for restAssure

    protected BaseAssertion(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
        this.elementActions = new ElementActions(driver);
    }

    protected abstract void assertTrue(boolean condition, String message);

    protected abstract void assertFalse(boolean condition, String message);

    protected abstract void assertEquals(String actual, String expected, String message);

    /*
    * لاحظ إن مفيش Body للـ Methods دي. الكلاسات الوارثة هي اللي بتحدد التنفيذ الفعلي.
وده تطبيق لفكرة Abstraction: الكلاس الأساسي بيحدد العمليات المطلوبة، والكلاسات الوارثة بتحدد إزاي العمليات دي هتتنفذ.
    */

    public BaseAssertion Equals(String actualText, String expectedText, String message) {
        assertEquals(actualText, expectedText, message);
        return this;
    }

    public void isElementVisible(By locator) {
        boolean flag = waitManager.fluentWait().until(d -> {
            try {
                d.findElement(locator).isDisplayed();
                return true;
            } catch (Exception e) {
                return false;
            }
        });
        assertTrue(flag, "Element is not visible: " + locator);
    }
    //verify page title
    public void verifyPageTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        assertEquals(actualTitle, expectedTitle, "Page title does not match. Expected: "
                + expectedTitle + ", but got: " + actualTitle);

    /*
    * دي بتجيب العنوان الفعلي من المتصفح، وبعدها تستخدم assertEquals() للتحقق من مطابقته للعنوان المتوقع.
الميزة إن BaseAssertion مش محتاجة تعرف هل assertEquals() هتكون Hard ولا Soft؛ ده بيتحدد حسب الـ Implementation اللي ورث منها الكود.
    ونفس الفكرة موجودة في verifyPageUrl()، اللي بتقارن الرابط الحالي بالرابط المتوق
    */
    }


    //verify page url
    public void verifyPageUrl(String expectedUrl) {
        String actualUrl = driver.getCurrentUrl();
        assertEquals(actualUrl, expectedUrl, "Page URL does not match. Expected: "
                + expectedUrl + ", but got: " + actualUrl);
    }
}