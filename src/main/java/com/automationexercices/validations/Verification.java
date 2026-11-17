package com.automationexercices.validations;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

// Hard Assersion
public class Verification extends BaseAssertion {

    public Verification(){
        super(); /// دي اللي بتخليني اورث ال parent constructor
    } // Default constructor for restAssure

    public Verification(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    protected void assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
    }

    @Override
    protected void assertEquals(String actual, String expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }
}
