package dev.daehoon.customparser;

public enum DateTimeFormat {

    DATE_FORMAT_YYYY_MM_DD("yyyy-MM-dd"),
    DATE_TIME_YYYY_MM_DD_HH_MI_SS("yyyy-MM-dd HH:mm[:ss]"), // ss는 Optional하다.

    CUSTOM_DATE_ONLY_FORMAT("MM-dd-yyyy"),
    CUSTOM_DATE_TIME_FORMAT("MM-dd-yyyy HH:mm");

    private String format;

    DateTimeFormat(String format) {

        this.format = format;
    }

    public String getFormat() {

        return format;
    }
}
