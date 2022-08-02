package project.ui.pages.socialnetworks;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import project.ui.BasePage;

public class LinkedInPage extends BasePage {
    private static final By PAGE_VISIBILITY_LOCATOR = By.xpath("//span[contains(text(),'LinkedIn')]");
    private static final String PAGE_NAME = "HiQo Solutions, Inc. | LinkedIn";

    public LinkedInPage() {
        super(PAGE_VISIBILITY_LOCATOR, PAGE_NAME);
        waitForVisibilityLocator(PAGE_VISIBILITY_LOCATOR);
    }

    @Step("The page is opened")
    public boolean isPageOpened() {
        return isElementDisplayed(PAGE_VISIBILITY_LOCATOR);
    }
}