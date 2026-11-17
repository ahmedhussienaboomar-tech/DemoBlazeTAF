package com.automationexercices.utils.logs;
import com.automationexercices.utils.report.AllureConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogsManager {

    /*
     * إزاي الـ Logs بتتحفظ؟
     *
     * ملف log4j2.properties هو إعدادات Log4j2، وبيحدد شكل الرسائل ومكان إخراجها:
     * - Console Appender: يطبع الرسائل في الـ Console.
     * - File Appender: يكتب الرسائل في ملف.
     * - PatternLayout: يحدد شكل كل رسالة، مثل المستوى والتاريخ واسم الـ Thread والكلاس.
     */

    public static final String LOGS_PATH =
            AllureConstants.USER_DIR + "/test-outputs/logs/";
    // LOGS_PATH: المسار الذي يتوقع الكود وجود ملفات الـ Logs فيه.

    // TODO: static means the variable belongs to the class itself,
    // so we don't need to create an object using new LogsManager().

    private static Logger logger() {
        return LogManager.getLogger(
                Thread.currentThread().getStackTrace()[3].getClassName()
        );
        // السطر ده بيستخدم الـ Stack Trace علشان يحاول يجيب اسم الكلاس الذي استدعى LogsManager.
    }

    /*
     * Thread.currentThread().getStackTrace()[3].getClassName()
     *
     * باختصار، علشان ميجيش في الآخر يكون طابعلي اسم الكلاس LogsManager
     * لأنه هو اللي متعرف فيه الـ Logger بتاعي.
     *
     * فكده هخليه يطبعلي اسم الـ current class اللي فيها الـ test.
     *
     * This line retrieves the name of the class that called LogsManager.
     * It does this by accessing the current thread's stack trace.
     * This allows for dynamic logging based on which class is using LogsManager.
     */

    // Log messages at different levels:
    // INFO, DEBUG, ERROR, WARN, FATAL, TRACE

    public static void info(String... message) {
        logger().info(String.join(" ", message));
    }

    public static void debug(String... message) {
        logger().debug(String.join(" ", message));
    }

    public static void error(String... message) {
        logger().error(String.join(" ", message));
    }

    public static void warn(String... message) {
        logger().warn(String.join(" ", message));
    }

    public static void fatal(String... message) {
        logger().fatal(String.join(" ", message));
    }

    public static void trace(String... message) {
        logger().trace(String.join(" ", message));
    }

    /*
     * TRACE  : تفاصيل دقيقة جدًا.
     * DEBUG  : تفاصيل تساعد في اكتشاف المشاكل.
     * INFO   : أحداث طبيعية أثناء التشغيل.
     * WARN   : تحذير من مشكلة محتملة.
     * ERROR  : خطأ حصل أثناء التنفيذ.
     * FATAL  : مشكلة حرجة جدًا.
     */

    // String.join(" ", message):
    // بتعمل concatenate للـ messages اللي جاية من الـ user
    // وبتفصل بينها بمسافة.
    //
    // Example:
    // info("Error Message", "Something went wrong");
    //
    // Output:
    // Error Message Something went wrong
}