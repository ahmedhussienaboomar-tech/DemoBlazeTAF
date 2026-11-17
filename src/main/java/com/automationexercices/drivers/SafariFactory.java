package com.automationexercices.drivers;

import com.automationexercices.utils.dataReader.PropertyReader;
import com.automationexercices.utils.logs.LogsManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.URI;


/// addArguments  not supported in SafariOptions, so we will not use them here. Safari has limited options compared to other browsers.

public class SafariFactory extends AbstractDriver {

    private SafariOptions getOptions() {
        SafariOptions options = new SafariOptions();
        options.setAcceptInsecureCerts(true);

        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return options;
    }
    @Override
    public WebDriver createDriver() {
        // Create and return a Safari WebDriver instance
        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Local") ||
                PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless"))
        {
            return new SafariDriver(getOptions());
        }

        else if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Remote")) {
            try {
                return new RemoteWebDriver(
                        new URI("http://" + remoteHost + " : " + remotePort +"/wd/hub").toURL(), getOptions()
                );
            } catch (Exception e) {
                LogsManager.error("Error occurred while creating RemoteWebDriver for Safari: " + e.getMessage());
                throw new RuntimeException("Error occurred while creating RemoteWebDriver for Safari: " + e.getMessage());
            }

        }

        else {
            LogsManager.error("Invalid execution type for Safari: " + PropertyReader.getProperty("executionType"));
            throw new IllegalArgumentException("Invalid execution type for Safari: " + PropertyReader.getProperty("executionType"));
        }

    }

}

