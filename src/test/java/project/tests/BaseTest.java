package project.tests;

import framework.config.EnvironmentManager;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class BaseTest {

    @BeforeEach
    void precondition() {
        open(EnvironmentManager.BASE_URL);
    }

    @AfterEach
    void postCondition() {
        closeWebDriver();
    }
}