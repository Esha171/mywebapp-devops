package com.pawsandclaws.tests;

import com.pawsandclaws.config.WebDriverConfig;
import com.pawsandclaws.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Test class for Navigation functionality.
 * Tests: TC003, TC004
 */
public class NavigationTests {
    
    private WebDriver driver;
    private HomePage homePage;
    private String baseUrl;
    
    @BeforeClass
    public void setUp() {
        driver = WebDriverConfig.getDriver();
        baseUrl = WebDriverConfig.BASE_URL;
        homePage = new HomePage(driver);
    }
    
    @BeforeMethod
    public void navigateToHome() {
        homePage.navigateTo(baseUrl);
    }
    
    /**
     * TC003: Verify Navigation to Adopt Pets Page
     * Verifies that clicking 'Adopt Pets' link navigates to the correct page.
     */
    @Test(priority = 1, description = "TC003: Verify navigation to Adopt Pets page")
    public void testNavigateToAdoptPets() {
        System.out.println("TC003: Testing Navigation to Adopt Pets...");
        
        // Click Adopt Pets link
        homePage.clickAdoptPetsLink();
        
        // Wait for navigation
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify URL contains adopt-pets
        String currentUrl = homePage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("adopt-pets"), 
                "URL should contain 'adopt-pets' after navigation. Actual: " + currentUrl);
        
        System.out.println("TC003: PASSED - Navigated to Adopt Pets page");
    }
    
    /**
     * TC004: Verify Navigation to Cart Page
     * Verifies that clicking cart icon navigates to the cart page.
     */
    @Test(priority = 2, description = "TC004: Verify navigation to Cart page")
    public void testNavigateToCart() {
        System.out.println("TC004: Testing Navigation to Cart...");
        
        // Click Cart icon
        homePage.clickCartIcon();
        
        // Wait for navigation
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify URL contains cart
        String currentUrl = homePage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("cart"), 
                "URL should contain 'cart' after navigation. Actual: " + currentUrl);
        
        System.out.println("TC004: PASSED - Navigated to Cart page");
    }
    
    @AfterClass
    public void tearDown() {
        // Driver cleanup handled by last test class
    }
}
