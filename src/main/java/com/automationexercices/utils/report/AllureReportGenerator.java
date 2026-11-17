package com.automationexercices.utils.report;

import com.automationexercices.utils.FileUtils;
import com.automationexercices.utils.OSUtils;
import com.automationexercices.utils.TerminalUtils;
import com.automationexercices.utils.TimeManager;
import com.automationexercices.utils.logs.LogsManager;
import com.automationexercices.utils.report.AllureBinaryManager;
import com.automationexercices.utils.report.AllureConstants;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.automationexercices.utils.dataReader.PropertyReader.getProperty;
import static com.automationexercices.utils.report.AllureConstants.HISTORY_FOLDER;
import static com.automationexercices.utils.report.AllureConstants.RESULTS_HISTORY_FOLDER;

public class AllureReportGenerator {

    // Generate allure report using command line
    public static void generateReport(boolean isSingleFile)
    // المتغير isSingleFile بيحدد نوع التقرير المطلوب:
    // - false: تقرير كامل في مجلد.
    // - true: تقرير HTML منفرد باستخدام الخيار --single-file.
    {
        Path outputFolder = isSingleFile
                ? AllureConstants.REPORT_PATH
                : AllureConstants.FULL_REPORT_PATH;

        // Choose the output folder based on the isSingleFile flag

        // Allure generate command -o reports --single-file --clean

        // List.of() بترجع List غير قابلة للتعديل.
        // لذلك بنستخدم ArrayList علشان نقدر نضيف --single-file عند الحاجة.
        List<String> command = new ArrayList<>(List.of(
                AllureBinaryManager.getExecutable().toString(),
                "generate",
                AllureConstants.RESULTS_FOLDER.toString(),
                "-o",
                outputFolder.toString(),
                "--clean"
        ));

        if (isSingleFile) {
            command.add("--single-file");
        }

        // قبل توليد التقرير الكامل، بننسخ الـ History السابق
        // إلى allure-results علشان Allure يستخدمه في التقرير الجديد.
        // التقرير المنفرد مش بنستخدمه كمصدر للـ History.
        if (!isSingleFile) {
            copyHistoryFolder();
        }

        TerminalUtils.executeTerminalCommand(command.toArray(new String[0]));

        // وبعدين بيستخدم TerminalUtils.executeTerminalCommand()
        // لتشغيل الأمر.
    }

    // Open allure report in default browser
    public static void openReport(String reportFileName)

    /*
     * الدالة بتقرأ إعداد OpenAllureReportAfterExecution،
     * وبعدها بتحاول تفتح التقرير حسب نظام التشغيل:
     * - Windows: باستخدام start.
     * - macOS: باستخدام open.
     * - Linux: باستخدام xdg-open.
     *
     * وده يخلي التقرير يفتح تلقائيًا بعد انتهاء التنفيذ
     * بدل ما تبحث عنه يدويًا.
     */
    {
        if (!getProperty("OpenAllureReportAfterExecution")
                .toLowerCase()
                .contains("local")) {
            return;
        }

        Path reportPath = AllureConstants.REPORT_PATH.resolve(reportFileName);

        switch (OSUtils.getCurrentOS()) {
            case WINDOWS ->
                    TerminalUtils.executeTerminalCommand(
                            "cmd", "/c", "start", reportPath.toString()
                    );

            case MAC ->
                    TerminalUtils.executeTerminalCommand(
                            "open", reportPath.toString()
                    );

            case LINUX ->
                    TerminalUtils.executeTerminalCommand(
                            "xdg-open", reportPath.toString()
                    );

            default -> LogsManager.warn(
                    "Unsupported OS for opening the report automatically. "
                            + "Please open the report manually at: "
                            + reportPath
            );
        }
    }

    // Copy history folder to result folder
    public static void copyHistoryFolder()
    // الهدف إنك تحتفظ ببيانات الـ History من التشغيل السابق،
    // علشان تستفيد منها في عرض اتجاهات نتائج الاختبارات بين مرات التشغيل.
    {
        try {
            FileUtils.copyDirectory(
                    HISTORY_FOLDER.toFile(),
                    RESULTS_HISTORY_FOLDER.toFile()
            );
        } catch (Exception e) {
            LogsManager.error("Error copying history file", e.getMessage());
        }
    }

    // Rename the report file to a unique name based on timestamp
    public static String renameReportFile()
    // بتنشئ اسمًا جديدًا باستخدام Timestamp.
    {
        String newReportFileName = AllureConstants.REPORT_PREFIX
                + TimeManager.getTimestamp()
                + AllureConstants.REPORT_EXTENSION;

        FileUtils.renameFile(
                AllureConstants.REPORT_PATH.resolve(AllureConstants.INDEX_HTML)
                        .toString(),
                newReportFileName
        );

        return newReportFileName;
    }
}
