package dev.daehoon.inflearn;

import dev.daehoon.inflearn.study.Base;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepeatTest {

    int value = 1;

    @DisplayName("Display Name")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetition}")
    void repeat_test(RepetitionInfo repetitionInfo) {

        System.out.println("total : " + repetitionInfo.getTotalRepetitions() +
                "current : " + repetitionInfo.getCurrentRepetition());
    }

    @ParameterizedTest
    @ValueSource(strings = { "1", "2", "3", "4" })
    @EmptySource
    @NullSource
    void parameterized_test(String message) {

        System.out.println(message);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    void parameterized_object_test(@ConvertWith(BaseConverter.class) Base base) {

        System.out.println(base.getInteger());
    }

    static class BaseConverter extends SimpleArgumentConverter {

        @Override
        protected Object convert(Object o, Class<?> aClass) throws ArgumentConversionException {

            return new Base(Integer.parseInt(o.toString()));
        }
    }

    @ParameterizedTest
    @CsvSource({ "Java, 11", "Spring Boot, 2" })
        // void parameterized_csv_test(@AggregateWith() Base base){
    void parameterized_csv_test(ArgumentsAccessor accessor) {

        Base base = new Base(accessor.getInteger(0), accessor.getString(1));

        System.out.println(base.getInteger());

        System.out.println(base.getName());
    }

    static class BaseAggregator implements ArgumentsAggregator {

        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext)
                throws ArgumentsAggregationException {

            return new Base(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        }
    }

}
