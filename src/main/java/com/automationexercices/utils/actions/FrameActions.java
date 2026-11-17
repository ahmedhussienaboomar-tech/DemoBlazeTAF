package com.automationexercices.utils.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.automationexercices.utils.WaitManager;
import com.automationexercices.utils.logs.LogsManager;
/*
بعض صفحات الويب بتحتوي على iframe، يعني صفحة أو مستند متداخل داخل الصفحة الأساسية.
لو العنصر اللي عايز تتعامل معاه موجود داخل Frame، مش هتقدر تتعامل معاه مباشرة من الـ Default Content. لازم تعمل Switch للـ Frame أولًا.
*/
public class FrameActions {
    private final WebDriver driver;
    private final WaitManager waitManager;
    public FrameActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    /*
    الخطوات المنطقية:
1. تحديد الـ Frame.
   2. الانتقال إليه.
3. التعامل مع الـ Input الموجود داخله.
4. الرجوع إلى الصفحة الأساسية باستخدام switchToDefaultContent().
    */

    /*
    SWITCH TO FRAME BY INDEX
     */
    public void switchToFrameByIndex(int index) {
        waitManager.fluentWait().until(d -> {
            try {
                d.switchTo().frame(index);
                LogsManager.info("Switched to frame with index: " + index);
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        });

        /*
         *SWITCH TO FRAME BY NAME OR ID
         *@param nameOrId The name or ID of the frame to switch to.
         */
    }
    public void switchToFrameByNameOrId(String nameOrId) {
            waitManager.fluentWait().until(d -> {
                try {
                    d.switchTo().frame(nameOrId);
                    LogsManager.info("Switched to frame with name or ID: " + nameOrId);
                    return true;
                }
                catch (Exception e)
                {
                    return false;
                }
            });

    }
    /*
        * SWITCH TO FRAME BY WEBELEMENT
     */
    public void switchToFrameByWebElement(By frameElement) {
        waitManager.fluentWait().until(d -> {
            try {
                d.switchTo().frame(d.findElement(frameElement));
                LogsManager.info("Switched to frame with WebElement: " + frameElement);
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        });
    }

    /*
        * SWITCH BACK TO DEFAULT CONTENT
     */
    public void switchToDefaultContent() {
        waitManager.fluentWait().until(d -> {
            try {
                d.switchTo().defaultContent();
                LogsManager.info("Switched back to default content");
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }


}
