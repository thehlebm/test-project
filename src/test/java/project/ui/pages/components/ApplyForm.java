package project.ui.pages.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import project.entity.ApplyFormDto;
import project.ui.BasePage;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;

public class ApplyForm extends BasePage {

    private static final By VISIBILITY_LOCATOR = By.cssSelector("applyForm");
    private static final By NAME_INPUT = By.cssSelector("#name");
    private static final By TELEPHONE_INPUT = By.cssSelector("#inputPhone");
    private static final By EMAIL_INPUT = By.cssSelector("#inputEmail");
    private static final By ATTACH_CV_INPUT = By.cssSelector("#addCV");
    private static final By COVER_LETTER_INPUT = By.cssSelector("#description");
    private static final By AGREEMENT_CHECKBOX = By.cssSelector("#agreement");
    private static final By APPLY_BUTTON = By.xpath("//button[@type='submit']");
    private static final By CONFIRMATION_OF_APPLYING_WINDOW = By
            .xpath("//h4[contains(text(),'Thanks for applying!')]");
    private static final By CLOSE_CONFIRMATION_WINDOW_ICON = By
            .xpath("//div[@id='jobApplicationModal']//button[@class='btn-close']");
    private static final By CHECK_SELECTED = By.xpath("//div[contains(text(),'Selected vacancy')]");
    private static final By CLOSE_SELECTED = By.xpath("//i[contains(@class, 'icon-close')]");
    private static final By CHECK_VALIDATION_ERROR_FOR_NAME = By
            .xpath("//input[@id='name']/following-sibling::div[contains(text(),'This field is required')]");
    private static final By CHECK_VALIDATION_ERROR_FOR_PHONE_NUMBER = By
            .xpath("//input[@id='inputPhone']/following-sibling::div[contains(text(),'This field is required')]");
    private static final By CHECK_VALIDATION_ERROR_FOR_EMAIL = By
            .xpath("//input[@id='inputEmail']/following-sibling::div[contains(text(),'This field is required')]");
    private static final By CHECK_INCORRECT_EMAIL = By.xpath("//div[contains(text(),'Email is incorrect')]");
    private static final By CONSENT_BUTTON = By.xpath("//span[contains(text(),'Consent to the processing')]");
    private static final By BACKGROUND = By.xpath("//div[@class='modal-backdrop show']");

    public ApplyForm() {
        super(VISIBILITY_LOCATOR, "Apply form");
    }

    @Step("Input in the 'Name and Surname' field")
    public void inputName(String name) {
        SelenideElement selenideElement = element(NAME_INPUT);
        selenideElement.click();
        selenideElement.setValue(name);
    }

    @Step("Input in the 'Telephone number' field")
    public void inputTelephoneNumber(String number) {
        SelenideElement inputTelephone = element(TELEPHONE_INPUT);
        inputTelephone.click();
        inputTelephone.setValue(number);
    }

    @Step("Input in the 'Email' field")
    public void inputEmail(String email) {
        SelenideElement inputEmail = element(EMAIL_INPUT);
        inputEmail.click();
        inputEmail.setValue(email);
    }

    @Step("Attach in the 'CV/File' field")
    public void attachFile(File file) {
        $(ATTACH_CV_INPUT).uploadFile(file);
    }

    @Step("Input in the 'Cover letter' field")
    public void inputCoverLetter(String letter) {
        SelenideElement inputLetter = element(COVER_LETTER_INPUT);
        inputLetter.setValue(letter);
    }

    @Step("Click on the 'Agreement' checkbox")
    public void clickAgreementCheckbox() {
        SelenideElement agreementCheckbox = element(AGREEMENT_CHECKBOX);
        agreementCheckbox.click();
    }

    @Step("Click an 'Apply' button")
    public void clickApply() {
        SelenideElement applyButton = element(APPLY_BUTTON);
        applyButton.click();
        waitForLoad();
        waitForButtonToWork();
    }

    private void waitForButtonToWork() {
        waitForVisibilityLocator(BACKGROUND);
    }

    @Step("Successful applying confirmation")
    public boolean checkSuccessfulApplyingConfirmation() {
        return isElementDisplayed(CONFIRMATION_OF_APPLYING_WINDOW);
    }

    @Step("Close applying confirmation window")
    public void closeSuccessfulApplyingConfirmationWindow() {
        SelenideElement closeConfirmationWindowButton = element(CLOSE_CONFIRMATION_WINDOW_ICON);
        closeConfirmationWindowButton.click();
    }

    @Step("Check that chosen item from accordion was selected")
    public boolean checkSelectFromAccordion() {
        return isElementDisplayed(CHECK_SELECTED);
    }

    @Step("Unselect item that chosen before")
    public void unselectSelectedFromAccordion() {
        SelenideElement closeSelected = element(CLOSE_SELECTED);
        closeSelected.click();
    }

    @Step("Check an unable 'Apply' button")
    public boolean checkUnableApplyButton() {
        return $(APPLY_BUTTON).is(Condition.disabled);
    }

    @Step("Check successful applying confirmation window for not displaying")
    public boolean checkUnsuccessfulApplying() {
        return $(CONFIRMATION_OF_APPLYING_WINDOW).is(Condition.hidden);
    }

    @Step("Create applying by inserting data into fields")
    public void createApplying(ApplyFormDto applyFormDto) {
        if (applyFormDto.getName() != null) {
            inputName(applyFormDto.getName());
        }
        if (applyFormDto.getPhoneNumber() != null) {
            inputTelephoneNumber(applyFormDto.getPhoneNumber());
        }
        if (applyFormDto.getEmail() != null) {
            inputEmail(applyFormDto.getEmail());
        }
        if (applyFormDto.getFile() != null) {
            attachFile(applyFormDto.getFile());
        }
        if (applyFormDto.getLetter() != null) {
            inputCoverLetter(applyFormDto.getLetter());
        }
    }

    @Step("Fill in Application form")
    public void fillInApplicationFormAndApply(ApplyFormDto applyFormDto) {
        createApplying(applyFormDto);
        clickAgreementCheckbox();
        clickApply();
    }

    @Step("Check emptiness for all required fields")
    public boolean checkRequiredFieldError(ApplyFormDto applyForm) {
        if (applyForm.getName().isEmpty()) {
            return isElementDisplayed(CHECK_VALIDATION_ERROR_FOR_NAME);
        } else if (applyForm.getEmail().isEmpty()) {
            return isElementDisplayed(CHECK_VALIDATION_ERROR_FOR_EMAIL);
        } else if (applyForm.getPhoneNumber().isEmpty()) {
            return isElementDisplayed(CHECK_VALIDATION_ERROR_FOR_PHONE_NUMBER);
        }
        return false;
    }

    @Step("Check validation error for email field")
    public boolean checkEmailValidationError() {
        return isElementDisplayed(CHECK_INCORRECT_EMAIL);
    }

    @Step("Check validation error for phone number field")
    public boolean checkPhoneNumberValidationError() {
        return isElementDisplayed(CHECK_VALIDATION_ERROR_FOR_PHONE_NUMBER);
    }

    @Step("Click on 'Consent to the processing of personal data' button next to checkbox of agreement")
    public void clickOnConsentButton() {
        SelenideElement consentButton = element(CONSENT_BUTTON);
        consentButton.click();
    }
}