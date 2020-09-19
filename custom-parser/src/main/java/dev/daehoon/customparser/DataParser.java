package dev.daehoon.customparser;

public interface DataParser {

    final String SPLIT_OPTION_REGEX = "\\|";

    final String SPLIT_VALUE_REGEX = "\\)\\(";

    void parseOption(String option);

    String getFormattedData(String data);

    boolean isValid(String data);

    default String[] splitOption(String option) {

        return option.split(SPLIT_OPTION_REGEX);
    }

    default String[] splitOptionValue(String option) {

        return option.split(SPLIT_VALUE_REGEX);
    }

}
