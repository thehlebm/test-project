package project.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import project.dataproviders.*;
import project.entity.ApplyFormDto;
import project.ui.pages.InternshipPage;
import project.ui.pages.components.Accordion;
import project.ui.pages.components.ApplyForm;
import project.ui.pages.components.Footer;
import project.ui.pages.components.Header;
import project.data.UserCreator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static project.data.Constants.*;

public class InternshipPageTest extends BaseTest {

    @BeforeEach
    public void openInternshipPage() {
        Header menu = new Header();
        menu.clickInternshipButton();
    }

    @Test
    @DisplayName("Open Internship page")
    public void checkInternshipPage() {
        InternshipPage internshipPage = new InternshipPage();
        assertTrue(internshipPage.isPageOpened(),
                "Internship page of HiQo Career site is not opened by the correct link");
    }

    @Test
    @DisplayName("Apply for the internship")
    public void applyForTheInternship() {
        InternshipPage internshipPage = new InternshipPage();
        ApplyForm applyForm = internshipPage.getApplyForm();
        ApplyFormDto applyFormDto = UserCreator.createDefaultUser();
        applyForm.fillInApplicationFormAndApply(applyFormDto);
        assertTrue(applyForm.checkSuccessfulApplyingConfirmation(), "There is no window about successful applying");
        applyForm.closeSuccessfulApplyingConfirmationWindow();
    }

    @Test
    @DisplayName("Apply for the chosen internship")
    public void applyForTheChosenInternship() {
        InternshipPage internshipPage = new InternshipPage();
        Accordion internship = internshipPage.getAccordion();
        ApplyForm applyForm = internshipPage.getApplyForm();
        internship.choseTheFirstFromAccordion();
        assertTrue(internship.checkToMinusButtonSwitching(), "There is no minus button");
        internship.applyChosenFromAccordion();
        assertTrue(applyForm.checkSelectFromAccordion(), "There is no selected vacancy at the top of applying block");
        ApplyFormDto applyFormDto = UserCreator.createDefaultUser();
        applyForm.fillInApplicationFormAndApply(applyFormDto);
        assertTrue(applyForm.checkSuccessfulApplyingConfirmation(), "There is no window about successful applying");
        applyForm.closeSuccessfulApplyingConfirmationWindow();
    }

    @Test
    @DisplayName("Apply for any other internship than chosen before")
    public void applyForOtherInternshipThanChosenBefore() {
        InternshipPage internshipPage = new InternshipPage();
        Accordion internship = internshipPage.getAccordion();
        ApplyForm applyForm = internshipPage.getApplyForm();
        internship.choseTheFirstFromAccordion();
        assertTrue(internship.checkToMinusButtonSwitching(), "There is no the minus button");
        internship.applyChosenFromAccordion();
        assertTrue(applyForm.checkSelectFromAccordion(), "There is no selected vacancy at the top of applying block");
        applyForm.unselectSelectedFromAccordion();
        assertFalse(applyForm.checkSelectFromAccordion(), "Selected vacancy is displayed");
        ApplyFormDto applyFormDto = UserCreator.createDefaultUser();
        applyForm.fillInApplicationFormAndApply(applyFormDto);
        assertTrue(applyForm.checkSuccessfulApplyingConfirmation(), "There is no window about successful applying");
        applyForm.closeSuccessfulApplyingConfirmationWindow();
    }

    @Test
    @DisplayName("Check 'Copy Link' button for switching to 'Link Copied'")
    public void copyLinkOfInternship() {
        InternshipPage internshipPage = new InternshipPage();
        Accordion internship = internshipPage.getAccordion();
        internship.choseTheFirstFromAccordion();
        internship.clickOnCopyLinkButton();
        assertTrue(internship.checkLinkCopiedButton(), "'Link Copy' button is updated to 'Link Copied' button");
    }

    @Test
    @DisplayName("Follow the copied link of internship and apply for the chosen")
    public void copyLinkPasteAndApplyForAnInternship() {
        InternshipPage internshipPage = new InternshipPage();
        ApplyForm applyForm = internshipPage.getApplyForm();
        Accordion internship = internshipPage.getAccordion();
        internship.choseTheFirstFromAccordion();
        internship.clickOnCopyLinkButton();
        internshipPage.createAndSwitchToNewTab();
        internshipPage.openLinkFromClipboard();
        ApplyFormDto applyFormDto = UserCreator.createDefaultUser();
        applyForm.fillInApplicationFormAndApply(applyFormDto);
        assertTrue(applyForm.checkSuccessfulApplyingConfirmation(), "There is no window about successful applying");
        applyForm.closeSuccessfulApplyingConfirmationWindow();
        internshipPage.closeCurrentTab();
        internshipPage.switchToDefaultTab();
    }

    @Test
    @DisplayName("Do not apply for the chosen internship after following the copied link")
    public void copyLinkPasteButDoNotApply() {
        InternshipPage internshipPage = new InternshipPage();
        Accordion internship = internshipPage.getAccordion();
        Header menu = internshipPage.getHeader();
        internship.choseTheFirstFromAccordion();
        internship.clickOnCopyLinkButton();
        internshipPage.createAndSwitchToNewTab();
        internshipPage.openLinkFromClipboard();
        menu.clickInternshipButton();
        assertTrue(internshipPage.isPageOpened(),
                "Internship page of HiQo Career site is not opened by the correct link");
        internshipPage.closeCurrentTab();
        internshipPage.switchToDefaultTab();
    }

    @ParameterizedTest
            (name = "Check possibility of applying with empty data in required fields for next arguments: {arguments}")
    @ArgumentsSource(CsvDataApplyProviderWithEmptyRequiredFields.class)
    public void checkApplyingWithEmptyDataInRequiredFields(ApplyFormDto applyFormDto) {
        InternshipPage internshipPage = new InternshipPage();
        ApplyForm applyForm = internshipPage.getApplyForm();
        applyForm.createApplying(applyFormDto);
        assertTrue(applyForm.checkRequiredFieldError(applyFormDto),
                "There is no validation error about empty required field");
        applyForm.clickAgreementCheckbox();
        assertTrue(applyForm.checkUnableApplyButton(), "'Apply' button is able and clickable");
        assertTrue(applyForm.checkUnsuccessfulApplying(),
                "User can apply with empty data in required field. Modal window of successful applying is appeared");
    }

    @ParameterizedTest(name = "Check possibility of applying with attached file in different formats: {arguments}")
    @ArgumentsSource(JsonDataApplyProviderFileFormats.class)
    public void applyForInternshipWithDifferentFileFormats(ApplyFormDto applyFormDto) {
        InternshipPage internshipPage = new InternshipPage();
        ApplyForm applyForm = internshipPage.getApplyForm();
        applyForm.fillInApplicationFormAndApply(applyFormDto);
        assertTrue(applyForm.checkSuccessfulApplyingConfirmation(), "There is no window about successful applying");
        applyForm.closeSuccessfulApplyingConfirmationWindow();
    }

    @Test
    @DisplayName("Check possibility for applying without agreement of the Consent to the processing of personal data")
    public void applyWithoutAgreement() {
        InternshipPage internshipPage = new InternshipPage();
        ApplyFormDto applyFormDto = UserCreator.createDefaultUser();
        ApplyForm applyForm = internshipPage.getApplyForm();
        applyForm.createApplying(applyFormDto);
        assertTrue(applyForm.checkUnableApplyButton(), "'Apply' button is able and clickable");
        assertTrue(applyForm.checkUnsuccessfulApplying(),
                "User can apply with empty data in required field. Modal window of successful applying is appeared");
    }

    @Test
    @DisplayName("Check displaying country's name in dropdown block")
    public void checkDropdownButtonForDisplayingSelectedCheckboxes() {
        InternshipPage internshipPage = new InternshipPage();
        Accordion internship = internshipPage.getAccordion();
        internship.clickDropdownButton();
        internship.clickOnCheckbox(GLOBAL);
        internship.clickOnCheckbox(POLAND);
        assertTrue(internship.checkDropdownButton(POLAND), "There is no displayed country name in the field");
        internship.clickOnCheckbox(POLAND);
        internship.clickOnCheckbox(BELARUS);
        assertTrue(internship.checkDropdownButton(BELARUS), "There is no displayed country name in the field");
        internship.clickOnCheckbox(BELARUS);
        internship.clickOnCheckbox(RUSSIA);
        assertTrue(internship.checkDropdownButton(RUSSIA), "There is no displayed country name in the field");
        internship.clickOnCheckbox(RUSSIA);
        internship.clickOnCheckbox(GLOBAL);
        assertTrue(internship.checkDropdownButton(GLOBAL), "There is no displayed country name in the field");
        internship.clickOnCheckbox(GLOBAL);
        internship.clickOnCheckbox(POLAND);
        internship.clickOnCheckbox(BELARUS);
        assertTrue(internship.checkDropdownButton(POLAND + ", " + BELARUS),
                "There is no displayed country name in the field");
        internship.clickOnCheckbox(BELARUS);
        internship.clickOnCheckbox(RUSSIA);
        assertTrue(internship.checkDropdownButton(POLAND + ", " + RUSSIA),
                "There is no displayed country name in the field");
        internship.clickOnCheckbox(POLAND);
        internship.clickOnCheckbox(BELARUS);
        assertTrue(internship.checkDropdownButton(BELARUS + ", " + RUSSIA),
                "There is no displayed country name in the field");
        internship.clickOnCheckbox(POLAND);
        assertTrue(internship.checkDropdownButton(GLOBAL), "There is no displayed country name in the field");
    }

    @ParameterizedTest(name = "Check possibility to apply without cover letter")
    @ArgumentsSource(CsvDataApplyProviderWithEmptyLetterField.class)
    public void applyWithoutCoverLetter(ApplyFormDto applyFormDto) {
        InternshipPage internshipPage = new InternshipPage();
        ApplyForm applyForm = internshipPage.getApplyForm();
        applyForm.fillInApplicationFormAndApply(applyFormDto);
        assertTrue(applyForm.checkSuccessfulApplyingConfirmation(), "There is no window about successful applying");
        applyForm.closeSuccessfulApplyingConfirmationWindow();
    }

    @ParameterizedTest(name = "Check possibility to apply without attached file")
    @ArgumentsSource(CsvDataApplyProviderWithEmptyFileField.class)
    public void applyWithoutAttachedFile(ApplyFormDto applyFormDto) {
        InternshipPage internshipPage = new InternshipPage();
        ApplyForm applyForm = internshipPage.getApplyForm();
        applyForm.fillInApplicationFormAndApply(applyFormDto);
        assertTrue(applyForm.checkSuccessfulApplyingConfirmation(), "There is no window about successful applying");
        applyForm.closeSuccessfulApplyingConfirmationWindow();
    }

    @ParameterizedTest(name = "Check possibility to apply with invalid email")
    @ArgumentsSource(JsonDataApplyProviderWithInvalidEmail.class)
    public void applyWithInvalidEmail(ApplyFormDto applyFormDto) {
        InternshipPage internshipPage = new InternshipPage();
        ApplyForm applyForm = internshipPage.getApplyForm();
        applyForm.createApplying(applyFormDto);
        assertTrue(applyForm.checkEmailValidationError(), "Email is correct");
        applyForm.clickAgreementCheckbox();
        assertTrue(applyForm.checkUnableApplyButton(), "'Apply' button is able and clickable");
        assertTrue(applyForm.checkUnsuccessfulApplying(),
                "User can apply with empty data in required field. Modal window of successful applying is appeared");
    }

    @ParameterizedTest(name = "Check possibility to apply with invalid phone number")
    @ArgumentsSource(JsonDataApplyProviderWithInvalidPhoneNumber.class)
    public void applyWithInvalidPhoneNumber(ApplyFormDto applyFormDto) {
        InternshipPage internshipPage = new InternshipPage();
        ApplyForm applyForm = internshipPage.getApplyForm();
        applyForm.createApplying(applyFormDto);
        assertTrue(applyForm.checkPhoneNumberValidationError(), "There is no validation error about email field");
        applyForm.clickAgreementCheckbox();
        assertTrue(applyForm.checkUnableApplyButton(), "'Apply' button is able and clickable");
        assertTrue(applyForm.checkUnsuccessfulApplying(),
                "User can apply with empty data in required field. Modal window of successful applying is appeared");
    }

    @Test
    @DisplayName("Check 'Privacy Policy' windows for appearing")
    public void checkPrivacyPolicyWindows() {
        InternshipPage internshipPage = new InternshipPage();
        ApplyForm applyForm = internshipPage.getApplyForm();
        Footer footer = internshipPage.getFooter();
        applyForm.clickOnConsentButton();
        assertTrue(footer.checkPrivacyPolicyWindow(), "Window with 'Privacy Policy' did not appeared");
        footer.closePrivacyPolicyWindow();
        footer.clickOnPrivacyPolicyButton();
        assertTrue(footer.checkPrivacyPolicyWindow(), "Window with 'Privacy Policy' did not appeared");
        footer.closePrivacyPolicyWindow();
    }

    @Test
    @DisplayName("Check that internship is expandable and 'Plus' button turning into 'Minus' button")
    public void checkPlusToMinusButton() {
        InternshipPage internshipPage = new InternshipPage();
        Accordion internship = internshipPage.getAccordion();
        internship.choseTheFirstFromAccordion();
        assertTrue(internship.checkToMinusButtonSwitching(), "'Plus' button not switching to 'Minus' button");
    }
}