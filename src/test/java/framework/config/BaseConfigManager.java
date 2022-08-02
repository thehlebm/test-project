package framework.config;


import com.codeborne.selenide.Configuration;
import framework.helpers.FileUtils;

import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * \brief The class contains common logic which is used by inherited classes
 */
public abstract class BaseConfigManager {

    private static final String ENVIRONMENT_VARIABLE = "ENVIRONMENT";
    private static final String SOLUTION_FILE_NAME = "solution";
    private static final String DEFAULT_ENVIRONMENT_VARIABLE = "dev";
    protected static Properties solutionProperties = FileUtils.loadPropertiesFromResources(SOLUTION_FILE_NAME);

    protected static Properties testProperties = new Properties();

    protected static Properties loadTestProperties() {
        String customProfile = System.getenv(ENVIRONMENT_VARIABLE);
        if (isNotEmpty(customProfile)) {
            testProperties = FileUtils.loadPropertiesFromResources(customProfile);
        } else {
            testProperties = FileUtils.loadPropertiesFromResources(DEFAULT_ENVIRONMENT_VARIABLE);
        }
        return testProperties;
    }

    protected static void initSelenideProperties() {
        Configuration.timeout = Long.valueOf(solutionProperties.getProperty("selenide.timeout"));
        Configuration.browserSize = solutionProperties.getProperty("selenide.browser.size");
        Configuration.pollingInterval = Long.valueOf(solutionProperties.getProperty("selenide.polling.interval"));
    }
}