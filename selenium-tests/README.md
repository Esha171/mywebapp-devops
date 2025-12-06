# Paws & Claws - Selenium Automated Tests

This project contains automated Selenium test cases for the **Paws & Claws** Pet Adoption Web Application. These tests are designed to run in a headless Chrome browser environment, making them suitable for CI/CD pipelines like Jenkins on AWS EC2.

## Project Structure

```
selenium-tests/
├── pom.xml                                    # Maven configuration
├── testng.xml                                 # TestNG suite configuration
├── README.md                                  # This file
└── src/
    ├── main/java/com/pawsandclaws/
    │   ├── config/
    │   │   └── WebDriverConfig.java           # WebDriver configuration (headless Chrome)
    │   └── pages/
    │       ├── HomePage.java                  # Home page POM
    │       ├── LoginPopupPage.java            # Login popup POM
    │       ├── AdoptPetsPage.java             # Adopt pets page POM
    │       ├── AdoptionPopupPage.java         # Adoption form popup POM
    │       ├── CartPage.java                  # Cart page POM
    │       └── PetSuppliesPage.java           # Pet supplies page POM
    └── test/java/com/pawsandclaws/tests/
        ├── BaseTest.java                      # Base test class
        ├── HomePageTests.java                 # Home page tests
        ├── NavigationTests.java               # Navigation tests
        ├── LoginPopupTests.java               # Login popup tests
        ├── AdoptPetsTests.java                # Adopt pets tests
        ├── CartTests.java                     # Cart page tests
        └── PetSuppliesTests.java              # Pet supplies tests
```

## Test Cases Summary (12 Test Cases)

| Test ID | Test Name | Description | Page/Feature |
|---------|-----------|-------------|--------------|
| TC001 | testHomePageLoads | Verify home page loads successfully with navbar and brand name | Home Page |
| TC002 | testCategoriesDisplayed | Verify pet categories are displayed on home page | Home Page |
| TC003 | testNavigateToAdoptPets | Verify navigation to Adopt Pets page works | Navigation |
| TC004 | testNavigateToCart | Verify navigation to Cart page works | Navigation |
| TC005 | testLoginPopupOpens | Verify login popup opens with email/password fields | Login Popup |
| TC006 | testLoginPopupCloses | Verify login popup closes when close button clicked | Login Popup |
| TC007 | testAdoptPetsPageLoadsWithFilter | Verify Adopt Pets page loads with filter dropdown | Adopt Pets |
| TC008 | testAdoptionPopupOpens | Verify adoption form popup opens when Adopt Now clicked | Adoption Form |
| TC009 | testCartPageLoads | Verify cart page loads successfully | Cart |
| TC010 | testEmptyCartMessage | Verify empty cart displays appropriate message | Cart |
| TC011 | testNavigateToPetFood | Verify navigation to Pet Food page | Pet Supplies |
| TC012 | testNavigateToPetAccessories | Verify navigation to Pet Accessories page | Pet Supplies |

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- Chrome browser (for local testing)
- Internet connection (for downloading dependencies)

## Configuration

### Environment Variables

- `BASE_URL`: The base URL of the application (default: `http://localhost:5173`)

### Headless Chrome

The tests are configured to run in **headless Chrome** mode by default, which is required for:
- CI/CD pipelines
- AWS EC2 instances without display
- Docker containers

## Running Tests

### Local Execution

```bash
# Navigate to selenium-tests directory
cd selenium-tests

# Run all tests
mvn clean test

# Run with custom base URL
mvn clean test -DBASE_URL=http://your-app-url:port
```

### Docker Execution (CI/CD)

Using the `markhobson/maven-chrome` image:

```bash
docker run --rm -v $(pwd):/app -w /app markhobson/maven-chrome mvn clean test
```

### Jenkins Pipeline

The tests are designed to be integrated into a Jenkins pipeline. See the main `Jenkinsfile` for the test stage configuration.

## Test Reports

After running tests, reports are generated in:
- `target/surefire-reports/` - TestNG XML reports
- `target/surefire-reports/emailable-report.html` - HTML report

## Design Patterns Used

1. **Page Object Model (POM)**: Each page has its own class with locators and methods
2. **Explicit Waits**: WebDriverWait for reliable element interactions
3. **Configuration Separation**: WebDriverConfig class for driver management
4. **TestNG Annotations**: Proper setup and teardown methods

## Features Tested

- ✅ Home page loading
- ✅ Pet categories display
- ✅ Navigation between pages
- ✅ Login popup functionality
- ✅ Adopt pets page with filter
- ✅ Adoption form popup
- ✅ Shopping cart page
- ✅ Pet supplies (Food & Accessories)

## Author

Paws & Claws Pet Adoption App - DevOps Assignment
CSC483 – Topics in Computer Science II (DevOps)
COMSATS University, Islamabad
