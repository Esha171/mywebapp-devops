package com.pawsandclaws.tests;

import com.pawsandclaws.config.WebDriverConfig;
import com.pawsandclaws.pages.PetSuppliesPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Test class for Pet Supplies Page functionality.
 * Tests: TC011, TC012
 */
public class PetSuppliesTests {
    
    private WebDriver driver;
    private PetSuppliesPage petSuppliesPage;
    private String baseUrl;
    
    @BeforeClass
    public void setUp() {
        driver = WebDriverConfig.getDriver();
        baseUrl = WebDriverConfig.BASE_URL;
        petSuppliesPage = new PetSuppliesPage(driver);
    }
    
    /**
     * TC011: Verify Pet Food Page Navigation
     * Verifies navigation to Pet Food page works correctly.
     */
    @Test(priority = 1, description = "TC011: Verify navigation to Pet Food page")
    public void testNavigateToPetFood() {
        System.out.println("TC011: Testing Pet Food Page Navigation...");
        
        // Navigate to Pet Food page
        petSuppliesPage.navigateToFood(baseUrl);
        
        // Verify URL
        String currentUrl = petSuppliesPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("food"), 
                "URL should contain 'food'. Actual: " + currentUrl);
        
        System.out.println("TC011: PASSED - Navigated to Pet Food page");
    }
    
    /**
     * TC012: Verify Pet Accessories Page Navigation
     * Verifies navigation to Pet Accessories page works correctly.
     */
    @Test(priority = 2, description = "TC012: Verify navigation to Pet Accessories page")
    public void testNavigateToPetAccessories() {
        System.out.println("TC012: Testing Pet Accessories Page Navigation...");
        
        // Navigate to Pet Accessories page
        petSuppliesPage.navigateToAccessories(baseUrl);
        
        // Verify URL
        String currentUrl = petSuppliesPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("accessories"), 
                "URL should contain 'accessories'. Actual: " + currentUrl);
        
        System.out.println("TC012: PASSED - Navigated to Pet Accessories page");
    }
    
    @AfterClass
    public void tearDown() {
        // Final cleanup - quit the driver
        WebDriverConfig.quitDriver();
        System.out.println("All tests completed. Browser closed.");
    }
}
