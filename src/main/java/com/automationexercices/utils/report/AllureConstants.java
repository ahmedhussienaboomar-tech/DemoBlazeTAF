package com.automationexercices.utils.report;

import com.automationexercices.utils.dataReader.PropertyReader;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AllureConstants {

    //Paths > final - static
    /// /* الكلاس دي حرفيا انا عاملها لمجلرد اني اضيف فيها الconstanats بتاعتي واخلي الباقي يورثوهم من هنا
    ///  وكنت اقدر اضيفهم برضو في ال allure Preoerties  وانده عليهم بال getPreoerty الطريقتين شغالين عادي
    public static final Path USER_DIR = Paths.get(PropertyReader.getProperty("user.dir"), File.separator);
    public static final Path USER_HOME = Paths.get(PropertyReader.getProperty("user.home"), File.separator);

    public static final Path RESULTS_FOLDER = Paths.get(
            String.valueOf(USER_DIR),
            "test-outputs",
            "allure-results"
    );
    public static final Path REPORT_PATH = Paths.get(String.valueOf(USER_DIR), "test-outputs", "reports", File.separator); //single report path
    public static final Path FULL_REPORT_PATH = Paths.get(String.valueOf(USER_DIR), "test-outputs", "full-report", File.separator);

    public static final Path HISTORY_FOLDER = Paths.get(FULL_REPORT_PATH.toString(), "history", File.separator); // after generating the report,
    public static final Path RESULTS_HISTORY_FOLDER = Paths.get(RESULTS_FOLDER.toString(), "history", File.separator); // copy generated history

    // دول هستخدمهم في ال renaming funcation اللي انا عاملها في ال FileUtils class
    public static final String INDEX_HTML = "index.html";           ///دا اسم ال single file اللي بيطلعلي
    public static final String REPORT_PREFIX = "AllureReport_";    /// دا الاسم الجديد اللي هسميهوله + timestamp علشان ال uniqueness
    public static final String REPORT_EXTENSION = ".html";

    //allure repository Github path
    // علشان استخدمه فحتة اني بعمل automate install to allure عشان مش كل حد معايا فالبروجيكت يروح يعملها بنفسه
    public static final String ALLURE_ZIP_BASE_URL = "https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/";

    //allure extraction path
    // فولدر عندي فال c
    public static final Path EXTRACTION_DIR = Paths.get(String.valueOf(USER_HOME), ".m2", "repository", "allure", File.separator);




}
