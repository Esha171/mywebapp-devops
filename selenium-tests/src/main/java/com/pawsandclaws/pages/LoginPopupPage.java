package com.pawsandclaws.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Page Object Model for the Login Popup of Paws and Claws application.
 */
public class LoginPopupPage {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Locators
    private By loginPopup = By.cssSelector(".login-popup");
    private By popupContainer = By.cssSelector(".login-popup-container");
    private By popupHeader = By.cssSelector(".login-popup-header h2");
    private By closeButton = By.cssSelector(".login-popup .close-btn");
    private By emailInput = By.cssSelector("input[name='email']");
    private By passwordInput = By.cssSelector("input[name='password']");
    private By nameInput = By.cssSelector("input[name='name']");
    private By submitButton = By.cssSelector(".login-popup form button[type='submit']");
    private By switchToSignUp = By.xpath("//span[contains(text(),'Sign Up') or contains(text(),'Create')]");
    private By switchToLogin = By.xpath("//span[contains(text(),'Login') or contains(text(),'Sign In')]");
    
    public LoginPopupPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public boolean isPopupDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(loginPopup)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getPopupHeaderText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(popupHeader)).getText();
    }
    
    public void enterEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        emailField.clear();
        emailField.sendKeys(email);
    }
    
    public void enterPassword(String password) {
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        passwordField.clear();
        passwordField.sendKeys(password);
    }
    
    public void enterName(String name) {
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
        nameField.clear();
        nameField.sendKeys(name);
    }
    
    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }
    
    public void closePopup() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
    }
    
    public void switchToSignUpMode() {
        wait.until(ExpectedConditions.elementToBeClickable(switchToSignUp)).click();
    }
    
    public void switchToLoginMode() {
        wait.until(ExpectedConditions.elementToBeClickable(switchToLogin)).click();
    }
    
    public boolean isEmailInputDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isPasswordInputDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isNameInputDisplayed() {
        try {
            WebElement nameField = driver.findElement(nameInput);
            return nameField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void performLogin(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSubmit();
    }
}
