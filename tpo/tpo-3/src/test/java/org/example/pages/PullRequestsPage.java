package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class PullRequestsPage extends BasePage {

    private final By sortSummary = By.xpath("//summary[contains(., 'Sort')] | //details[contains(@class, 'details-reset')]//summary[contains(., 'Sort')]");
    private final By mostCommentedOption = By.xpath("//a[contains(., 'Most commented')] | //span[contains(., 'Most commented')]");
    private final By commentsXPath = By.xpath("//a[contains(@aria-label, 'comment')] | //svg[contains(@class, 'octicon-comment')]/following-sibling::span");

    public PullRequestsPage(WebDriver driver) {
        super(driver);
    }

    public PullRequestsPage open(String url) {
        driver.get(url);
        return this;
    }

    public PullRequestsPage sortByMostCommented() {
        WebElement sortButton = waitForElementClickable(sortSummary);
        sortButton.click();
        waitForElementClickable(mostCommentedOption).click();
        wait.until(ExpectedConditions.stalenessOf(sortButton));
        return this;
    }

    public List<WebElement> getCommentResults() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(commentsXPath));
    }
}
