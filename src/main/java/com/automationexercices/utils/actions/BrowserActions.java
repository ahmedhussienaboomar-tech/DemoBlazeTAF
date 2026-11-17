package com.automationexercices.utils.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import com.automationexercices.utils.logs.LogsManager;
/*
الفرق بين BrowserActions وElementActions
إن الأولى بتتعامل مع نافذة المتصفح والتنقل، بينما الثانية بتتعامل مع عناصر الصفحة.
*/
public class BrowserActions {
    private final WebDriver driver;
    public BrowserActions(WebDriver driver) {
        this.driver = driver;
    }

    public void maximizeWindow() {
        driver.manage().window().maximize();
    }

    public void navigateTo(String url) {
        driver.get(url);
        LogsManager.info("Navigated to URL: " + url);
    }

    public String getCurrentUrl() {
        String currentUrl = driver.getCurrentUrl();
        LogsManager.info("Current URL: " + currentUrl);
        return currentUrl;
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void closeCurrentWindow() {
        driver.close();
    }

    public void openNewWindow() {
        driver.switchTo().newWindow(WindowType.WINDOW);
    }

}
