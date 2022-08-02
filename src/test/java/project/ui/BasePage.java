package project.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import framework.config.SolutionManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

/**
 * \brief It's a base page for web pages and complex web elements
 * <p>
 * The class defines the structure for inherited classes and provides a common logic
 */
public abstract class BasePage implements Alerts, PageLoadWaiters {

    public final static String PREFIX_FOR_PAGE_SCREENSHOT_FILE = "page_";

    private final String pageName;
    private final By pageVisibilityLocator;

    public BasePage(By pageVisibilityLocator, String pageName) {
        this.pageVisibilityLocator = pageVisibilityLocator;
        this.pageName = pageName;
    }

    public String getName() {
        return pageName;
    }

    public String getLocator() {
        return pageVisibilityLocator.toString();
    }

    public SelenideElement element(final By locator) {
        return $(locator)
                .scrollIntoView(true)
                .shouldBe(Condition.visible, SolutionManager.DURATION_TIMEOUT);
    }

    public SelenideElement element(final String cssLocator) {
        return element(By.cssSelector(cssLocator));
    }

    public void clickWhenReady(final By locator) {
        SelenideElement clickableItem = element(locator);
        clickWhenReady(clickableItem);
    }

    public void clickWhenReady(final SelenideElement button) {
        button.shouldBe(Condition.enabled).click();
    }

    public boolean isElementDisplayed(final By locator) {
        ElementsCollection elements = $$(locator);
        if (elements.size() > 0) {
            return $(locator).isDisplayed();
        }
        return false;
    }

    public void hoverOver(final By locator) {
        SelenideElement foundElement = element(locator);
        foundElement.hover();
    }

    public void waitForVisibilityLocator(final By locator) {
        waitForVisibilityLocator(locator, SolutionManager.TIMEOUT);
    }

    public void waitForVisibilityLocator(final By locator, final long timeout) throws TimeoutException {

        new WebDriverWait(getWebDriver(), Duration.ofMillis(timeout))
                .until(condition -> $(locator).isDisplayed());
    }

    public void waitForInvisibilityLocator(final By locator, final long timeout) throws TimeoutException {

        new WebDriverWait(getWebDriver(), Duration.ofMillis(timeout))
                .until(condition -> !$(locator).isDisplayed());
    }

    public void waitForInvisibilityLocator(final By locator) {
        waitForInvisibilityLocator(locator, SolutionManager.TIMEOUT);
    }

    public void createAndSwitchToNewTab() {
        executeJavaScript("window.open()");
        switchTo().window(1);
    }

    public void switchToOpenedTab() {
        switchTo().window(1);
        waitForLoad();
    }

    public void openLinkFromClipboard() {
        open(Selenide.clipboard().getText());
        waitForLoad();
    }

    public void closeCurrentTab() {
        executeJavaScript("window.close()");
    }

    public void switchToDefaultTab() {
        switchTo().window(0);
    }
}