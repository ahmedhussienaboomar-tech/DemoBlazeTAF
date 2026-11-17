package com.automationexercices.drivers;

import com.automationexercices.utils.dataReader.PropertyReader;
import com.automationexercices.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.URI;

public class ChromeFactory extends AbstractDriver {

    private ChromeOptions getOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); // السماح بالوصول إلى الموارد من أي أصل أثناء جلسة الاختبار.
        options.addArguments("--disable-notifications"); // تعطيل الإشعارات أثناء جلسة الاختبار.
        options.addArguments("--disable-popup-blocking");  // تعطيل حظر النوافذ المنبثقة أثناء جلسة الاختبار.
        options.addArguments("--start-maximized");
        options.addArguments("--disable-infobars"); // تعطيل شريط المعلومات الذي يظهر في أعلى نافذة المتصفح أثناء جلسة الاختبار.
        options.addArguments("--disable-extensions"); // تعطيل الإضافات المثبتة في المتصفح أثناء جلسة الاختبار.
        options.addArguments("--disable-gpu"); // تعطيل استخدام  وحدة معالجة الرسومات (GPU) أثناء جلسة الاختبار.
        options.addArguments("--start-maximized"); // فتح المتصفح في وضع ملء الشاشة عند بدء جلسة الاختبار.
        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless") ||
                PropertyReader.getProperty("executionType").equalsIgnoreCase("Remote")) {
            options.addArguments("--headless");
        }
        options.setCapability(CapabilityType.ENABLE_DOWNLOADS,true);

        options.setAcceptInsecureCerts(true); // السماح بقبول الشهادات غير الموثوقة أثناء جلسة الاختبار.
        options.setPageLoadStrategy(PageLoadStrategy.EAGER); // اعتبار التنقل جاهزًا بعد وصول حالة المستند إلى interactive، دون انتظار اكتمال تحميل كل الموارد.
        return options;
    }

    @Override
    public WebDriver createDriver() {  //  بتقرر إزاي وأين يتم إنشاء الـ WebDriver.
        // Create and return a Chrome WebDriver instance
        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Local") ||
                PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless"))
        {
            return new ChromeDriver(getOptions());
        }

        else if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Remote")) {
            try {
                return new RemoteWebDriver(
                        new URI("http://" +remoteHost+ " : " +remotePort+"/wd/hub").toURL(), getOptions());
            } catch (Exception e) {
                LogsManager.error("Error occurred while creating RemoteWebDriver for Chrome: " + e.getMessage());
                throw new RuntimeException("Error occurred while creating RemoteWebDriver for Chrome: " + e.getMessage());
            }
        }

        else {
            LogsManager.error("Invalid execution type for Chrome: " + PropertyReader.getProperty("executionType"));
            throw new IllegalArgumentException("Invalid execution type for Chrome: " + PropertyReader.getProperty("executionType"));
        }

    }
}
