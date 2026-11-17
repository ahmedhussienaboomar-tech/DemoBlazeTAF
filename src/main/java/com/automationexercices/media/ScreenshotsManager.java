package com.automationexercices.media;

import com.automationexercices.utils.TimeManager;
import com.automationexercices.utils.logs.LogsManager;
import com.automationexercices.utils.report.AllureAttachementManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

//ScreenshotsManager مسؤول عن إنشاء الصورة وحفظها.
//AllureAttachementManager مسؤول عن إرفاقها بالتقرير.

public class ScreenshotsManager {

    public static final String SCREENSHOTS_PATH = "test-outputs/screenshots/";

    // full page screenshot

    public static void takeFullPageScreenshot(WebDriver driver, String screenshotName) {
        // Implementation for taking a full page screenshot

        // Check if WebDriver is null before taking the screenshot
        if (driver == null) {
            LogsManager.error("Cannot take screenshot: WebDriver is null.");
            return;
        }

        try {
            File screenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            File screenshotFile = new File(SCREENSHOTS_PATH + screenshotName + "-"
                    + TimeManager.getTimestamp() + ".png");

            // TimeManager.getTimestamp() to ensure unique screenshot names
            // هيبقى الاسم كل مره متضاف عليه التوقيت

            FileUtils.copyFile(screenshotSrc, screenshotFile);
            // ينسخ الصورة إلى مجلد الـ Screenshots.

            LogsManager.info("Full page screenshot taken: " + screenshotFile.getAbsolutePath());

            // Attach the screenshot to the Allure report after saving it
            // بعد ما نحفظ الصورة، بنضيفها إلى تقرير Allure باستخدام اسم الملف والمسار الكامل.
            AllureAttachementManager.attachScreenshot(
                    screenshotFile.getName(),
                    screenshotFile.getAbsolutePath()
            );

        } catch (Exception e) {
            LogsManager.error("Error taking or attaching full page screenshot: " + e.getMessage());

        /*
         لو مجلد Screenshots مش موجود، copyFile() ممكن تفشل.
         إنت بتنشئ المجلد من الـ Listener، لكن ده بيعتمد على إن الـ Listener
         اتنفذ بنجاح قبل التقاط الصورة.

         كمان لو حصل خطأ أثناء إرفاق الصورة بـ Allure، هيتسجل في الـ Logs.
        */
        }
    }


    // element screenshot
    public static void takeElementScreenshot(WebDriver driver, By elementLocator) {
        // Implementation for taking an element screenshot
        try {

            String ariaName = driver.findElement(elementLocator).getAccessibleName();
            // Get the accessible name of the element for naming the screenshot file اسمه الفعلي اللي ظاهر فال page
            File screenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            File screenshotFile = new File(SCREENSHOTS_PATH + ariaName + "-" + TimeManager.getTimestamp() + ".png");
            //TimeManager.getTimestamp() to ensure unique screenshot names

            FileUtils.copyFile(screenshotSrc, screenshotFile);

            //ToDO: attach the screenshot to the test report using Allure or ExtentReports if needed

            AllureAttachementManager.attachScreenshot(
                    screenshotFile.getName(),
                    screenshotFile.getAbsolutePath());
            //وده بيربط ScreenshotsManager بـ AllureAttachementManager؛
            // الأولى بتجهّز الصورة وتحفظها، والثانية بتضيفها للتقرير.

            LogsManager.info("Element screenshot taken: " + screenshotFile.getAbsolutePath());
        }
        catch (IOException e) {
            LogsManager.error("Error taking element screenshot: " + e.getMessage());
        }
    }
}
