package project.data;

import framework.helpers.FileUtils;
import framework.helpers.JsonUtils;
import project.entity.ApplyFormDto;

import static project.data.TestData.TEST_USER;

public class UserCreator {

    public static ApplyFormDto createDefaultUser() {
        String readUserFromJson = FileUtils.readFromResourceFileAsString(TEST_USER);
        return (ApplyFormDto) JsonUtils.serializeJsonString(ApplyFormDto.class, readUserFromJson);
    }
}
