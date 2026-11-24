
package com.automationexercices.utils;

import com.automationexercices.utils.dataReader.PropertyReader;
import com.automationexercices.utils.logs.LogsManager;

import java.io.File;
import java.io.IOException;

import static org.aspectj.util.FileUtil.copyFile;

public class FileUtils {

    /*
     * user.dir: مسار الـ Working Directory اللي بدأ منه تشغيل Java.
     * File.separator: بيوفر فاصل المسار المناسب لنظام التشغيل،
     * زي \ على Windows أو / على Linux.
     * USER_DIR: بيجمع المسار الأساسي علشان تستخدمه باقي الدوال.
     */

    private static final String USER_DIR =
            PropertyReader.getProperty("user.dir") + File.separator;

    // File.separator -> is used to make the correct path separator.

    private FileUtils() {
        // Private constructor to prevent instantiation.
    }

    // Renaming
    public static void renameFile(String oldName, String newName) {
        try {
            var targetFile = new File(oldName);
            String targetDirectory = targetFile.getParentFile().getAbsolutePath();

            File newFile = new File(
                    targetDirectory + File.separator + newName
            );

            if (!targetFile.getPath().equals(newFile.getPath())) {

                copyFile(targetFile, newFile);

                org.apache.commons.io.FileUtils.deleteQuietly(targetFile);

                LogsManager.info(
                        "Target File Path: \"" + oldName
                                + "\", file was renamed to \"" + newName + "\"."
                );

            } else {

                LogsManager.info(
                        "Target File Path: \"" + oldName
                                + "\", already has the desired name \"" + newName + "\"."
                );
            }

        } catch (IOException e) {
            LogsManager.error(e.getMessage());
        }
    }

    // Create Directory
    public static void createDirectory(String path) {
        try {
            File file = new File(USER_DIR + path);

            if (!file.exists()) {
                file.mkdirs();

                LogsManager.info(
                        "Directory created: " + file.getAbsolutePath()
                );
            }

        } catch (Exception e) {
            LogsManager.error(
                    "Error creating directory: " + path + " - " + e.getMessage()
            );
        }
    }

    // Force delete
    public static void forceDelete(File file) {
        try {
            org.apache.commons.io.FileUtils.forceDelete(file);

            LogsManager.info(
                    "Directory deleted: " + file.getAbsolutePath()
            );

        } catch (IOException e) {
            LogsManager.error(
                    "Error deleting directory: "
                            + file.getAbsolutePath()
                            + " - "
                            + e.getMessage()
            );
        }
    }


    // Clean Directory
    public static void cleanDirectory(File file) {
        try {

            /*
             * If directory does not exist, create it.
             * If it already exists, clean its contents.
             */

            if (!file.exists()) {

                org.apache.commons.io.FileUtils.forceMkdir(file);

                LogsManager.info(
                        "Directory created: " + file.getAbsolutePath()
                );

            } else {

                org.apache.commons.io.FileUtils.cleanDirectory(file);

                LogsManager.info(
                        "Directory cleaned: " + file.getAbsolutePath()
                );
            }

        } catch (IOException e) {

            LogsManager.error(
                    "Error cleaning directory: "
                            + file.getAbsolutePath()
                            + " - "
                            + e.getMessage()
            );
        }
    }


    // Copy Directory
    public static void copyDirectory(File file, File resultsHistoryFolderFile) {
        try {

            // Use Apache Commons IO to copy the directory.
            org.apache.commons.io.FileUtils.copyDirectory(
                    file,
                    resultsHistoryFolderFile
            );

        } catch (Exception e) {

            LogsManager.error(
                    "Error copying directory: "
                            + file.getAbsolutePath()
                            + " to "
                            + resultsHistoryFolderFile.getAbsolutePath()
                            + " - "
                            + e.getMessage()
            );
        }
    }
}
