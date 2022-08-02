package project.ui;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public interface Alerts {
    default boolean isAlertPresent() {
        try {
            getWebDriver()
                    .switchTo()
                    .alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    default Alert getAlert() {
        if (this.isAlertPresent()) {
            return getWebDriver().switchTo().alert();
        } else {
            return null;
        }
    }
}