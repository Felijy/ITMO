package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignUpPage extends BasePage {

    private final By createAccountButton = By.xpath("//button[contains(., 'Create account')]");

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCreateAccountButtonVisible() {
        try {
            waitForElementVisible(createAccountButton);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
