package project.dataproviders;

import framework.helpers.FileUtils;
import framework.helpers.JsonUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import project.entity.ApplyFormDto;

import java.io.IOException;
import java.util.stream.Stream;

public class JsonDataApplyProviderFileFormats implements ArgumentsProvider {
    public static final String JSON_FILE_FORMATS = "testdata/testdata-fileformats.json";

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws IOException {
        String userJsonString = FileUtils.readFromResourceFileAsString(JSON_FILE_FORMATS);
        ApplyFormDto[] applyFormDto = (ApplyFormDto[]) JsonUtils.serializeJsonString(ApplyFormDto[].class, userJsonString);
        return Stream.of(applyFormDto).map(Arguments::of);
    }
}
