package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchResultsPage extends BasePage {

    private final By languageMenu = By.xpath("//*[contains(text(), 'Language')]");
    private final By sortButton = By.xpath("//*[contains(text(), 'Sort')]");
    private final By mostStarsOption = By.xpath("//*[contains(text(), 'Most stars')]");
    private final By filterByLanguageC = By.xpath("//*[@title='C']");
    private final By languageC = By.xpath("//*[@aria-label='C language']");
    private final By stars = By.xpath("//a[contains(@href, '/stargazers')]");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isSpecificResultFound(String repoPath) {
        By specificLink = By.xpath("//a[contains(@href, '/" + repoPath + "')]");
        waitForElementVisible(specificLink);
        return true;
    }

    public SearchResultsPage filterByLanguageC() {
        waitForElementClickable(languageMenu).click();
        waitForElementClickable(filterByLanguageC).click();
        return this;
    }

    public List<WebElement> getLanguageResults() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(languageC));
    }

    public SearchResultsPage sortByMostStars() {
        waitForElementClickable(sortButton).click();
        waitForElementClickable(mostStarsOption).click();
        return this;
    }

    public List<WebElement> getStarResults() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(stars));
    }
}
