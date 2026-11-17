package com.automationexercices.drivers;

import org.openqa.selenium.WebDriver;

public interface WebDriverProvider {
    WebDriver getDriver();
    /*
    دي interface بسيطة بتحدد عقدًا: أي Class تعمل implements WebDriverProvider لازم توفر ميثود اسمها getDriver() بترجع WebDriver.
    */
}
