package dev.daehoon.customparser;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static dev.daehoon.customparser.DateTimeFormat.CUSTOM_DATE_ONLY_FORMAT;
import static dev.daehoon.customparser.DateTimeFormat.CUSTOM_DATE_TIME_FORMAT;
import static dev.daehoon.customparser.DateTimeFormat.DATE_FORMAT_YYYY_MM_DD;
import static dev.daehoon.customparser.DateTimeFormat.DATE_TIME_YYYY_MM_DD_HH_MI_SS;

@Getter
public class DateParser implements DataParser {

    private Option option;

    @Override
    public void parseOption(String option) {

        boolean hasTime = Arrays.stream(splitOption(option))
                .map(it -> splitOptionValue(it))
                .anyMatch(it -> it.length == 2 && it[0].equals("time") && it[1].equals("on"));

        this.option = Option.builder()
                .hasTime(hasTime)
                .build();

    }

    @Override
    public String getFormattedData(String data) {

        try {
            if (option.getHasTime()) {

                return LocalDateTime.parse(data, DateTimeFormatter.ofPattern(DATE_TIME_YYYY_MM_DD_HH_MI_SS.getFormat()))
                        .format(DateTimeFormatter.ofPattern(CUSTOM_DATE_TIME_FORMAT.getFormat()));
            } else {
                return LocalDate.parse(data, DateTimeFormatter.ofPattern(DATE_FORMAT_YYYY_MM_DD.getFormat()))
                        .format(DateTimeFormatter.ofPattern(CUSTOM_DATE_ONLY_FORMAT.getFormat()));
            }

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean isValid(String data) {

        try {

            if (option.getHasTime()) {
                LocalDateTime.parse(data, DateTimeFormatter.ofPattern(DATE_TIME_YYYY_MM_DD_HH_MI_SS.getFormat()));
            } else {
                LocalDate.parse(data, DateTimeFormatter.ofPattern(DATE_FORMAT_YYYY_MM_DD.getFormat()));
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
