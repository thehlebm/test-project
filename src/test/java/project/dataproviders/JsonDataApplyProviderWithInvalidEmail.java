package project.dataproviders;

import framework.helpers.FileUtils;
import framework.helpers.JsonUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import project.entity.ApplyFormDto;

import java.io.IOException;
import java.util.stream.Stream;

public class JsonDataApplyProviderWithInvalidEmail implements ArgumentsProvider {
    public static final String JSON_FILE_EMAILS = "testdata/testdata-invalidemail.json";

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws IOException {
        String userJsonString = FileUtils.readFromResourceFileAsString(JSON_FILE_EMAILS);
        ApplyFormDto[] applyFormDto = (ApplyFormDto[]) JsonUtils.serializeJsonString(ApplyFormDto[].class, userJsonString);
        return Stream.of(applyFormDto).map(Arguments::of);
    }
}