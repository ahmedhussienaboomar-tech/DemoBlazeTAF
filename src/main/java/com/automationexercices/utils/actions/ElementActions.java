
package com.automationexercices.utils.actions;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import com.automationexercices.utils.WaitManager;
import com.automationexercices.utils.logs.LogsManager;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ElementActions {

    private WebDriver driver;
    private WaitManager waitManager;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    // Add methods for common actions like click, type, etc.

    public ElementActions click(By Locator) {
        waitManager.fluentWait()
                .until(d -> {
                    try {
                        WebElement element = d.findElement(Locator);

                        // Use the same WebElement for scrolling and clicking.
                        scrollToElementJS(element);

                        if (!element.isDisplayed() || !element.isEnabled()) {
                            return false;
                        }

                        Point initialLocaion = element.getLocation();
                        LogsManager.info("initialLocation" + initialLocaion);

                        Point finalLocation = element.getLocation();
                        LogsManager.info("finalLocation" + finalLocation);

                        if (!initialLocaion.equals(finalLocation)) {
                            return false;
                        }

                        element.click();
                        LogsManager.info("Clicked on element: " + Locator);

                        return true;

                    } catch (NoSuchElementException |
                             StaleElementReferenceException |
                             ElementNotInteractableException e) {

                        LogsManager.error(
                                "Failed to click element: " + Locator
                                        + " | Exception: " + e.getClass().getSimpleName()
                                        + " | Message: " + e.getMessage()
                        );

                        return false;
                    }
                });

        /*
         * Notice that we retrieve the element once and reuse the same WebElement
         * for scrolling and clicking instead of locating it again.
         *
         * Catching all exceptions and returning false allows FluentWait to retry,
         * but it can hide the actual reason for failure.
         *
         * ElementClickInterceptedException does not always mean that waiting longer
         * will solve the problem. An overlay or another element may be blocking
         * the target element.
         *
         * For a final framework design, keep useful error logs and handle only
         * expected exceptions during retries.
         */

        return this;
    }

    public ElementActions type(By Locator, String text) {
        waitManager.fluentWait()
                .until(d -> {
                    try {
                        WebElement element = d.findElement(Locator);

                        // Use the same WebElement for scrolling and typing.
                        scrollToElementJS(element);

                        LogsManager.info("Typing text '" + text + "' into element: " + Locator);
                        element.clear();
                        LogsManager.info("Cleared text in element: " + Locator);

                        element.sendKeys(text);
                        LogsManager.info("Typed text '" + text + "' into element: " + Locator);

                        return true;

                    } catch (Exception e) {
                        LogsManager.error(
                                "Error occurred while typing text into element: " + Locator
                                        + " | Exception: " + e.getClass().getSimpleName()
                                        + " | Message: " + e.getMessage()
                        );

                        return false;
                    }
                });

        return this;
    }

    public String getText(By locator) {
        return waitManager.fluentWait()
                .until(d -> {
                    try {
                        WebElement element = d.findElement(locator);

                        LogsManager.info("Element found: " + locator);

                        String msg = element.getText();

                        LogsManager.info("Element text: [" + msg + "]");

                        return !msg.trim().isEmpty() ? msg : null;

                    } catch (Exception e) {
                        LogsManager.error(
                                "Failed to get text from: " + locator
                                        + " | Exception: " + e.getClass().getSimpleName()
                                        + " | Message: " + e.getMessage()
                        );
                        return null;
                    }
                });
    }

    public ElementActions upLoadFile(By Locator, String filePath) {
        String absolutePath = new java.io.File(filePath).getAbsolutePath();

        waitManager.fluentWait()
                .until(d -> {
                    try {
                        WebElement element = d.findElement(Locator);

                        // Scroll to the file input before uploading.
                        scrollToElementJS(element);

                        LogsManager.info("Scrolling to element: " + Locator);

                        element.sendKeys(absolutePath);

                        LogsManager.info(
                                "Uploaded file '" + absolutePath + "' to element: " + Locator
                        );

                        return true;

                    } catch (Exception e) {
                        LogsManager.error(
                                "Error occurred while uploading file to element: " + Locator
                                        + " | Exception: " + e.getClass().getSimpleName()
                                        + " | Message: " + e.getMessage()
                        );

                        return false;
                    }
                });

        /*
         * Selenium usually uploads files by sending the file path to an
         * <input type="file"> element using sendKeys().
         *
         * Why convert the path to an Absolute Path?
         * A relative path may depend on the Maven working directory or
         * the current working directory, while an absolute path identifies
         * the file location explicitly.
         *
         * Important:
         * This approach works when the file input is directly accessible.
         * For Remote Selenium Grid, the file must be available to the remote
         * environment, or the appropriate remote file-upload mechanism
         * should be used.
         */

        return this;
    }

    public WebElement findElement(By Locator) {
        LogsManager.info("Finding element: " + Locator);
        return driver.findElement(Locator);
    }

    public void scrollToElementJS(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("""
                        arguments[0].scrollIntoView({
                            behavior: "auto",
                            block: "center",
                            inline: "center"
                        });
                        """,
                        element
                );

        /*
         * arguments[0] represents the first argument passed to executeScript().
         * In this case, it is the WebElement that we want to scroll to.
         *
         * The element is brought into the visible area before interacting with it.
         *
         * However, scrolling does not guarantee that the element is clickable.
         * Another element may cover it, or it may not be ready for interaction.
         */
    }

    // Select from dropdown

    public ElementActions selectFromDropdown(By locator, String value) {
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = d.findElement(locator);

                // Use the same WebElement for scrolling and selecting.
                scrollToElementJS(element);

                Select select = new Select(element);
                select.selectByVisibleText(value);

                LogsManager.info(
                        "Selected value '" + value +
                                "' from dropdown: " + locator
                );

                return true;

            } catch (Exception e) {
                LogsManager.error(
                        "Error selecting value '" + value +
                                "' from dropdown: " + locator +
                                " | Cause: " + e.getClass().getSimpleName() +
                                " | Message: " + e.getMessage()
                );

                return false;
            }
        });

        return this;
    }

    public ElementActions hover(By locator) {
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = d.findElement(locator);

                // Use the same WebElement for scrolling and hovering.
                scrollToElementJS(element);

                Actions actions = new Actions(driver);
                actions.moveToElement(element).perform();

                LogsManager.info("Hovered over element: " + locator);

                return true;

            } catch (Exception e) {
                LogsManager.error(
                        "Error hovering over element: " + locator +
                                " | Cause: " + e.getClass().getSimpleName() +
                                " | Message: " + e.getMessage()
                );

                return false;
            }
        });

        return this;
    }

    // Hover over an element and click another element without scrolling again.
    public ElementActions hoverAndClick(By hoverLocator, By clickLocator) {

        waitManager.fluentWait().until(d -> {
            try {
                WebElement hoverElement = d.findElement(hoverLocator);

                // Scroll once before performing the hover and click actions.
                scrollToElementJS(hoverElement);

                Actions actions = new Actions(d);
                actions.moveToElement(hoverElement).perform();

                LogsManager.info("Hovered over element: " + hoverLocator);

                // Find all matching elements to avoid selecting a hidden duplicate.
                List<WebElement> clickElements = d.findElements(clickLocator);

                LogsManager.info(
                        "Matching click elements found: " + clickElements.size()
                );

                for (WebElement clickElement : clickElements) {

                    if (!clickElement.isDisplayed() || !clickElement.isEnabled()) {
                        continue;
                    }

                    try {
                        // Do not scroll again after hovering.
                        // Use Actions to move to and click the visible target.
                        new Actions(d)
                                .moveToElement(clickElement)
                                .click()
                                .perform();

                        LogsManager.info(
                                "Clicked on element after hover: " + clickLocator
                        );

                        return true;

                    } catch (
                            ElementNotInteractableException e) {

                        LogsManager.error(
                                "Click attempt failed for element: " + clickLocator
                                        + " | Exception: " + e.getClass().getSimpleName()
                                        + " | Message: " + e.getMessage()
                        );

                        // Retry from the hover step on the next wait iteration.
                        return false;
                    }
                }

                LogsManager.info(
                        "No visible and enabled click element found: " + clickLocator
                );

                return false;

            } catch (NoSuchElementException |
                     StaleElementReferenceException |
                     MoveTargetOutOfBoundsException |
                                ElementNotInteractableException e) {

                LogsManager.error(
                        "Failed to hover and click: " + hoverLocator
                                + " -> " + clickLocator
                                + " | Exception: " + e.getClass().getSimpleName()
                                + " | Message: " + e.getMessage()
                );

                return false;
            }
        });

        return this;
    }
}
