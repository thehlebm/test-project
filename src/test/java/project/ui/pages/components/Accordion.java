package project.ui.pages.components;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import project.ui.BasePage;

public class Accordion extends BasePage {

    private static final By VISIBILITY_LOCATOR = By.xpath("//app-vacancies-list[contains(@listtitle,'Navigation.Internship')]");
    private static final By ACCORDION = By.xpath("//div[@id='vacancies-accordion']//div[@class='accordion-item']");
    private static final By PLUS_BUTTON = By
            .xpath("//button[contains(@class, 'accordion-button') and contains(@class,'collapsed')]");
    private static final By MINUS_BUTTON = By
            .xpath("//button[contains(@class, 'accordion-button') and not(contains(@class,'collapsed'))]");
    private static final By APPLY_CHOSEN_BUTTON = By
            .xpath("//div[@id='vacancies-accordion']//button[contains(@class, 'btn-primary')]");
    private static final By COPY_LINK_BUTTON = By.xpath("//a[contains(@class, 'copy-link')]");
    private static final By LINK_COPIED_BUTTON = By.xpath("//a[contains(text(),'link copied')]");
    private static final String CLICK_CHECKBOX_PATTERN = "//label[contains(text(),'%s')]";
    private static final By DROPDOWN_BUTTON = By.xpath("//button[contains(@class,'dropdown-btn')]");
    private static final String CHECK_DROPDOWN_BUTTON_PATTERN =
            "//button[contains(@class,'dropdown-btn') and contains(text(),'%s')]";

    public Accordion() {
        super(VISIBILITY_LOCATOR, "Accordion");
    }

    @Step("Chose from accordion")
    public void choseTheFirstFromAccordion() {
        SelenideElement firstItemFromAccordion = element(ACCORDION);
        firstItemFromAccordion.click();
    }

    @Step("Check plus button switching to minus button")
    public boolean checkToMinusButtonSwitching() {
        return isElementDisplayed(MINUS_BUTTON);
    }

    @Step("Apply for the chosen from accordion")
    public void applyChosenFromAccordion() {
        SelenideElement accordionApplyButton = element(APPLY_CHOSEN_BUTTON);
        accordionApplyButton.click();
    }

    @Step("Copy the link of chosen item")
    public void clickOnCopyLinkButton() {
        SelenideElement copyLinkButton = element(COPY_LINK_BUTTON);
        copyLinkButton.click();
    }

    @Step("Check 'Link Copy' button is updated to 'Link Copied'")
    public boolean checkLinkCopiedButton() {
        return isElementDisplayed(LINK_COPIED_BUTTON);
    }

    @Step("Click checkbox for country {country}")
    public void clickOnCheckbox(String country) {
        By xPath = By.xpath(String.format(CLICK_CHECKBOX_PATTERN, country));
        SelenideElement countryCheckbox = element(xPath);
        countryCheckbox.click();
    }

    @Step("Check that selected of country checkbox name is displayed in the field")
    public boolean checkDropdownButton(String countryName) {
        By xPath = By.xpath(String.format(CHECK_DROPDOWN_BUTTON_PATTERN, countryName));
        return isElementDisplayed(xPath);
    }

    @Step("Click on dropdown button and wait for expand it")
    public void clickDropdownButton() {
        SelenideElement dropdownButton = element(DROPDOWN_BUTTON);
        dropdownButton.click();
    }
}