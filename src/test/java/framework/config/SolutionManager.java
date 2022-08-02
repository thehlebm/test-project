package framework.config;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;

import java.time.Duration;

/**
 * \brief The class provides parameters connected with the current solution
 */
public class SolutionManager extends BaseConfigManager {

    private static final String CONFIG_NAME = "solution";

    static {
        loadTestProperties();
        initLoggerProperties();
    }

    public static final boolean LOG_CONSOLE_VERBOSE = Boolean.parseBoolean(solutionProperties
            .getProperty("log.selenide.verbose"));
    public static final boolean LOG_ALLURE_VERBOSE = Boolean.parseBoolean(solutionProperties
            .getProperty("log.selenide.to.allure.verbose"));
    public static final Long TIMEOUT = Long.parseLong(solutionProperties
            .getProperty("wait.pageTimeout"));
    public static final Duration DURATION_TIMEOUT = Duration.ofMillis(TIMEOUT);
    public static final Long CONDITIONAL_TIMEOUT = Long.parseLong(solutionProperties
            .getProperty("selenide.timeout"));
    public static final Long SMALL_CONDITIONAL_TIMEOUT = Long.parseLong(solutionProperties
            .getProperty("wait.smallConditionTimeout"));


    public static void initLoggerProperties() {
        if (LOG_ALLURE_VERBOSE)
            SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                    .screenshots(true)
                    .savePageSource(false));
        if (LOG_CONSOLE_VERBOSE) {
        }
    }
}