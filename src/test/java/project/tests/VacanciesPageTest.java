package project.tests;

import project.data.UserCreator;
import project.dataproviders.*;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import project.entity.ApplyFormDto;
import project.ui.pages.MainPage;
import project.ui.pages.VacanciesPage;
import project.ui.pages.components.Accordion;
import project.ui.pages.components.ApplyForm;
import project.ui.pages.components.Footer;
import project.ui.pages.components.Header;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VacanciesPageTest extends BaseTest {

    @BeforeEach
    public void openVacanciesPage() {
        Header menu = new Header();
        menu.clickVacanciesButton();
    }

    @Test
    @DisplayName("Open Vacancies page")
    public void checkVacanciesPage() {
        VacanciesPage vacanciesPage = new VacanciesPage();
        assertTrue(vacanciesPage.isPageOpened(),
                "Vacancies page of HiQo Career site is not opened by the correct link");
    }

    @Test
    @DisplayName("Apply for the position")
    public void applyForThePosition() {
        VacanciesPage vacanciesPage = new VacanciesPage();
        ApplyForm applyForm = vacanciesPage.getApplyForm();
        ApplyFormDto applyFormDto = UserCreator.createDefaultUser();
        applyForm.fillInApplicationFormAndApply(applyFormDto);
        assertTrue(applyForm.checkSuccessfulApplyingConfirmation(), "There is no window about successful applying");
        applyForm.closeSuccessfulApplyingConfirmationWindow();
    }

    @Test
    @DisplayName("Apply for the chosen position")
    public void applyForTheChosenPosition() {
        VacanciesPage vacanciesPage = new VacanciesPage();
        Accordion vacancies = vacanciesPage.getAccordion();
        vacancies.choseTheFirstFromAccordion();
        assertTrue(vacancies.checkToMinusButtonSwitching(), "There is no minus button");
        vacancies.applyChosenFromAccordion();
        ApplyForm applyForm = vacanciesPage.getApplyForm();
        assertTrue(applyForm.checkSelectFromAccordion(), "There is no selected vacancy at the top of applying block");
        ApplyFormDto applyFormDto = UserCreator.createDefaultUser();
        applyForm.fillInApplicationFormAndApply(applyFormDto);
        assertTrue(applyForm.checkSuccessfulApplyingConfirmation(), "There is no window about successful applying");
        applyForm.closeSuccessfulApplyingConfirmationWindow();
    }

    @Test
    @DisplayName("Apply for any other position than chosen before")
    public void applyForOtherPositionThanChosenBefore() {
        VacanciesPage vacanciesPage = new VacanciesPage();
        Accordion vacancies = vacanciesPage.getAccordion();
        vacancies.choseTheFirstFromAccordion();
        assertTrue(vacancies.checkToMinusButtonSwitching(), "There is no the minus button");
        vacancies.applyChosenFromAccordion();
        ApplyForm applyForm = vacanciesPage.getApplyForm();
        assertTrue(applyForm.checkSelectFromAccordion(), "There is no selected vacancy at the top of applying block");
        applyForm.unselectSelectedFromAccordion();
        assertFalse(applyForm.checkSelectFromAccordion(), "Selected vacancy is displayed");
        ApplyFormDto applyFormDto = UserCreator.createDefaultUser();
        applyForm.fillInApplicationFormAndApply(applyFormDto);
        assertTrue(applyForm.checkSuccessfulApplyingConfirmation(), "There is no window about successful applying");
        applyForm.closeSuccessfulApplyingConfirmationWindow();
    }

    @Test
    @DisplayName("Copy the link of vacancy")
    public void copyLinkOfVacancy() {
        VacanciesPage vacanciesPage = new VacanciesPage();
        Accordion vacancies = vacanciesPage.getAccordion();
        vacancies.choseTheFirstFromAccordion();
        vacancies.clickOnCopyLinkButton();
        assertTrue(vacancies.checkLinkCopiedButton(), "'Link Copy' button is updated to 'Link Copied' button");
    }

    @Test
    @DisplayName("Follow the copied link of vacancy and apply for the chosen position")
    public void pasteLinkOfVacancy() {
        MainPage mainPage = new MainPage();
        VacanciesPage vacanciesPage = new VacanciesPage();
        Accordion vacancies = vacanciesPage.getAccordion();
        vacancies.choseTheFirstFromAccordion();
        vacancies.clickOnCopyLinkButton();
        mainPage.createAndSwitchToNewTab();
        mainPage.openLinkFromClipboard();
        ApplyForm applyForm = vacanciesPage.getApplyForm();
        ApplyFormDto applyFormDto = UserCreator.createDefaultUser();
        applyForm.fillInApplicationFormAndApply(applyFormDto);
        assertTrue(applyForm.checkSuccessfulApplyingConfirmation(), "There is no window about successful applying");
        applyForm.closeSuccessfulApplyingConfirmationWindow();
        mainPage.closeCurrentTab();
        mainPage.switchToDefaultTab();
    }

    @Test
    @DisplayName("Do not apply for the chosen position after following the copied link")
    public void pasteLinkOfVacancyButDoNotApply() {
        MainPage mainPage = new MainPage();
        VacanciesPage vacanciesPage = new VacanciesPage();
        Accordion vacancies = vacanciesPage.getAccordion();
        Header menu = vacanciesPage.getHeader();
        vacancies.choseTheFirstFromAccordion();
        vacancies.clickOnCopyLinkButton();
        mainPage.createAndSwitchToNewTab();
        vacanciesPage.openLinkFromClipboard();
        menu.clickVacanciesButton();
        assertTrue(vacanciesPage.isPageOpened(), "Vacancies page of HiQo Career site is not opened by the correct link");
        mainPage.closeCurrentTab();
        mainPage.switchToDefaultTab();
    }

    @ParameterizedTest(name = "Check possibility of applying with empty data in required fields for next arguments: {arguments}")
    @ArgumentsSource(CsvDataApplyProviderWithEmptyRequiredFields.class)
    public void checkApplyingWithEmptyDataInRequiredFields(ApplyFormDto applyFormDto) {
        VacanciesPage vacanciesPage = new VacanciesPage();
        ApplyForm applyForm = vacanciesPage.getApplyForm();
        applyForm.createApplying(applyFormDto);
        assertTrue(applyForm.checkRequiredFieldError(applyFormDto), "There is no validation error about empty required field");
        applyForm.clickAgreementCheckbox();
        assertTrue(applyForm.checkUnableApplyButton(), "'Apply' button is able and clickable");
        assertTrue(applyForm.checkUnsuccessfulApplying(), "User can apply with empty data in required field. Modal window of successful applying is appeared");
    }

    @ParameterizedTest(name = "Check possibility of applying with attached file in different formats: {arguments}")
    @ArgumentsSource(JsonDataApplyProviderFileFormats.class)
    public void applyForVacancyWithDifferentFileFormats(ApplyFormDto applyFormDto) {
        VacanciesPage vacanciesPage = new VacanciesPage();
        ApplyForm applyForm = vacanciesPage.getApplyForm();
        applyForm.fillInApplicationFormAndApply(applyFormDto);
        assertTrue(applyForm.checkSuccessfulApplyingConfirmation(), "There is no window about successful applying");
        applyForm.closeSuccessfulApplyingConfirmationWindow();
    }

    @Test
    @DisplayName("Check possibility for applying without agreement of the Consent to the processing of personal data")
    public void applyWithoutAgreement() {
        VacanciesPage vacanciesPage = new VacanciesPage();
        ApplyFormDto applyFormDto = UserCreator.createDefaultUser();
        ApplyForm applyForm = vacanciesPage.getApplyForm();
        applyForm.createApplying(applyFormDto);
        assertTrue(applyForm.checkUnableApplyButton(), "'Apply' button is able and clickable");
        assertTrue(applyForm.checkUnsuccessfulApplying(), "User can apply with empty data in required field. Modal window of successful applying is appeared");
    }

    @Test
    @DisplayName("Check displaying country's name in dropdown block")
    public void checkDropdownButtonForDisplayingSelectedCheckboxes() {
        String poland = "Poland";
        String belarus = "Belarus";
        String russia = "Russia";
        String global = "Global";
        VacanciesPage vacanciesPage = new VacanciesPage();
        Accordion vacancies = vacanciesPage.getAccordion();
        vacancies.clickDropdownButton();
        vacancies.clickOnCheckbox(global);
        vacancies.clickOnCheckbox(poland);
        assertTrue(vacancies.checkDropdownButton(poland), "There is no displayed country name in the field");
        vacancies.clickOnCheckbox(poland);
        vacancies.clickOnCheckbox(belarus);
        assertTrue(vacancies.checkDropdownButton(belarus), "There is no displayed country name in the field");
        vacancies.clickOnCheckbox(belarus);
        vacancies.clickOnCheckbox(russia);
        assertTrue(vacancies.checkDropdownButton(russia), "There is no displayed country name in the field");
        vacancies.clickOnCheckbox(russia);
        vacancies.clickOnCheckbox(global);
        assertTrue(vacancies.checkDropdownButton(global), "There is no displayed country name in the field");
        vacancies.clickOnCheckbox(global);
        vacancies.clickOnCheckbox(poland);
        vacancies.clickOnCheckbox(belarus);
        assertTrue(vacancies.checkDropdownButton(poland + ", " + belarus), "There is no displayed country name in the field");
        vacancies.clickOnCheckbox(belarus);
        vacancies.clickOnCheckbox(russia);
        assertTrue(vacancies.checkDropdownButton(poland + ", " + russia), "There is no displayed country name in the field");
        vacancies.clickOnCheckbox(poland);
        vacancies.clickOnCheckbox(belarus);
        assertTrue(vacancies.checkDropdownButton(belarus + ", " + russia), "There is no displayed country name in the field");
        vacancies.clickOnCheckbox(poland);
        assertTrue(vacancies.checkDropdownButton(global), "There is no displayed country name in the field");
    }

    @ParameterizedTest(name = "Check possibility to apply without cover letter")
    @ArgumentsSource(CsvDataApplyProviderWithEmptyLetterField.class)
    public void applyWithoutCoverLetter(ApplyFormDto applyFormDto) {
        VacanciesPage vacanciesPage = new VacanciesPage();
        ApplyForm applyForm = vacanciesPage.getApplyForm();
        applyForm.fillInApplicationFormAndApply(applyFormDto);
        assertTrue(applyForm.checkSuccessfulApplyingConfirmation(), "There is no window about successful applying");
        applyForm.closeSuccessfulApplyingConfirmationWindow();
    }

    @ParameterizedTest(name = "Check possibility to apply without attached file")
    @ArgumentsSource(CsvDataApplyProviderWithEmptyFileField.class)
    public void applyWithoutAttachedFile(ApplyFormDto applyFormDto) {
        VacanciesPage vacanciesPage = new VacanciesPage();
        ApplyForm applyForm = vacanciesPage.getApplyForm();
        applyForm.fillInApplicationFormAndApply(applyFormDto);
        assertTrue(applyForm.checkSuccessfulApplyingConfirmation(), "There is no window about successful applying");
        applyForm.closeSuccessfulApplyingConfirmationWindow();
    }

    @ParameterizedTest(name = "Check possibility to apply with invalid email")
    @ArgumentsSource(JsonDataApplyProviderWithInvalidEmail.class)
    public void applyWithInvalidEmail(ApplyFormDto applyFormDto) {
        VacanciesPage vacanciesPage = new VacanciesPage();
        ApplyForm applyForm = vacanciesPage.getApplyForm();
        applyForm.createApplying(applyFormDto);
        assertTrue(applyForm.checkEmailValidationError(), "Email is correct");
        applyForm.clickAgreementCheckbox();
        assertTrue(applyForm.checkUnableApplyButton(), "'Apply' button is able and clickable");
        assertTrue(applyForm.checkUnsuccessfulApplying(), "User can apply with empty data in required field. Modal window of successful applying is appeared");
    }

    @ParameterizedTest(name = "Check possibility to apply with invalid phone number")
    @ArgumentsSource(JsonDataApplyProviderWithInvalidPhoneNumber.class)
    public void applyWithInvalidPhoneNumber(ApplyFormDto applyFormDto) {
        VacanciesPage vacanciesPage = new VacanciesPage();
        ApplyForm applyForm = vacanciesPage.getApplyForm();
        applyForm.createApplying(applyFormDto);
        assertTrue(applyForm.checkPhoneNumberValidationError(), "There is no validation error about email field");
        applyForm.clickAgreementCheckbox();
        assertTrue(applyForm.checkUnableApplyButton(), "'Apply' button is able and clickable");
        assertTrue(applyForm.checkUnsuccessfulApplying(), "User can apply with empty data in required field. Modal window of successful applying is appeared");
    }

    @Test
    @DisplayName("Check 'Privacy Policy' windows for appearing")
    public void checkPrivacyPolicyWindows() {
        VacanciesPage vacanciesPage = new VacanciesPage();
        ApplyForm applyForm = vacanciesPage.getApplyForm();
        Footer footer = vacanciesPage.getFooter();
        applyForm.clickOnConsentButton();
        assertTrue(footer.checkPrivacyPolicyWindow(), "Window with 'Privacy Policy' did not appeared");
        footer.closePrivacyPolicyWindow();
        footer.clickOnPrivacyPolicyButton();
        assertTrue(footer.checkPrivacyPolicyWindow(), "Window with 'Privacy Policy' did not appeared");
        footer.closePrivacyPolicyWindow();
    }

    @Test
    @DisplayName("Check that vacancy is expandable and 'Plus' button turning into 'Minus' button")
    public void checkPlusToMinusButton() {
        VacanciesPage vacanciesPage = new VacanciesPage();
        Accordion vacancies = vacanciesPage.getAccordion();
        vacancies.choseTheFirstFromAccordion();
        assertTrue(vacancies.checkToMinusButtonSwitching(), "'Plus' button not switching to 'Minus' button");
    }
}