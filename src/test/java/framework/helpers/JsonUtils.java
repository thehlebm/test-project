package framework.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class JsonUtils {

    /**
     * @param json         JSON source string
     * @param pathToObject path to JSON object
     * @return HashMap
     */
    public static HashMap<String, ArrayList<String>> getJSONObject(String json, String pathToObject) {

        JsonPath jsonPath = new JsonPath(json);
        return jsonPath.getJsonObject(pathToObject);
    }

    /**
     * Verifies that response body is valid json and converts it to string.
     *
     * @param response
     * @return json body as String
     */
    public static String convertJsonResponseToString(Response response) {
        response.then()
                .contentType(ContentType.JSON)
                .extract()
                .response();
        return response.asString();
    }

    /**
     * @param value DTO object with valid getters and setters to be converted
     * @return returns JSON string
     */
    public static String serializeJson(Object value) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = null;
        try {
            jsonBody = mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new AssertionError("We were not able to serialize the  provided object to json because of the following exeption \r\n"
                    + e.getLocalizedMessage());
        }
        return jsonBody;
    }

    /**
     * Verifies that response body is valid json and converts it to DTO
     *
     * @param response api response
     * @param aClass   class of the expected DTO with valid getters/setters
     * @return returns deserialized object
     */
    public static Object deserializeJsonResponse(Response response, Class aClass) {
        String responceBody = JsonUtils.convertJsonResponseToString(response);
        Object jsonClassObject = serializeJsonString(aClass, responceBody);
        return jsonClassObject;
    }

    /**
     * Converts json string to DTO
     *
     * @param json   valid json string
     * @param aClass class of the expected DTO with valid getters/setters
     * @return returns deserialized object
     */
    public static Object serializeJsonString(Class aClass, String json) {
        Object jsonClassObject;
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonClassObject = mapper.readValue(json, aClass);
        } catch (IOException e) {
            throw new AssertionError("We were not able to deserialize received json to the provided DTO because of the following exeption \r\n"
                    + e.getLocalizedMessage());
        }
        return jsonClassObject;
    }

    /**
     * Reads resource json  file to string, and converts it to DTO
     *
     * @param filePath resource file path
     * @param aClass   class of the expected DTO with valid getters/setters
     * @return returns deserialized object
     */
    public static <T> T deserializeJsonFile(String filePath, Class aClass) {
        String json = FileUtils.readFromResourceFileAsString(filePath);
        Object jsonClassObject = serializeJsonString(aClass, json);
        return (T) jsonClassObject;
    }

    @SuppressWarnings("unchecked")
    public static <T> T convertObjectToPojo(Object object, Class pojoClass) {
        String json = serializeJson(object);
        Object pojo = serializeJsonString(pojoClass, json);
        return (T) pojo;
    }
}