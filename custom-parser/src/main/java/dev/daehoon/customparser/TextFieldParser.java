package dev.daehoon.customparser;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class TextFieldParser implements DataParser {

    private static final String CUSTOM_DATA_FIELD_TEXT = "^[A-Za-z0-9`~!@#$%^&*()\\-_=+{}\\[\\]|\\\\:;\'\"<>,./?\\s]+";

    private Option option;

    @Override
    public void parseOption(String option) {

        Map<String, String> optionValue = Arrays.stream(splitOption(option))
                .map(it -> splitOptionValue(it))
                .filter(it -> it.length == 2)
                .collect(Collectors.toMap(it -> it[0], it -> it[1]));

        int maxLength = Integer.parseInt(optionValue.getOrDefault("maxlength", "4000"));

        this.option = Option.builder()
                .maxLength(maxLength)
                .build();
    }

    @Override
    public String getFormattedData(String data) {

        return StringUtils.truncate(data, option.getMaxLength());
    }

    @Override
    public boolean isValid(String data) {

        return data.matches(CUSTOM_DATA_FIELD_TEXT);
    }
}
