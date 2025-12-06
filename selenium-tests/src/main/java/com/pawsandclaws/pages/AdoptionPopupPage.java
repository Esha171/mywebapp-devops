package com.pawsandclaws.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Page Object Model for the Adoption Popup Form of Paws and Claws application.
 */
public class AdoptionPopupPage {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Locators
    private By adoptionPopup = By.cssSelector(".adoption-popup");
    private By popupContainer = By.cssSelector(".adoption-popup-container");
    private By popupHeader = By.cssSelector(".adoption-popup-header h2");
    private By closeButton = By.cssSelector(".adoption-popup .close-btn");
    private By nameInput = By.cssSelector("input[name='name']");
    private By emailInput = By.cssSelector("input[name='email']");
    private By phoneInput = By.cssSelector("input[name='phoneNo']");
    private By livingSituationDropdown = By.cssSelector("select[name='livingSituation']");
    private By addressInput = By.cssSelector("input[name='address'], textarea[name='address']");
    private By previousPetsInput = By.cssSelector("input[name='previousPets'], textarea[name='previousPets']");
    private By otherPetsInput = By.cssSelector("input[name='otherPets'], textarea[name='otherPets']");
    private By submitButton = By.cssSelector(".adoption-popup form button[type='submit']");
    private By errorMessages = By.cssSelector(".error-text");
    
    public AdoptionPopupPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public boolean isPopupDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(adoptionPopup)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getPopupHeaderText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(popupHeader)).getText();
    }
    
    public void enterName(String name) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
        field.clear();
        field.sendKeys(name);
    }
    
    public void enterEmail(String email) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        field.clear();
        field.sendKeys(email);
    }
    
    public void enterPhone(String phone) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(phoneInput));
        field.clear();
        field.sendKeys(phone);
    }
    
    public void selectLivingSituation(String situation) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(livingSituationDropdown));
        Select select = new Select(dropdown);
        select.selectByValue(situation);
    }
    
    public void enterAddress(String address) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(addressInput));
        field.clear();
        field.sendKeys(address);
    }
    
    public void enterPreviousPets(String previousPets) {
        try {
            WebElement field = driver.findElement(previousPetsInput);
            field.clear();
            field.sendKeys(previousPets);
        } catch (Exception e) {
            // Field might not be required
        }
    }
    
    public void enterOtherPets(String otherPets) {
        try {
            WebElement field = driver.findElement(otherPetsInput);
            field.clear();
            field.sendKeys(otherPets);
        } catch (Exception e) {
            // Field might not be required
        }
    }
    
    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }
    
    public void closePopup() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
    }
    
    public boolean isNameInputDisplayed() {
        try {
            return driver.findElement(nameInput).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isEmailInputDisplayed() {
        try {
            return driver.findElement(emailInput).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isPhoneInputDisplayed() {
        try {
            return driver.findElement(phoneInput).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isLivingSituationDropdownDisplayed() {
        try {
            return driver.findElement(livingSituationDropdown).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void fillAdoptionForm(String name, String email, String phone, 
                                  String livingSituation, String address) {
        enterName(name);
        enterEmail(email);
        enterPhone(phone);
        selectLivingSituation(livingSituation);
        enterAddress(address);
    }
}
