package framework.config;

/**
 * \brief The class provides parameters connected with an environment where autotests should be executed
 */
public final class EnvironmentManager extends BaseConfigManager {

    static {
        loadTestProperties();
        initSelenideProperties();
    }

    // APP DATA (if required)
    public static final String USERNAME = testProperties.getProperty("user.username");
    public static final String PASSWORD = testProperties.getProperty("user.password");
    public static final String BASE_URL = testProperties.getProperty("webUrl");
}