package com.pawsandclaws.tests;

import com.pawsandclaws.config.WebDriverConfig;
import com.pawsandclaws.pages.HomePage;
import com.pawsandclaws.pages.LoginPopupPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Test class for Login Popup functionality.
 * Tests: TC005, TC006
 */
public class LoginPopupTests {
    
    private WebDriver driver;
    private HomePage homePage;
    private LoginPopupPage loginPopupPage;
    private String baseUrl;
    
    @BeforeClass
    public void setUp() {
        driver = WebDriverConfig.getDriver();
        baseUrl = WebDriverConfig.BASE_URL;
        homePage = new HomePage(driver);
        loginPopupPage = new LoginPopupPage(driver);
    }
    
    @BeforeMethod
    public void navigateToHome() {
        homePage.navigateTo(baseUrl);
    }
    
    /**
     * TC005: Verify Login Popup Opens
     * Verifies that clicking Login button opens the login popup.
     */
    @Test(priority = 1, description = "TC005: Verify login popup opens when Login button is clicked")
    public void testLoginPopupOpens() {
        System.out.println("TC005: Testing Login Popup Opens...");
        
        // Click Login button
        homePage.clickLoginButton();
        
        // Wait for popup
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify popup is displayed
        Assert.assertTrue(loginPopupPage.isPopupDisplayed(), 
                "Login popup should be displayed after clicking Login button");
        
        // Verify email and password fields are present
        Assert.assertTrue(loginPopupPage.isEmailInputDisplayed(), 
                "Email input should be displayed in login popup");
        Assert.assertTrue(loginPopupPage.isPasswordInputDisplayed(), 
                "Password input should be displayed in login popup");
        
        // Close popup for next test
        loginPopupPage.closePopup();
        
        System.out.println("TC005: PASSED - Login popup opens correctly");
    }
    
    /**
     * TC006: Verify Login Popup Close Functionality
     * Verifies that the login popup can be closed using the close button.
     */
    @Test(priority = 2, description = "TC006: Verify login popup closes when close button is clicked")
    public void testLoginPopupCloses() {
        System.out.println("TC006: Testing Login Popup Close...");
        
        // Click Login button to open popup
        homePage.clickLoginButton();
        
        // Wait for popup
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify popup is displayed
        Assert.assertTrue(loginPopupPage.isPopupDisplayed(), 
                "Login popup should be displayed");
        
        // Close popup
        loginPopupPage.closePopup();
        
        // Wait for close animation
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify popup is closed
        Assert.assertFalse(loginPopupPage.isPopupDisplayed(), 
                "Login popup should be closed after clicking close button");
        
        System.out.println("TC006: PASSED - Login popup closes correctly");
    }
    
    @AfterClass
    public void tearDown() {
        // Driver cleanup handled by last test class
    }
}
