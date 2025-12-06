package com.pawsandclaws.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

/**
 * Page Object Model for the Home Page of Paws and Claws application.
 */
public class HomePage {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Locators
    private By logo = By.xpath("//img[@alt='Home']");
    private By brandName = By.xpath("//span[contains(text(),'PAWS & CLAWS')]");
    private By homeLink = By.xpath("//a[text()='Home']");
    private By adoptPetsLink = By.xpath("//a[text()='Adopt Pets']");
    private By petSuppliesLink = By.xpath("//a[contains(text(),'Pet Supplies')]");
    private By loginButton = By.xpath("//button[contains(text(),'Log In')]");
    private By cartIcon = By.xpath("//img[@alt='Cart']");
    private By categoryItems = By.cssSelector(".explore-categories-list-item");
    private By banner = By.cssSelector(".carousel, .banner, [class*='banner']");
    private By navbar = By.cssSelector(".navbar");
    private By footer = By.tagName("footer");
    
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void navigateTo(String baseUrl) {
        driver.get(baseUrl);
        wait.until(ExpectedConditions.visibilityOfElementLocated(navbar));
    }
    
    public boolean isLogoDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(logo)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isBrandNameDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(brandName)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    public void clickHomeLink() {
        wait.until(ExpectedConditions.elementToBeClickable(homeLink)).click();
    }
    
    public void clickAdoptPetsLink() {
        wait.until(ExpectedConditions.elementToBeClickable(adoptPetsLink)).click();
    }
    
    public void clickPetSuppliesLink() {
        wait.until(ExpectedConditions.elementToBeClickable(petSuppliesLink)).click();
    }
    
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }
    
    public void clickCartIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
    }
    
    public List<WebElement> getCategoryItems() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(categoryItems));
        return driver.findElements(categoryItems);
    }
    
    public void clickCategory(String categoryName) {
        By categoryLocator = By.xpath("//h3[contains(text(),'" + categoryName + "')]/parent::div");
        wait.until(ExpectedConditions.elementToBeClickable(categoryLocator)).click();
    }
    
    public boolean isNavbarDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(navbar)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isFooterDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(footer)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
