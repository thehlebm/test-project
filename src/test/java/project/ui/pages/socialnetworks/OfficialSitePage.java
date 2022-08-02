package project.ui.pages.socialnetworks;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import project.ui.BasePage;

public class OfficialSitePage extends BasePage {
    private static final By PAGE_VISIBILITY_LOCATOR = By.xpath("//img[@alt='hiqo logo']");
    private static final String PAGE_NAME = "HiQo Solutions";

    public OfficialSitePage() {
        super(PAGE_VISIBILITY_LOCATOR, PAGE_NAME);
        waitForVisibilityLocator(PAGE_VISIBILITY_LOCATOR);
    }

    @Step("The page is opened")
    public boolean isPageOpened() {
        return isElementDisplayed(PAGE_VISIBILITY_LOCATOR);
    }
}