package dev.daehoon.customparser;

public enum DataType {

    TEXT_FILED(1, TextFieldParser.class), DATE(2, DateParser.class), NUMBER(3, NumberParser.class);

    private Integer code;

    private final Class<? extends DataParser> parserClass;

    DataType(Integer code, Class<? extends DataParser> parserClass) {

        this.code = code;
        this.parserClass = parserClass;
    }

    public Integer getCode() {

        return code;
    }

    public Class<? extends DataParser> getParserClassBy() {

        return parserClass;
    }

}