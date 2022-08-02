package project.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.ui.pages.MainPage;
import project.ui.pages.components.Footer;
import project.ui.pages.socialnetworks.*;

import static org.junit.jupiter.api.Assertions.*;

public class MainPageTest extends BaseTest {

    @Test
    @DisplayName("Check 'Facebook' button")
    public void checkFacebookButton() {
        MainPage mainPage = new MainPage();
        Footer footer = mainPage.getFooter();
        footer.clickFacebookButton();
        mainPage.switchToOpenedTab();
        FacebookPage facebookPage = new FacebookPage();
        assertTrue(facebookPage.isPageOpened(), "There is no 'Facebook' page opened");
        facebookPage.closeCurrentTab();
        facebookPage.switchToDefaultTab();
    }

    @Test
    @DisplayName("Check 'Instagram' button")
    public void checkInstagramButton() {
        MainPage mainPage = new MainPage();
        Footer footer = mainPage.getFooter();
        footer.clickInstagramButton();
        mainPage.switchToOpenedTab();
        InstagramPage instagramPage = new InstagramPage();
        assertTrue(instagramPage.isPageOpened(), "There is no 'Instagram' page opened");
        instagramPage.closeCurrentTab();
        instagramPage.switchToDefaultTab();
    }

    @Test
    @DisplayName("Check 'LinkedIn' button")
    public void checkLinkedInButton() {
        MainPage mainPage = new MainPage();
        Footer footer = mainPage.getFooter();
        footer.clickLinkedInButton();
        mainPage.switchToOpenedTab();
        LinkedInPage linkedInPage = new LinkedInPage();
        assertTrue(linkedInPage.isPageOpened(), "There is no 'LinkedIn' page opened");
        linkedInPage.closeCurrentTab();
        linkedInPage.switchToDefaultTab();
    }

    @Test
    @DisplayName("Check 'Official SIte' button")
    public void checkOfficialSiteButton() {
        MainPage mainPage = new MainPage();
        Footer footer = mainPage.getFooter();
        footer.clickOfficialSiteButton();
        mainPage.switchToOpenedTab();
        OfficialSitePage officialSitePage = new OfficialSitePage();
        assertTrue(officialSitePage.isPageOpened(), "There is no 'Official SIte' page opened");
        officialSitePage.closeCurrentTab();
        officialSitePage.switchToDefaultTab();
    }

    @Test
    @DisplayName("Check 'Vimeo' button")
    public void checkVimeoButton() {
        MainPage mainPage = new MainPage();
        Footer footer = mainPage.getFooter();
        footer.clickVimeoButton();
        mainPage.switchToOpenedTab();
        VimeoPage vimeoPage = new VimeoPage();
        assertTrue(vimeoPage.isPageOpened(), "There is no 'Vimeo' page opened");
        vimeoPage.closeCurrentTab();
        vimeoPage.switchToDefaultTab();
    }

    @Test
    @DisplayName("Check 'YouTube' button")
    public void checkYouTubeButton() {
        MainPage mainPage = new MainPage();
        Footer footer = mainPage.getFooter();
        footer.clickYouTubeButton();
        mainPage.switchToOpenedTab();
        YouTubePage youTubePage = new YouTubePage();
        assertTrue(youTubePage.isPageOpened(), "There is no 'Official SIte' page opened");
        youTubePage.closeCurrentTab();
        youTubePage.switchToDefaultTab();
    }
}