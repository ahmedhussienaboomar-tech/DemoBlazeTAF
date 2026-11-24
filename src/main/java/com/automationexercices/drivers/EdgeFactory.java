
package com.automationexercices.drivers;

import com.automationexercices.utils.dataReader.PropertyReader;
import com.automationexercices.utils.logs.LogsManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;

public class EdgeFactory extends AbstractDriver {

    private EdgeOptions getOptions() {

        EdgeOptions options = new EdgeOptions();

        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");

        String executionType = PropertyReader.getProperty("executionType");

        if (executionType.equalsIgnoreCase("LocalHeadless") ||
                executionType.equalsIgnoreCase("Remote")) {

            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");

        } else {
            options.addArguments("--start-maximized");
        }

        options.setAcceptInsecureCerts(true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        return options;
    }

    @Override
    public WebDriver createDriver() {

        String executionType = PropertyReader.getProperty("executionType");

        if (executionType.equalsIgnoreCase("Local") ||
                executionType.equalsIgnoreCase("LocalHeadless")) {

            return new EdgeDriver(getOptions());

        } else if (executionType.equalsIgnoreCase("Remote")) {

            try {
                return new RemoteWebDriver(
                        new URI("http://" + remoteHost + ":" +
                                remotePort + "/wd/hub").toURL(),
                        getOptions()
                );

            } catch (Exception e) {
                LogsManager.error(
                        "Error occurred while creating RemoteWebDriver for Edge: "
                                + e.getMessage()
                );

                throw new RuntimeException(
                        "Error occurred while creating RemoteWebDriver for Edge",
                        e
                );
            }

        } else {
            throw new IllegalArgumentException(
                    "Invalid execution type for Edge: " + executionType
            );
        }
    }
}
