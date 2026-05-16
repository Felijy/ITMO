package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {
    private final By searchButton = By.xpath("//button[contains(@class, 'header-search-button')]");
    private final By searchInput = By.xpath("//input[contains(@id, 'query-builder')]");

    private final By signUpLink = By.xpath("//a[contains(@href, '/signup')]");

    private final By openSourceMenu = By.xpath("//button[contains(., 'Open Source')]");
    private final By sponsorsLink = By.xpath("//a[contains(@href, '/sponsors')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get("https://github.com/");
        return this;
    }

    public SearchResultsPage searchFor(String query) {
        waitForElementClickable(searchButton);
        click(searchButton);
        WebElement input = waitForElementVisible(searchInput);
        input.clear();
        input.sendKeys(query);
        input.sendKeys(Keys.ENTER);
        return new SearchResultsPage(driver);
    }

    public boolean navigateToSponsors() {
        waitForElementClickable(openSourceMenu);

        click(openSourceMenu);
        waitForElementVisible(sponsorsLink);
        click(sponsorsLink);
        return true;
    }

    public SignUpPage clickSignUp() {
        waitForElementClickable(signUpLink);
        click(signUpLink);
        return new SignUpPage(driver);
    }
}
