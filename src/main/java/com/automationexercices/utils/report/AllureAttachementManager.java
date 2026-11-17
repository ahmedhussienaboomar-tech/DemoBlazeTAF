package com.automationexercices.utils.report;

import com.automationexercices.utils.logs.LogsManager;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//ScreenshotsManager مسؤول عن إنشاء الصورة وحفظها.
//AllureAttachementManager مسؤول عن إرفاقها بالتقرير.

public class AllureAttachementManager {
    // attach a screenshot to the allure report
    public static void attachScreenshot(String name , String path) {
        /*
        تعالى نمشي فيها بالترتيب:
1. بتستقبل اسم الصورة ومسارها.
2. بتحوّل المسار إلى Path.
3. بتتأكد إن الملف موجود.
4. لو موجود، بتستخدم Allure.attachment() علشان تضيفه للتقرير.
5. لو مش موجود، بتسجل رسالة خطأ.
نقطة مهمة: الدالة دي مش بتلتقط Screenshot بنفسها؛ هي بتاخد صورة موجودة بالفعل وتضيفها إلى Allure.
        */
        try {
            Path screenshotPath = Paths.get(path);
            if (Files.exists(screenshotPath)) {
                Allure.attachment(name, Files.newInputStream(screenshotPath));
            } else {
                LogsManager.error("Screenshot file not found: " + path);
            }
        } catch (Exception e) {
            LogsManager.error("Failed to attach screenshot: " + e.getMessage());
        }
    }

    // attach log file to the allure report
    public static void attachLogs() {
        try{
            LogManager.shutdown();
            File logFile = new File(LogsManager.LOGS_PATH + "logs.log");
            if (logFile.exists()) {
                Allure.attachment("Automation Logs", Files.newInputStream(logFile.toPath()));
            }
        } catch (Exception e) {
            LogsManager.error("Failed to attach logs: " + e.getMessage());
        }
    }

    //attach records
    public static void attachRecords(String testMethodName) {
        try {
            File recordFile = new File(AllureConstants.USER_DIR + "test-outputs/recordings/record.mp4");
            if (recordFile.exists()) {
                Allure.attachment("Automation Records", Files.newInputStream(recordFile.toPath()));
            }
        } catch (Exception e) {
            LogsManager.error("Failed to attach records: " + e.getMessage());
        }
    }



}
