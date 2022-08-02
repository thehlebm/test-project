package project.ui.pages.components;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import project.ui.BasePage;
import project.ui.pages.InternshipPage;
import project.ui.pages.VacanciesPage;

public class Header extends BasePage {
    private static final By VISIBILITY_LOCATOR = By.xpath("//header[contains(@class,'navbar')]");
    private static final By ABOUT_BUTTON = By.xpath("//a[text()=' About ']");
    private static final By VACANCIES_BUTTON = By.xpath("//a[contains(text(),'Vacancies')]");
    private static final By INTERNSHIP_BUTTON = By.xpath("//a[contains(text(),'Internship')]");
    private static final By CONTACTS_BUTTON = By.xpath("//a[text()=' Contacts ']");

    public Header() {
        super(VISIBILITY_LOCATOR, "Header with menu");
    }

    @Step("Click Vacancies button")
    public VacanciesPage clickVacanciesButton() {
        SelenideElement vacanciesButton = element(VACANCIES_BUTTON);
        vacanciesButton.click();
        waitForLoad();
        return new VacanciesPage();
    }

    @Step("Click Internship button")
    public InternshipPage clickInternshipButton() {
        SelenideElement internshipButton = element(INTERNSHIP_BUTTON);
        internshipButton.click();
        waitForLoad();
        return new InternshipPage();
    }
}
