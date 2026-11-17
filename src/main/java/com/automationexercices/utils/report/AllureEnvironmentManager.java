
package com.automationexercices.utils.report;

import com.automationexercices.utils.dataReader.PropertyReader;
import com.automationexercices.utils.logs.LogsManager;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

public class AllureEnvironmentManager {

    public static void setEnvironmentVariables() {

        Properties environment = new Properties();

        environment.setProperty(
                "OS",
                System.getProperty("os.name")
        );

        environment.setProperty(
                "Java version",
                System.getProperty("java.runtime.version")
        );

        environment.setProperty(
                "Browser",
                PropertyReader.getProperty("browserType")
        );

        environment.setProperty(
                "Execution Type",
                PropertyReader.getProperty("executionType")
        );

        environment.setProperty(
                "URL",
                PropertyReader.getProperty("baseUrlWeb")
        );

        File resultsDirectory =
                AllureConstants.RESULTS_FOLDER.toFile();

        if (!resultsDirectory.exists() && !resultsDirectory.mkdirs()) {
            throw new IllegalStateException(
                    "Unable to create Allure results directory: "
                            + resultsDirectory.getAbsolutePath()
            );
        }

        File environmentFile = new File(
                resultsDirectory,
                "environment.properties"
        );

        try (FileOutputStream outputStream =
                     new FileOutputStream(environmentFile)) {

            environment.store(
                    outputStream,
                    "Allure Environment Variables"
            );

            LogsManager.info(
                    "Allure environment variables saved to: "
                            + environmentFile.getAbsolutePath()
            );

        } catch (Exception e) {
            LogsManager.error(
                    "Error saving Allure environment variables: "
                            + e.getMessage()
            );

            throw new IllegalStateException(
                    "Failed to save Allure environment variables",
                    e
            );
        }

        AllureBinaryManager.downloadAndExtract();
    }
}
