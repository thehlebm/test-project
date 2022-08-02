package project.dataproviders;

import com.fasterxml.jackson.databind.ObjectMapper;
import framework.helpers.FileUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import project.entity.ApplyFormDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CsvDataApplyProviderWithEmptyLetterField implements ArgumentsProvider {
    public static final String TEST_CSV_PATH = "testdata/testdata-emptyletter.csv";
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, String>> dataList = FileUtils.getCsvFromResources(TEST_CSV_PATH);
        Map<String, String>[] mapArray = dataList.toArray(new HashMap[dataList.size()]);
        ApplyFormDto[] applyFormDto = mapper.convertValue(mapArray, ApplyFormDto[].class);
        return Stream.of(applyFormDto).map(Arguments::of);
    }
}

