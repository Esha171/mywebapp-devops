package com.pawsandclaws.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

/**
 * Page Object Model for the Adopt Pets Page of Paws and Claws application.
 */
public class AdoptPetsPage {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Locators
    private By pageHeader = By.xpath("//h3[contains(text(),'Adopt a Pet')]");
    private By filterDropdown = By.id("filter");
    private By petCards = By.cssSelector(".grid > div");
    private By adoptNowButtons = By.xpath("//button[contains(text(),'Adopt Now')]");
    private By petNames = By.cssSelector(".grid > div h4");
    private By petAnimals = By.cssSelector(".grid > div p");
    
    public AdoptPetsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    
    public void navigateTo(String baseUrl) {
        driver.get(baseUrl + "/adopt-pets");
        waitForPageLoad();
    }
    
    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeader));
    }
    
    public boolean isPageHeaderDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeader)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getPageHeaderText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeader)).getText();
    }
    
    public boolean isFilterDropdownDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(filterDropdown)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void selectFilter(String filterValue) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(filterDropdown));
        Select select = new Select(dropdown);
        select.selectByValue(filterValue);
        // Wait for the list to update
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public String getSelectedFilter() {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(filterDropdown));
        Select select = new Select(dropdown);
        return select.getFirstSelectedOption().getAttribute("value");
    }
    
    public List<WebElement> getPetCards() {
        try {
            Thread.sleep(1000); // Wait for API response
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return driver.findElements(petCards);
    }
    
    public int getPetCount() {
        return getPetCards().size();
    }
    
    public List<WebElement> getAdoptNowButtons() {
        return driver.findElements(adoptNowButtons);
    }
    
    public void clickFirstAdoptNowButton() {
        List<WebElement> buttons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(adoptNowButtons));
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
        }
    }
    
    public List<WebElement> getPetNames() {
        return driver.findElements(petNames);
    }
    
    public boolean arePetsDisplayed() {
        try {
            List<WebElement> cards = getPetCards();
            return !cards.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}
