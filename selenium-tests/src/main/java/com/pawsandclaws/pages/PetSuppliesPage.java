package com.pawsandclaws.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

/**
 * Page Object Model for the Pet Supplies Pages of Paws and Claws application.
 */
public class PetSuppliesPage {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Locators
    private By petFoodLink = By.xpath("//a[contains(text(),'Pet Food')]");
    private By accessoriesLink = By.xpath("//a[contains(text(),'Accessories')]");
    private By productCards = By.cssSelector(".grid > div, .product-card");
    private By addToCartButtons = By.xpath("//button[contains(text(),'Add') or contains(text(),'Cart')]");
    private By productNames = By.cssSelector(".grid > div h4, .product-card h4, .product-name");
    private By productPrices = By.cssSelector(".grid > div p, .product-card p, .product-price");
    
    public PetSuppliesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void navigateTo(String baseUrl) {
        driver.get(baseUrl + "/pet-supplies");
    }
    
    public void navigateToFood(String baseUrl) {
        driver.get(baseUrl + "/pet-supplies/food");
        waitForPageLoad();
    }
    
    public void navigateToAccessories(String baseUrl) {
        driver.get(baseUrl + "/pet-supplies/accessories");
        waitForPageLoad();
    }
    
    public void waitForPageLoad() {
        try {
            Thread.sleep(2000); // Wait for products to load
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void clickPetFoodLink() {
        wait.until(ExpectedConditions.elementToBeClickable(petFoodLink)).click();
    }
    
    public void clickAccessoriesLink() {
        wait.until(ExpectedConditions.elementToBeClickable(accessoriesLink)).click();
    }
    
    public List<WebElement> getProductCards() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return driver.findElements(productCards);
    }
    
    public int getProductCount() {
        return getProductCards().size();
    }
    
    public List<WebElement> getAddToCartButtons() {
        return driver.findElements(addToCartButtons);
    }
    
    public void clickAddToCart(int productIndex) {
        List<WebElement> buttons = getAddToCartButtons();
        if (productIndex < buttons.size()) {
            buttons.get(productIndex).click();
        }
    }
    
    public List<WebElement> getProductNames() {
        return driver.findElements(productNames);
    }
    
    public List<WebElement> getProductPrices() {
        return driver.findElements(productPrices);
    }
    
    public boolean areProductsDisplayed() {
        try {
            List<WebElement> products = getProductCards();
            return !products.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
