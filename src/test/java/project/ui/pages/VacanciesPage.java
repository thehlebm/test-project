package project.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import project.ui.pages.components.Accordion;
import project.ui.pages.components.ApplyForm;

public class VacanciesPage extends MainPage {

    private final ApplyForm applyForm;
    private final Accordion accordion;
    private static final By PAGE_VISIBILITY_LOCATOR = By
            .xpath("//h1[contains(text(), 'Work hard dream big')]");
    private static final String PAGE_NAME = "HiQo Careers";

    public VacanciesPage() {
        super();
        waitForLoad();
        waitForVisibilityLocator(PAGE_VISIBILITY_LOCATOR);
        accordion = new Accordion();
        applyForm = new ApplyForm();
    }

    public ApplyForm getApplyForm() {
        return new ApplyForm();
    }

    public Accordion getAccordion() {
        return new Accordion();
    }
    @Step("The page is opened")
    public boolean isPageOpened() {
        return isElementDisplayed(PAGE_VISIBILITY_LOCATOR);
    }
}