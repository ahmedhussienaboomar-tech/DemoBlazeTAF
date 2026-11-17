package com.automationexercices.utils;

import com.automationexercices.utils.dataReader.PropertyReader;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.ArrayList;
import org.openqa.selenium.NoSuchElementException;


public class WaitManager {
    private WebDriver driver;

    public WaitManager(WebDriver driver) {
        this.driver = driver;
    }
    public FluentWait<WebDriver> fluentWait() {
        return new FluentWait<>(driver)
                .withTimeout(java.time.Duration.ofSeconds(
                        Long.parseLong(PropertyReader.getProperty("DEFAULT_WAIT_TIME")
                        ))) //وبتحولها من String إلى long باستخدام Long.parseLong().
                .pollingEvery(java.time.Duration.ofMillis(100))
                .ignoreAll(getExceptions());
    }
    /*
    . إيه هي FluentWait؟
     دي Class من Selenium بتسمح لك تحدد:
- أقصى مدة انتظار.
     - الفترة بين محاولات التحقق.
- أنواع Exceptions اللي تتجاهلها أثناء الانتظار.
الميثود عندك بترجع FluentWait<WebDriver>، وبالتالي تقدر تستخدمها
      */

    private ArrayList <Class<? extends Exception>> getExceptions() {
        ArrayList<Class<? extends Exception>> exceptions = new ArrayList<>();
        exceptions.add(NoSuchElementException.class);
        exceptions.add(StaleElementReferenceException.class);
        exceptions.add(ElementNotInteractableException.class);
        exceptions.add(ElementClickInterceptedException.class);

        return exceptions;
    }
    /*هـ. ليه عندك ignoreAll(getExceptions())؟
       في بعض الأحيان، العنصر ممكن يكون لسه مش جاهز، وSelenium ترمي Exception أثناء محاولة التفاعل معه.
      إنت بتجهز قائمة بالـ Exceptions اللي عايز تتجاهلها مؤقتًا أثناء الانتظار:
*/
    /*
    NoSuchElementException	العنصر مش موجود وقت البحث عنه.
    StaleElementReferenceException	المرجع للعنصر بقى قديمًا بسبب تغيير الـ DOM أو استبدال العنصر.
    ElementNotInteractableException	العنصر موجود لكن مش قابل للتفاعل بالشكل المطلوب.
    ElementClickInterceptedException	عنصر تاني أو Overlay منع وصول الـ Click للعنصر المستهدف.
     */
/*وفيه فرق مهم: FluentWait عندك بتتجاهل الاستثناءات دي أثناء تنفيذ الشرط،
 لكن لو الشرط فضل يرجع false لحد انتهاء الوقت، عملية الانتظار نفسها هتفشل.*/
}
