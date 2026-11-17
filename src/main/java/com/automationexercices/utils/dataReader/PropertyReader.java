
package com.automationexercices.utils.dataReader;

import org.apache.commons.io.FileUtils;
import com.automationexercices.utils.logs.LogsManager;

import java.io.File;
import java.util.Collection;
import java.util.Properties;

public class PropertyReader {

    // Properties كلاس جاهز في Java لتخزين الإعدادات كأزواج من Keys و Values.
    // بنحتفظ بالـ Properties المحملة في متغير static علشان نستخدمها في أي وقت
    // من غير ما نعيد تحميل الملفات مع كل استدعاء لـ getProperty().
    private static final Properties properties = loadProperties();

    public static Properties loadProperties() {

        // إنشاء Object من كلاس Properties لتخزين الإعدادات المقروءة من الملفات.
        Properties properties = new Properties();

        try {

            Collection<File> propertyFiles;

            propertyFiles = FileUtils.listFiles(
                    new File("src/main/resources"),
                    new String[]{"properties"},
                    true
            );

            /*
             * new File("src/main/resources"):
             * بيحدد المجلد اللي هنبحث جواه عن ملفات الإعدادات.
             *
             * new String[]{"properties"}:
             * بيخلي البحث يجيب الملفات اللي امتدادها .properties فقط.
             *
             * true:
             * معناه البحث داخل المجلدات الفرعية كمان.
             *
             * FileUtils.listFiles():
             * بترجع Collection فيها ملفات الـ properties اللي تم العثور عليها.
             */

            propertyFiles.forEach(file -> {

                try (var inputStream = FileUtils.openInputStream(file)) {

                    // openInputStream(file):
                    // بتفتح الملف للقراءة وبتقفل الـ InputStream تلقائيًا بعد الانتهاء.

                    // properties.load(inputStream):
                    // بتقرأ الـ Keys والـ Values من الملف وتخزنها داخل Properties Object.
                    // مثال: browserType=edge

                    properties.load(inputStream);

                    LogsManager.info(
                            "Properties loaded successfully from file: " + file.getName()
                    );

                } catch (Exception e) {

                    LogsManager.error(
                            "Error loading properties from file: "
                                    + file.getName() + " - " + e.getMessage()
                    );
                }
            });

            // إضافة Java System Properties إلى الـ Properties Object.
            // لو فيه Key موجود في الاثنين، قيمة الـ System Properties هي اللي هتكون المستخدمة.
            properties.putAll(System.getProperties());

        } catch (Exception e) {

            LogsManager.error("Error loading properties: " + e.getMessage());
        }

        return properties;
    }

    public static String getProperty(String key) {

        // قراءة قيمة الـ Key من ملفات الـ properties المحملة،
        // بدلًا من System.getProperty() التي تقرأ Java System Properties فقط.
        String value = properties.getProperty(key);

        // لو الـ Key مش موجود أو ملوش قيمة، نسجل رسالة في الـ Logs.
        if (value == null) {
            LogsManager.error("Property not found: " + key);
        }

        return value;
    }
}
