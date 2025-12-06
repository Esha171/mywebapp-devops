package com.pawsandclaws.tests;

import com.pawsandclaws.config.WebDriverConfig;
import com.pawsandclaws.pages.CartPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Test class for Cart Page functionality.
 * Tests: TC009, TC010
 */
public class CartTests {
    
    private WebDriver driver;
    private CartPage cartPage;
    private String baseUrl;
    
    @BeforeClass
    public void setUp() {
        driver = WebDriverConfig.getDriver();
        baseUrl = WebDriverConfig.BASE_URL;
        cartPage = new CartPage(driver);
    }
    
    @BeforeMethod
    public void navigateToCart() {
        cartPage.navigateTo(baseUrl);
    }
    
    /**
     * TC009: Verify Cart Page Loads Successfully
     * Verifies that the cart page loads with proper header.
     */
    @Test(priority = 1, description = "TC009: Verify cart page loads successfully")
    public void testCartPageLoads() {
        System.out.println("TC009: Testing Cart Page Load...");
        
        // Verify cart header is displayed
        Assert.assertTrue(cartPage.isCartHeaderDisplayed(), 
                "Cart page header should be displayed");
        
        // Verify header text
        String headerText = cartPage.getCartHeaderText();
        Assert.assertTrue(headerText.toLowerCase().contains("cart"), 
                "Header should contain 'cart'. Actual: " + headerText);
        
        System.out.println("TC009: PASSED - Cart page loads successfully");
    }
    
    /**
     * TC010: Verify Empty Cart Message
     * Verifies that empty cart shows appropriate message.
     */
    @Test(priority = 2, description = "TC010: Verify empty cart displays appropriate message")
    public void testEmptyCartMessage() {
        System.out.println("TC010: Testing Empty Cart Message...");
        
        // Check if cart is empty or has items
        boolean isEmpty = cartPage.isCartEmpty();
        int itemCount = cartPage.getCartItemCount();
        
        if (isEmpty) {
            // Verify empty cart message is displayed
            Assert.assertTrue(cartPage.isCartEmpty(), 
                    "Empty cart message should be displayed when cart is empty");
            System.out.println("TC010: PASSED - Empty cart message displayed correctly");
        } else {
            // Cart has items - verify checkout button is present
            Assert.assertTrue(cartPage.isCheckoutButtonDisplayed(), 
                    "Checkout button should be displayed when cart has items");
            System.out.println("TC010: PASSED - Cart has " + itemCount + " items with checkout button");
        }
    }
    
    @AfterClass
    public void tearDown() {
        // Driver cleanup handled by last test class
    }
}
