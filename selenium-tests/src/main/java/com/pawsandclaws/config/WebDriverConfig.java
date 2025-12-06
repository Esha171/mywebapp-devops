package com.pawsandclaws.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * WebDriver configuration class for Selenium tests.
 * Configures headless Chrome for CI/CD pipeline execution.
 */
public class WebDriverConfig {
    
    private static WebDriver driver;
    
    // Base URL - can be overridden via environment variable
    public static final String BASE_URL = System.getenv("BASE_URL") != null 
            ? System.getenv("BASE_URL") 
            : "http://localhost:5173";
    
    /**
     * Initialize and return a headless Chrome WebDriver instance.
     * Uses headless mode for running in CI/CD pipelines (e.g., Jenkins on AWS EC2).
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            // Setup ChromeDriver using WebDriverManager
            WebDriverManager.chromedriver().setup();
            
            // Configure Chrome options for headless execution
            ChromeOptions options = new ChromeOptions();
            
            // Headless mode - required for CI/CD pipeline
            options.addArguments("--headless=new");
            
            // Additional options for stability in containerized environments
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-infobars");
            
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        }
        return driver;
    }
    
    /**
     * Quit and cleanup the WebDriver instance.
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
