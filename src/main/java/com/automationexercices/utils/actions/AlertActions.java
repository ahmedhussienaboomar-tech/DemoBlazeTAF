package com.automationexercices.utils.actions;

import org.openqa.selenium.WebDriver;
import com.automationexercices.utils.WaitManager;
import com.automationexercices.utils.logs.LogsManager;
/*
الـ Alert هي نافذة تنبيه بيعرضها المتصفح عن طريق JavaScript، وليست عنصر HTML عاديًا تقدر تجيبه باستخدام By.
*/

public class AlertActions {
    private final WebDriver driver;
    private final WaitManager waitManager;

    public AlertActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    /*
    Accepts the alert if present. This method waits for the alert to be present and then accepts it.
     */

    public void acceptAlert()
    //الضغط على OK أو Accept.
    {waitManager.fluentWait()
                .until(d -> {
                    try {
                        d.switchTo().alert().accept();
                        return true;
                    } catch (Exception e) {
                        LogsManager.error("Failed to accept alert:" + e.getMessage());
                        return false;
                    }
                });
    }

    /*
    Dismisses the alert if present. This method waits for the alert to be present and then dismisses it.
     */

    public void dismissAlert()
    //الضغط على Cancel أو Dismiss لو متاح.
    {waitManager.fluentWait()
                .until(d -> {
                    try {
                        d.switchTo().alert().dismiss();
                        return true;
                    } catch (Exception e) {
                        LogsManager.error("Failed to dismiss alert:" + e.getMessage());
                        return false;
                    }
                });
    }

    /*
    Retrieves the text from the alert if present. This method waits for the alert to be present and then returns its text.
     */
    public String getAlertText()
    //قراءة النص الموجود داخل الـ Alert.
    {return waitManager.fluentWait()
                .until(d -> {
                    try {
                        String alertText = d.switchTo().alert().getText();
                        return !alertText.isEmpty() ? alertText : null;
                    } catch (Exception e) {
                        LogsManager.error("Failed to get alert text:" + e.getMessage());
                        return null;
                    }
                });
    }
    /*
    Sets the text in the alert input field. This method waits for the alert to be present and then sends the specified text to it.
    */
    public void sendKeysToAlert(String text)
    //إرسال نص إلى الـ Alert لو كان من نوع Prompt.
    {waitManager.fluentWait()
                .until(d -> {
                    try {
                        d.switchTo().alert().sendKeys(text);
                        LogsManager.info("Sent keys to alert: " + text);
                        return true;
                    } catch (Exception e) {
                        LogsManager.error("Failed to send keys to alert:" + e.getMessage());
                        return false;
                    }
                });
    }
    /*
    إزاي الـ Wait مستخدمة هنا؟
    لو الـ Alert لسه مش موجود، المحاولة هتفشل وتُعاد لحد ما يظهر أو تنتهي المهلة.
لكن عندك ملاحظة مهمة: إنت بتسجل رسالة Error في كل محاولة فاشلة،
 رغم إن عدم ظهور الـ Alert مؤقتًا ممكن يكون طبيعيًا أثناء الانتظار. ده ممكن يملأ الـ Logs برسائل غير ضرورية.
كمان Selenium بتوفر ExpectedConditions.alertIsPresent()، وده غالبًا أوضح من كتابة الانتظار يدويًا في الحالة دي.
    */

}
