package project.ui.pages.components;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import project.ui.BasePage;

public class Footer extends BasePage {

    private static final By VISIBILITY_LOCATOR = By.xpath("//footer[contains(@class,'footer')]");
    private static final By FACEBOOK_BUTTON = By.cssSelector("a[href*='facebook.com']");
    private static final By LINKEDIN_BUTTON = By.cssSelector("a[href*='linkedin.com']");
    private static final By VIMEO_BUTTON = By.cssSelector("a[href*='vimeo.com']");
    private static final By YOUTUBE_BUTTON = By.cssSelector("a[href*='youtube.com']");
    private static final By INSTAGRAM_BUTTON = By.cssSelector("a[href*='instagram.com']");
    private static final By OFFICIAL_SITE_BUTTON = By.cssSelector("a[href*='www.hiqo-solutions.com']");
    private static final By PRIVACY_POLICY_BUTTON = By.xpath("//span[contains(text(),'Privacy Policy')]");
    private static final By PRIVACY_POLICY_WINDOW = By
            .xpath("//h4[contains(@class,'modal') and contains(text(),'Privacy Policy')]");
    private static final By PRIVACY_POLICY_CLOSE_BUTTON = By
            .xpath("//h4[@id='policyModalLabel']/following-sibling::button[@aria-label='Close']");

    public Footer() {
        super(VISIBILITY_LOCATOR, "Footer with social networks and privacy policy");
    }

    @Step("Click 'Facebook' button")
    public void clickFacebookButton() {
        SelenideElement facebookButton = element(FACEBOOK_BUTTON);
        facebookButton.click();
    }

    @Step("Click 'LinkedIn' button")
    public void clickLinkedInButton() {
        SelenideElement linkedInButton = element(LINKEDIN_BUTTON);
        linkedInButton.click();
    }

    @Step("Click 'Vimeo' button")
    public void clickVimeoButton() {
        SelenideElement vimeoButton = element(VIMEO_BUTTON);
        vimeoButton.click();
    }

    @Step("Click 'YouTube' button")
    public void clickYouTubeButton() {
        SelenideElement youTubeButton = element(YOUTUBE_BUTTON);
        youTubeButton.click();
    }

    @Step("Click 'Instagram' button")
    public void clickInstagramButton() {
        SelenideElement instagramButton = element(INSTAGRAM_BUTTON);
        instagramButton.click();
    }

    @Step("Click 'Official Site' button")
    public void clickOfficialSiteButton() {
        SelenideElement officialSiteButton = element(OFFICIAL_SITE_BUTTON);
        officialSiteButton.click();
    }

    @Step("Check 'Privacy Policy' window for appearing")
    public boolean checkPrivacyPolicyWindow() {
        return isElementDisplayed(PRIVACY_POLICY_WINDOW);
    }

    @Step("Close 'Privacy Policy' window")
    public void closePrivacyPolicyWindow() {
        SelenideElement closePrivacyPoliceWindowButton = element(PRIVACY_POLICY_CLOSE_BUTTON);
        closePrivacyPoliceWindowButton.click();
    }

    @Step("Click on 'Privacy Policy' button in the end of page")
    public void clickOnPrivacyPolicyButton() {
        SelenideElement privacyPolicyButton = element(PRIVACY_POLICY_BUTTON);
        privacyPolicyButton.click();
    }
}