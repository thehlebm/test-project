package framework.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import static framework.constants.BaseConstants.DEFAULT_ENCODING;

public final class FileUtils {

    private static final MessageFormat PROPERTY_FILE_NAME_PATTERN = new MessageFormat("/{0}.properties");
    private static final String DATA_SEPARATOR = ",";

    private FileUtils() {
    }

    public static Properties loadPropertiesFromResources(final String fileName) {
        Properties localProperties = new Properties();
        String fullFileName = PROPERTY_FILE_NAME_PATTERN.format(new Object[]{fileName});
        try {
            localProperties.load(FileUtils.class.getResourceAsStream(fullFileName));
        } catch (IOException e) {
            throw new RuntimeException(fileName + "  property file is not loaded because of " + e.getStackTrace());
        }
        return localProperties;
    }

    public static InputStream getInputStreamFromResources(final String fileName) {
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        InputStream file = (InputStream) classLoader.getResourceAsStream(fileName);
        if (file == null) {
            throw new RuntimeException(fileName + "  file is not loaded");

        }
        return file;
    }

    public static List<Map<String, String>> getCsvFromResources(final String fileName) throws IOException {
        List<Map<String, String>> results = new ArrayList<>();
        InputStreamReader inputStream = new InputStreamReader(getInputStreamFromResources(fileName));
        BufferedReader file = new BufferedReader(inputStream);
        String singleLine = file.readLine();
        String[] columnsArray = singleLine.split(DATA_SEPARATOR);
        while ((singleLine = file.readLine()) != null) {
            String[] dataArray = singleLine.split(DATA_SEPARATOR);
            results.add(convertToMap(columnsArray, dataArray));
        }
        return results;
    }

    private static Map<String, String> convertToMap(String[] keyName, String[] keyValue) {
        if (keyName.length != keyValue.length)
            throw new RuntimeException("Amount data provider value doesn't equals columns amount");

        Map<String, String> store = new HashMap<String, String>();
        for (int count = 0; count < keyName.length; count++) {
            store.put(keyName[count], keyValue[count]);
        }
        return store;
    }

    /**
     * @param relativeFilePath the relative path to the file in the resources folder
     * @return String content of the file
     */
    public static String readFromResourceFileAsString(String relativeFilePath) {
        String string = "";
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        InputStream resourceAsStream = classLoader.getResourceAsStream(relativeFilePath);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream, DEFAULT_ENCODING))) {
            string = br.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException("The data that was necessary for the tests hasn't been read from the file because of the error \r\n"
                    + e.getLocalizedMessage());
        }
        return string;
    }
}