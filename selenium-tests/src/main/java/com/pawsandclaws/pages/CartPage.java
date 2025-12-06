package com.pawsandclaws.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

/**
 * Page Object Model for the Cart Page of Paws and Claws application.
 */
public class CartPage {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Locators
    private By cartHeader = By.xpath("//h1[contains(text(),'Shopping Cart')]");
    private By emptyCartMessage = By.xpath("//p[contains(text(),'Your cart is empty')]");
    private By cartItems = By.cssSelector("ul > li");
    private By increaseButtons = By.xpath("//button[text()='+']");
    private By decreaseButtons = By.xpath("//button[text()='-']");
    private By removeButtons = By.cssSelector("img[alt='Remove']");
    private By totalPrice = By.xpath("//h3[contains(text(),'Total')]//span");
    private By checkoutButton = By.xpath("//button[contains(text(),'Checkout')]");
    private By itemQuantity = By.cssSelector("ul > li span.font-medium");
    
    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void navigateTo(String baseUrl) {
        driver.get(baseUrl + "/cart");
        waitForPageLoad();
    }
    
    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartHeader));
    }
    
    public boolean isCartHeaderDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(cartHeader)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getCartHeaderText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartHeader)).getText();
    }
    
    public boolean isCartEmpty() {
        try {
            return driver.findElement(emptyCartMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public List<WebElement> getCartItems() {
        try {
            return driver.findElements(cartItems);
        } catch (Exception e) {
            return List.of();
        }
    }
    
    public int getCartItemCount() {
        return getCartItems().size();
    }
    
    public void clickIncreaseQuantity(int itemIndex) {
        List<WebElement> buttons = driver.findElements(increaseButtons);
        if (itemIndex < buttons.size()) {
            buttons.get(itemIndex).click();
        }
    }
    
    public void clickDecreaseQuantity(int itemIndex) {
        List<WebElement> buttons = driver.findElements(decreaseButtons);
        if (itemIndex < buttons.size()) {
            buttons.get(itemIndex).click();
        }
    }
    
    public void removeItem(int itemIndex) {
        List<WebElement> buttons = driver.findElements(removeButtons);
        if (itemIndex < buttons.size()) {
            buttons.get(itemIndex).click();
        }
    }
    
    public String getTotalPrice() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(totalPrice)).getText();
        } catch (Exception e) {
            return "0";
        }
    }
    
    public boolean isCheckoutButtonDisplayed() {
        try {
            return driver.findElement(checkoutButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
    }
}
