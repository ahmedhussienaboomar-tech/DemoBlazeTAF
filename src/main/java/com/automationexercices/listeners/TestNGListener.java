
package com.automationexercices.listeners;

import com.automationexercices.drivers.UITest;
import com.automationexercices.drivers.WebDriverProvider;
import com.automationexercices.media.ScreenshotsManager;
import com.automationexercices.utils.FileUtils;
import com.automationexercices.utils.logs.LogsManager;
import com.automationexercices.utils.report.AllureAttachementManager;
import com.automationexercices.utils.report.AllureConstants;
import com.automationexercices.utils.report.AllureEnvironmentManager;
import com.automationexercices.utils.report.AllureReportGenerator;
import com.automationexercices.validations.Validation;
import com.automationexercices.utils.dataReader.PropertyReader;

import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;

public class TestNGListener implements IInvokedMethodListener,
        ITestListener, IExecutionListener {

    /*
     * أ) ليه الكلاس بيعمل implements لثلاثة Interfaces؟
     *
     * لأن كل Interface بيديك Hooks مختلفة في دورة حياة TestNG:
     *
     * - IInvokedMethodListener:
     *   قبل وبعد استدعاء الـ Methods، ومن ضمنها Test Methods.
     *
     * - ITestListener:
     *   أحداث نجاح الاختبار أو فشله أو تخطيه.
     *
     * - IExecutionListener:
     *   بداية ونهاية تنفيذ مجموعة الاختبارات.
     */

    @Override
    public void onExecutionStart() {

        LogsManager.info("Execution started");

        cleanTestOutputDirectories();

        LogsManager.info("Test output directories cleaned");

        createTestOutputDirectories();
        // Create directories for screenshots, logs, and Allure results.

        LogsManager.info("Test output directories created");

        PropertyReader.loadProperties();

        LogsManager.info("Properties loaded");

        AllureEnvironmentManager.setEnvironmentVariables();
        // Set Allure environment variables.

        LogsManager.info("Allure environment variables set");
    }

    @Override
    public void onExecutionFinish() {

        AllureReportGenerator.generateReport(false);

        LogsManager.info("Allure report generated");

        AllureReportGenerator.copyHistoryFolder();

        LogsManager.info("Allure history folder copied");

        AllureReportGenerator.generateReport(true);

        LogsManager.info("Allure single file report generated");

        AllureReportGenerator.openReport(
                AllureReportGenerator.renameReportFile()
        );

        LogsManager.info(
                "Test execution finished. Allure report opened in default browser"
        );

        /*
         * الفكرة هنا إنك بتولد تقرير Allure، وتحاول تحتفظ ببيانات الـ History،
         * وبعدها تولد نسخة HTML مستقلة وتفتحها في المتصفح الافتراضي.
         *
         * هنشرح AllureReportGenerator بالتفصيل لما نوصل لجزء Reporting؛
         * لأن فهمه محتاج نفهم ملفات النتائج، والـ History،
         * والفرق بين تقرير Allure العادي والنسخة المستقلة.
         */
    }

    @Override
    public void beforeInvocation(
            IInvokedMethod method,
            ITestResult testResult
    ) {

        /*
         * الـ Listener هنا بيتأكد إن الميثود اللي هتتنفذ هي Test Method،
         * مش Configuration Method زي @BeforeClass أو @AfterMethod.
         */

        if (method.isTestMethod()) {
            if (testResult.getInstance() instanceof UITest) {

                // TODO: uncomment this line if you want to start recording
                // the screen for each test method.

                // ScreenRecordManager.startRecording();
            }


            LogsManager.info(
                    "Test Case " + testResult.getName() + " started"
            );
        }
    }

    @Override
    public void afterInvocation(
            IInvokedMethod method,
            ITestResult testResult
    ) {

        WebDriver driver = null;

        if (method.isTestMethod()) {

            // TODO: uncomment this line if you want to stop recording
            // the screen for each test method.

            // ScreenRecordManager.stopRecording(testResult.getName());

            Validation.assertAll(testResult);
            // Check for soft assertions after each test method.

            /*
             * الفكرة إن Soft Assertion تسمح للاختبار يكمل بعد Assertion فاشلة،
             * وبعدها يتم الإبلاغ عن الإخفاقات المجمعة عند assertAll().
             */
            if (testResult.getInstance() instanceof UITest) {
                if (testResult.getInstance() instanceof WebDriverProvider provider) {

                    driver = provider.getDriver();
                    // Initialize the driver from WebDriverProvider.
                }

                /*
                 * ده استخدام للـ Interface اللي شرحناه في الجزء السابق.
                 *
                 * الـ Listener مش بيعمل new GUIDriver() بنفسه؛
                 * هو بيطلب الـ Driver من الـ Test Class
                 * لو كانت بتوفر WebDriverProvider.
                 */

                switch (testResult.getStatus()) {

                    case ITestResult.SUCCESS ->
                            ScreenshotsManager.takeFullPageScreenshot(
                                    driver,
                                    testResult.getName()
                            );

                    case ITestResult.FAILURE ->
                            ScreenshotsManager.takeFullPageScreenshot(
                                    driver,
                                    testResult.getName()
                            );

                    case ITestResult.SKIP ->
                            ScreenshotsManager.takeFullPageScreenshot(
                                    driver,
                                    testResult.getName()
                            );

                    /*
                     * أما في حالة SKIP، لازم تنتبه إن الـ Driver ممكن ما يكونش
                     * اتنشأ أصلًا؛ فتمرير null إلى ScreenshotsManager
                     * ممكن يؤدي إلى NullPointerException.
                     *
                     * لأن الكود الحالي مش بيتحقق من صلاحية الـ Driver
                     * قبل استخدامه.
                     */
                }
                AllureAttachementManager.attachRecords(testResult.getName());
                // Attach screen recording to Allure report.

            }

            Validation.assertAll(testResult);
            // Check for soft assertions after each test method.
            AllureAttachementManager.attachLogs();
            // Attach logs to Allure report.


            /*
             * لو Validation.assertAll() رمت Exception بسبب فشل Assertion،
             * ممكن التنفيذ يخرج من afterInvocation() قبل التقاط الصورة
             * وإرفاق الأدلة.
             *
             * وده عكس الهدف اللي بنحاول نحققه؛ لأن الاختبار الفاشل
             * هو غالبًا أكثر اختبار محتاج Evidence.
             *
             * الحل هو تنظيم الكود بحيث تتنفذ عملية مراجعة الـ Assertions،
             * وفي الوقت نفسه تضمن تنفيذ خطوات جمع الأدلة في finally،
             * مع الحفاظ على نتيجة الاختبار الأصلية وعدم إخفاء سبب الفشل.
             */
        }
    }

    // TestNG Listener methods for test case status logging.
    // الميثودز دي بتسجل نتيجة كل اختبار في الـ Logs.

    @Override
    public void onTestSuccess(ITestResult result) {

        LogsManager.info(
                "Test Case " + result.getName() + " passed"
        );
    }

    @Override
    public void onTestFailure(ITestResult result) {

        LogsManager.info(
                "Test Case " + result.getName() + " failed"
        );
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        LogsManager.info(
                "Test Case " + result.getName() + " skipped"
        );
    }

    // Clean directory logs - screenshots - Allure reports - records.

    public void cleanTestOutputDirectories() {

        FileUtils.cleanDirectory(
                AllureConstants.RESULTS_FOLDER.toFile()
        );

        FileUtils.cleanDirectory(
                new File(ScreenshotsManager.SCREENSHOTS_PATH)
        );

        /*
         * Log4j2 manages the log file initialization and cleanup.
         *
         * We don't delete test.log manually because Log4j2 may still
         * be using the file through its File Appender.
         *
         * Deleting an active log file can cause a file-locking issue,
         * especially on Windows.
         */

        // TODO: uncomment this line if you want to clean
        // the screen records directory after each execution.

        // FileUtils.cleanDirectory(
        //         new File(ScreenRecordManager.SCREEN_RECORDS_PATH)
        // );
    }

    private void createTestOutputDirectories() {

        FileUtils.createDirectory(
                ScreenshotsManager.SCREENSHOTS_PATH
        );

        // TODO: uncomment this line if you want to create
        // the screen records directory before each execution.

        // FileUtils.createDirectory(
        //         ScreenRecordManager.SCREEN_RECORDS_PATH
        // );

        /*
         * دول كده كده هيتعملهم create automatically
         *
         * FileUtils.createDirectory(new File(LogsManager.LOGS_PATH));
         * FileUtils.createDirectory(AllureConstants.RESULTS_FOLDER.toFile());
         */
    }
}
