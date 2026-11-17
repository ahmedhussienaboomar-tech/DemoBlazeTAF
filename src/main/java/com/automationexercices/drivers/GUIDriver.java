package com.automationexercices.drivers;

import com.automationexercices.utils.actions.AlertActions;
import com.automationexercices.utils.actions.BrowserActions;
import com.automationexercices.utils.actions.ElementActions;
import com.automationexercices.utils.actions.FrameActions;
import com.automationexercices.validations.Validation;
import com.automationexercices.validations.Verification;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;
import com.automationexercices.utils.dataReader.PropertyReader;
import com.automationexercices.utils.logs.LogsManager;

public class GUIDriver {

    private ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
//    المقصود إن GUIDriver يقرأ browserType، وبعدها يستخدم Browser.valueOf() لاختيار الـ Factory.
    private final String browser = PropertyReader.getProperty("browserType");
    ///////// كل الفكره من الكومناتا اللي تحت دي اني بقيت اعمل الحااجات بتعنعا فالكونستراكتور واول مبيتنادلى عليه هو بيعمل كل حاجه
    public GUIDriver() {
        //WebDriver driver = ThreadGuard.protect(getDriver());
        LogsManager.info("Initializing WebDriver for browser: " + browser);
        Browser browserType = Browser.valueOf(browser.toUpperCase());
        LogsManager.info("Browser type: " + browserType);
        //عامل دي علشان لو مثلا بفتح كروم وفتحلي ايدج فابقى عارف هو فتح ايه
        AbstractDriver abstractDriver = browserType.getDriverFactory(); //هاتلي الـ Factory المسؤولة عن Chrome. مثلا او edge ايا كان
        WebDriver driver = ThreadGuard.protect(abstractDriver.createDriver());

        driverThreadLocal.set(driver); // بتخزن المرجع للـ Thread الحالية.
        System.out.println("Browser: " + driver.getClass().getName());
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page title: " + driver.getTitle());
        //remove() بتمسح القيمة المخزنة للـ Thread الحالية.
    }

    /*
* لاحظ الفرق بين ThreadLocal وThreadGuard:
ThreadLocal	تخزين قيمة مستقلة لكل Thread.
ThreadGuard	اكتشاف استخدام الـ Driver من Thread غير الـ Thread اللي أنشأته، وإظهار خطأ بدل السماح بالاستخدام غير الصحيح.
هما مش بديلين لبعض؛ إنت مستخدمهم لتحقيق غرضين مختلفين.
*/

    public  WebDriver initDriver(String browser) {
    /*
* سم الميثود بيوحي إنها مسؤولة عن إنشاء الـ Driver، لكن التنفيذ الحالي مش بيعمل ده؛ هو بيرجع القيمة المخزنة فقط
كمان الـ Parameter المسمى browser مش مستخدم داخل الميثود.
السبب الظاهر من الكود إنك نقلت عملية الإنشاء للـ Constructor، لكن سبت الميثود القديمة موجودة.
بالتالي، في التصميم الحالي:
- الـ Constructor هو اللي بينشئ الـ Driver.
- get() بترجعه.
- initDriver() مش بتعمل Initialization فعلية.
    */
        return driverThreadLocal.get(); //بترجع المرجع المخزن للـ Thread الحالية.
    /*
    ليه get() مهمة؟
    لما أي Class تحتاج تتعامل مع المتصفح، بتقدر تستدعي get() بدل ما تنشئ Driver جديد.
    /// WebDriver driver = guiDriver.get();
    /// driver.get("https://example.com");
    */

    }
    public  void quitDriver() {
        driverThreadLocal.get().quit();
    }


    public WebDriver get() {
        //implement
        return driverThreadLocal.get();

        /*
        لو get() رجعت null، هيحصل NullPointerException.
        بعد quit()، المرجع لسه موجود في ThreadLocal؛ الأفضل عادةً تنظيفه باستخدام remove() بعد إغلاق الجلسة، مع مراعاة التعامل مع أي استثناء.
        */

    }

    ///  Actions
    public ElementActions element(){
        return new ElementActions(get());
    }

    public BrowserActions browser(){
        return new BrowserActions(get());
    }
    public FrameActions frame(){
        return new FrameActions(get());
    }
    public AlertActions alert(){
        return new AlertActions(get());
    }

    /// // Assertions

    public Validation validation(){
        return new Validation(get());
    }

    public Verification verification(){
        return new Verification(get());
    }
}
