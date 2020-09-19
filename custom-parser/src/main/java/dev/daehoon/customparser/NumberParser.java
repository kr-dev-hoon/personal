package dev.daehoon.customparser;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class NumberParser implements DataParser {

    private Option option;

    @Override
    public void parseOption(String option) {

        Map<String, String> optionValue = Arrays.stream(splitOption(option))
                .map(it -> splitOptionValue(it))
                .filter(it -> it.length == 2)
                .collect(Collectors.toMap(it -> it[0], it -> it[1]));

        Integer max = Optional.ofNullable(optionValue.get("max")).map(it -> Integer.parseInt(it)).orElseGet(() -> null);
        Integer min = Optional.ofNullable(optionValue.get("min")).map(it -> Integer.parseInt(it)).orElseGet(() -> null);
        Integer interval = Optional.ofNullable(optionValue.get("interval")).map(it -> Integer.parseInt(it))
                .orElseGet(() -> null);

        this.option = Option.builder()
                .max(max)
                .min(min)
                .interval(interval)
                .build();

    }

    @Override
    public String getFormattedData(String data) {

        return data;
    }

    @Override
    public boolean isValid(String data) {

        try {

            int value = Integer.parseInt(data);

            return checkNumber(option, value);

        } catch (Exception e) {
            return false;
        }

    }

    private boolean checkNumber(Option option, int value) {

        return option.getMin() <= value &&
                value <= option.getMax()
                && value % option.getInterval() == 0;
    }

}
