package com.pawsandclaws.tests;

import com.pawsandclaws.config.WebDriverConfig;
import com.pawsandclaws.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Test class for Home Page functionality.
 * Tests: TC001, TC002
 */
public class HomePageTests {
    
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
     * TC001: Verify Home Page Loads Successfully
     * Verifies that the home page loads and displays the navbar with logo.
     */
    @Test(priority = 1, description = "TC001: Verify home page loads successfully with navbar")
    public void testHomePageLoads() {
        System.out.println("TC001: Testing Home Page Load...");
        
        // Verify navbar is displayed
        Assert.assertTrue(homePage.isNavbarDisplayed(), 
                "Navbar should be displayed on home page");
        
        // Verify brand name is displayed
        Assert.assertTrue(homePage.isBrandNameDisplayed(), 
                "Brand name 'PAWS & CLAWS' should be displayed");
        
        System.out.println("TC001: PASSED - Home page loaded successfully");
    }
    
    /**
     * TC002: Verify Pet Categories are Displayed
     * Verifies that pet categories section is present and has category items.
     */
    @Test(priority = 2, description = "TC002: Verify pet categories are displayed on home page")
    public void testCategoriesDisplayed() {
        System.out.println("TC002: Testing Categories Display...");
        
        // Get category items
        int categoryCount = homePage.getCategoryItems().size();
        
        // Verify categories are displayed
        Assert.assertTrue(categoryCount > 0, 
                "At least one pet category should be displayed");
        
        System.out.println("TC002: PASSED - " + categoryCount + " categories displayed");
    }
    
    @AfterClass
    public void tearDown() {
        // Driver cleanup handled by last test class
    }
}
