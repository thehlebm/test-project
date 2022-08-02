package project.ui;

import com.codeborne.selenide.Condition;
import framework.config.SolutionManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public interface PageLoadWaiters {
    public static final By BLOCK_UI = By.xpath("//*[contains(@class, 'block-ui')]");
    public static final String LOADING_CIRCLE_KNOCKOUT = ".loading";
    public static final String CLASS_GLOBAL_LOADING = "[class*='globalLoading']";
    public static final String RETURN_DOCUMENT_READY_STATE = "return document.readyState";
    public static final int TIMEOUT = 15;
    public static final String DOCUMENT_STATE = "complete";

     default void waitForLoad() {
        waitForLoad(true);
    }

     default void waitForLoad(boolean waitWhenSpinnerIsFinished) {
        waitDocIsReady();
        if (waitWhenSpinnerIsFinished) {
            waitForKnockoutLoad();
            waitForReactLoad();
        }
    }

    default void waitForReactLoad() {
        try {
            $(BLOCK_UI).shouldBe(Condition.hidden);
        } catch (Exception e) {
            throw new TimeoutException("Timeout to load page with the following params: "
                    + System.lineSeparator()
                    + e.getMessage());
        }
    }

    default void waitForKnockoutLoad() {
        $(LOADING_CIRCLE_KNOCKOUT).shouldBe(Condition.hidden);
        $(CLASS_GLOBAL_LOADING).shouldBe(Condition.hidden);
    }

    default void waitDocIsReady() {
        ExpectedCondition<Boolean> pageLoadCondition
                = driver -> ((JavascriptExecutor) driver).executeScript(RETURN_DOCUMENT_READY_STATE)
                .equals(DOCUMENT_STATE);
        WebDriverWait wait = new WebDriverWait(getWebDriver(), Duration.ofMillis(SolutionManager.TIMEOUT));
        wait.until(pageLoadCondition);
    }
}
