package com.pawsandclaws.tests;

import com.pawsandclaws.config.WebDriverConfig;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Base test class providing common setup and teardown for all test classes.
 */
public class BaseTest {
    
    protected WebDriver driver;
    protected String baseUrl;
    
    @BeforeClass
    public void setUp() {
        driver = WebDriverConfig.getDriver();
        baseUrl = WebDriverConfig.BASE_URL;
        System.out.println("Test Suite Started - Base URL: " + baseUrl);
    }
    
    @AfterClass
    public void tearDown() {
        WebDriverConfig.quitDriver();
        System.out.println("Test Suite Completed - Browser Closed");
    }
}
