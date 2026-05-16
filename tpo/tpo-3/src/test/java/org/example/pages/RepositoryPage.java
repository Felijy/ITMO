package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RepositoryPage extends BasePage {
    private final By codeButton = By.xpath("//button[.//span[text()='Code']]");
    private final By downloadZipXPath = By.xpath("//span[text()='Download ZIP']");
    private final By starButton = By.xpath("//a[.//span[normalize-space(text())='Star']] | //button[.//span[normalize-space(text())='Star']]");

    public RepositoryPage(WebDriver driver) {
        super(driver);
    }

    public RepositoryPage open(String url) {
        driver.get(url);
        return this;
    }

    public RepositoryPage clickCodeButton() {
        waitForElementClickable(codeButton);
        click(codeButton);
        return this;
    }

    public boolean isDownloadZipVisible() {
        try {
            waitForElementVisible(downloadZipXPath);
            return true;
        } catch (Exception e) {
            System.err.println("Could not find Download ZIP:");
            printPageDebugInfo();
            return false;
        }
    }

    public RepositoryPage clickStar() {
        waitForElementClickable(starButton).click();
        return this;
    }

    public boolean isRedirectedToLogin() {
        wait.until(ExpectedConditions.urlContains("login"));
        return driver.getCurrentUrl().contains("login");
    }
}
