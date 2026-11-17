package com.automationexercices.utils;

public class TimeManager {

    //used in screeshots - logs - reports
    public static String getTimestamp() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new java.util.Date());
    /*
    *ملاحظة تقنية: استخدام hh بيشير إلى ساعة بنظام 12 ساعة، بينما HH بيستخدم نظام 24 ساعة.
 ولو هتستخدم Timestamp كمفتاح فريد لملفات كتير
 * ، فالدقة بالثواني وحدها مش كافية لو الاختبارات بتنتج ملفات متعددة في الثانية نفسها.
    */
    }

    //to ensure uniqueness
    //unique timeStamp for each data
    public static String getSimpleTimestamp(){
        return Long.toString(System.currentTimeMillis());
    }
    /*
    دي بترجع الوقت الحالي بالـ milliseconds، وغالبًا هتديك قيمة رقمية مختلفة عند استدعائها في أوقات مختلفة.

    */

}
