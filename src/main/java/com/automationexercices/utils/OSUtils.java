package com.automationexercices.utils;

import com.automationexercices.utils.dataReader.PropertyReader;

public class OSUtils {
    public enum OS {
        WINDOWS,
        MAC,
        LINUX,
        OTHER
    }

    public static OS getCurrentOS() {
        /*
        *الخطوات ببساطة:
1. يقرأ اسم نظام التشغيل.
2. يحوّل الاسم إلى حروف صغيرة.
3. يقارنه بالكلمات المعروفة.
4. يرجع قيمة من OS.
        */
        String osName = PropertyReader.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return OS.WINDOWS;
        } else if (osName.contains("mac")) {
            return OS.MAC;
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            return OS.LINUX;
        } else {
            return OS.OTHER;
        }
        /// خد بالك: os.name هنا خاصية Java System Property، ومش محتاجة تتحط في ملف properties طالما Java نفسها بتوفرها.
    }

}
