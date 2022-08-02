package project.ui.pages;

import org.openqa.selenium.By;
import project.ui.BasePage;
import project.ui.pages.components.Footer;
import project.ui.pages.components.Header;

public class MainPage extends BasePage {
    private final Header header;
    private final Footer footer;
    private static final By PAGE_VISIBILITY_LOCATOR = By.cssSelector("[alt=\"HiQo Logo\"]");
    private static final String PAGE_NAME = "HiQo Careers";

    public MainPage() {
        super(PAGE_VISIBILITY_LOCATOR, PAGE_NAME);
        waitForLoad();
        waitForVisibilityLocator(PAGE_VISIBILITY_LOCATOR);
        header = new Header();
        footer = new Footer();
    }

    public Header getHeader() {
        return new Header();
    }

    public Footer getFooter() {
        return new Footer();
    }

    public boolean isPageOpened() {
        return isElementDisplayed(PAGE_VISIBILITY_LOCATOR);
    }
}