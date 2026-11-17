package com.automationexercices.validations;

import com.automationexercices.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

// Soft Assersion
public class Validation extends BaseAssertion{


    /*
    * أ. ليه softAssert متغير static؟
    * ده معناه إن كل Objects من Validation بتشارك نفس المتغير على مستوى الـ Class، بدل ما يكون لكل Object نسخة مستقلة.
    */
    private static SoftAssert softAssert = new SoftAssert();

    private static boolean used = false; //إنت بتستخدمها علشان تعرف هل حصلت أي Assertion بالفعل.

    public Validation(){
        super(); /// دي اللي بتخليني اورث ال parent constructor
    } // Default constructor for restAssure

    public Validation(WebDriver driver) {
        super(driver);
    }

    protected void assertTrue(boolean condition, String message) {
        used = true;
        softAssert.assertTrue(condition, message);
    }

    protected void assertFalse(boolean condition, String message) {
        used = true;
        softAssert.assertFalse(condition, message);
    }

    protected void assertEquals(String actual, String expected, String message) {
        used = true;
        softAssert.assertEquals(actual, expected, message);
    }

    public static void assertAll(ITestResult result) {
        if (!used) return;
        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            LogsManager.error("Soft assertion failed: " + e.getMessage());
            result.setStatus(ITestResult.FAILURE);
            result.setThrowable(e);
        }
        finally {
            softAssert = new SoftAssert(); // Reset the SoftAssert instance for the next test
            /*هل ده صح في الـ Framework بتاعك؟
أيوه، ممكن يشتغل، لكن فيه نقطة مهمة: لو هدفك إنشاء SoftAssert جديدة لكل Test، فالأوضح والأفضل عادةً إنك تستخدم @BeforeMethod في TestN :
            */


            /*
            * soft assert  بيخزن النتايج عنده
            * فانتا لو معملتلهوش reset بعد ما تعمل assertAll
            * هو حرفيا هيفضل يعمل run لكل الtestcases بنفس ال object
            لو اول كيس فييلت هيخلي كل الباقي اتللي بعدها يفيييل*
            *
            * ///هتباانت اكتر لما نربطها بال testNG Listeners */

        }
    }
    /*
    *واضح إن هدفك إنك تجمع Soft Assertions اللي بتحصل خلال الاختبار، وبعدها تنفذ assertAll() من الـ Listener.
لكن هنا فيه مخاطرة مهمة: لو اختباراتك بتشتغل بالتوازي، الـ SoftAssert المشتركة ممكن تجمع إخفاقات من أكثر من Test في نفس الوقت. وكمان SoftAssert نفسها مش مصممة للاستخدام المتزامن بين Threads من غير تنظيم.
لذلك لو عايز دعم Parallel Execution بشكل سليم، الأفضل تكون نتائج الـ Soft Assertions مستقلة لكل Test، مثلًا عن طريق تخزينها في سياق خاص بالاختبار بدل مشاركة Object واحدة عالميًا.
    */
}
