package dev.daehoon.customparser.v1;

import dev.daehoon.customparser.DataType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static dev.daehoon.customparser.DateTimeFormat.DATE_FORMAT_YYYY_MM_DD;
import static dev.daehoon.customparser.DateTimeFormat.DATE_TIME_YYYY_MM_DD_HH_MI_SS;

public class ParserBefore {

    final static String SPLIT_VALUE_REGEX = "";

    final static String SPLIT_OPTION_REGEX = "";

    public static boolean validate(DataEntity entity, DataType dataType, String data) {

        if (dataType == DataType.TEXT_FILED) {

            return validTextField(entity.getOption(), data);

        } else if (dataType == DataType.DATE) {

            return validDate(entity.getOption(), data);

        } else if (dataType == DataType.NUMBER) {

            return validNumber(entity.getOption(), data);

        }

        return false;
    }

    private static boolean validTextField(String option, String data) {

        Map<String, String> optionValue = Arrays.stream(splitOption(option))
                .map(ParserBefore::splitOptionValue)
                .filter(it -> it.length == 2)
                .collect(Collectors.toMap(it -> it[0], it -> it[1]));

        int maxValue = Integer.parseInt(optionValue.getOrDefault("max", "4000"));

        return data.length() <= maxValue;
    }

    private static boolean validDate(String option, String data) {

        try {

            boolean hasTime = Arrays.stream(splitOption(option))
                    .map(ParserBefore::splitOptionValue)
                    .anyMatch(it -> it.length == 2 && it[0].equals("time") && it[1].equals("time_date"));

            if (hasTime) {
                LocalDateTime.parse(data, DateTimeFormatter.ofPattern(DATE_TIME_YYYY_MM_DD_HH_MI_SS.getFormat()));
            } else {
                LocalDate.parse(data, DateTimeFormatter.ofPattern(DATE_FORMAT_YYYY_MM_DD.getFormat()));
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private static boolean validNumber(String option, String data) {

        try {

            Map<String, String> optionValue = Arrays.stream(splitOption(option))
                    .map(ParserBefore::splitOptionValue)
                    .filter(it -> it.length == 2)
                    .collect(Collectors.toMap(it -> it[0], it -> it[1]));

            int maxValue = Integer.parseInt(optionValue.get("max"));
            int minValue = Integer.parseInt(optionValue.get("min"));
            int intervalValue = Integer.parseInt(optionValue.get("interval"));

            int value = Integer.parseInt(data);

            return checkNumber(minValue, maxValue, intervalValue, value);
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean checkNumber(int min, int max, int interval, int value) {

        return min <= value && value <= max && value % interval == 0;
    }

    private static String[] splitOption(String option) {

        return option.split(SPLIT_OPTION_REGEX);
    }

    private static String[] splitOptionValue(String optionValue) {

        return optionValue.split(SPLIT_VALUE_REGEX);
    }
}
