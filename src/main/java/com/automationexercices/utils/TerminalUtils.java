package com.automationexercices.utils;

import com.automationexercices.utils.logs.LogsManager;

import java.io.IOException;
/// الكلاس ده بيشغّل أوامر نظام التشغيل من Java.
/// utility class to execute terminal commands, especially for Allure report generation and opening.
public class TerminalUtils {
    public static void executeTerminalCommand(String... commandParts) {
        //العلامة ... معناها إنك تقدر تمرر عددًا متغيرًا من أجزاء الأمر.
        try {
            Process process = Runtime.getRuntime().exec(commandParts);
            // allure generate -o reports --single-file --clean
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                LogsManager.error("Command failed with exit code: " + exitCode);
            }
        }
        catch (IOException | InterruptedException e) {
            LogsManager.error("Failed to execute terminal command: " + String.join(" ", commandParts), e.getMessage());
        }
    }
    /*
    - exec() بتشغّل الأمر.
- waitFor() بتستنى لحد ما العملية تخلص.
- exitCode بيوضح إذا كانت العملية انتهت بنجاح أو فشل؛ غالبًا 0 معناها نجاح، وأي قيمة أخرى تشير إلى خطأ.
الكلاس ده بيُستخدم في مشروعك مع AllureReportGenerator لتشغيل أوامر Allure.
    */
}
/*
* نقطة محتاجة تحسين
الكود الحالي بيسجل إن الأمر فشل لو الـ Exit Code مش صفر، لكنه مش بيقرأ stdout وstderr من العملية. وبالتالي ممكن تعرف إن الأمر فشل، لكن ما تعرفش سبب الفشل بالتفصيل.
كمان لو حصل InterruptedException، الأفضل تعيد ضبط حالة المقاطعة:
*/