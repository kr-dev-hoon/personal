package dev.daehoon.customparser;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class DataParserTest {

    @DataProvider
    public static Object[][] isTextField() {

        return new Object[][] {
                { "width)(100|maxlength)(20|required)(1", 20, "data", "data", true },
                { "width)(100|maxlength)(10|required)(1", 10, "testintextfield", "testintext", true },
                { "width)(20|maxlength)(20|required)(0", 20, "testingtextfield testingtextfield",
                        "testingtextfield tes", true },
                { "width)(100|maxlength)(100|required)(1", 100, "testingtextfield testingtextfield",
                        "testingtextfield testingtextfield", true },
                { "width)(100|maxlength)(50|required)(1", 50, "`~!@#$%^&*()-_=+ {}[]|\\:;\"\'<>,./?",
                        "`~!@#$%^&*()-_=+ {}[]|\\:;\"\'<>,./?", true },
                { "width)(100|maxlength)(10|required)(1", 10, "한글 문자는 입력이 되지 않습니다!", "한글 문자는 입력이", false },
                { "width)(100|maxlength)(10|required)(1", 10, "☜ ☞", "☜ ☞", false }

        };
    }

    @DataProvider
    public static Object[][] isDate() {

        return new Object[][] {
                { "date)(on|time)(on|required)(1", true, "2020-12-25 23:20:11", "12-25-2020 23:20", true },
                { "date)(on|time)(on|required)(1", true, "2020-12-25 00:00", "12-25-2020 00:00", true },
                { "date)(on|time)(|required)(1", false, "2020-12-25", "12-25-2020", true },
                { "date)(on|time)(|required)(1", false, "2020-12-25 23:20:11", null, false },
                { "date)(on|time)(on|required)(1", true, "2020-12-25", null, false },
                { "date)(on|time)(on|required)(1", true, "20/12/25 23:20:11", null, false },
                { "date)(on|time)(|required)(1", false, "2020/12/25", null, false },
        };
    }

    @DataProvider
    public static Object[][] isNumber() {

        return new Object[][] {
                { "min)(10|max)(200|interval)(10|default)(50|required)(0",
                        10, 200, 10, "100", "100", true },
                { "min)(10|max)(200|interval)(10|default)(50|required)(0",
                        10, 200, 10, "10", "10", true },
                { "min)(10|max)(200|interval)(10|default)(50|required)(0",
                        10, 200, 10, "200", "200", true },
                { "min)(10|max)(200|interval)(10|default)(50|required)(0",
                        10, 200, 10, "15", "15", false },
                { "min)(10|max)(200|interval)(10|default)(50|required)(0",
                        10, 200, 10, "5", "5", false },
                { "min)(10|max)(200|interval)(10|default)(50|required)(0",
                        10, 200, 10, "test", "test", false },
                { "min)(10|max)(200|interval)(10|default)(50|required)(0",
                        10, 200, 10, "210", "210", false },
                { "min)(10|)(200|interval)(10|default)(50|required)(0",
                        10, null, 10, "100", "100", false },
                { "min)(10|max)(|interval)(10|default)(50|required)(0",
                        10, null, 10, "100", "100", false }
        };
    }

    @Test
    @UseDataProvider("isTextField")
    public void textFieldTest(String option, Integer maxLength, String data, String formattedData, boolean expected) {

        TextFieldParser textFieldParser = new TextFieldParser();

        textFieldParser.parseOption(option);

        Assert.assertEquals(textFieldParser.getOption().getMaxLength(), maxLength);

        Assert.assertEquals(expected, textFieldParser.isValid(data));

        Assert.assertEquals(formattedData, textFieldParser.getFormattedData(data));

    }

    @Test
    @UseDataProvider("isDate")
    public void dateTest(String option, Boolean hasTime, String data, String formattedData, boolean expected) {

        DateParser dateParser = new DateParser();

        dateParser.parseOption(option);

        Assert.assertEquals(dateParser.getOption().getHasTime(), hasTime);

        Assert.assertEquals(expected, dateParser.isValid(data));

        Assert.assertEquals(formattedData, dateParser.getFormattedData(data));

    }

    @Test
    @UseDataProvider("isNumber")
    public void numberTest(String option, Integer min, Integer max, Integer interval, String data, String formattedData,
            boolean expected) {

        NumberParser numberParser = new NumberParser();

        numberParser.parseOption(option);

        Option o = numberParser.getOption();

        Assert.assertEquals(max, o.getMax());

        Assert.assertEquals(min, o.getMin());

        Assert.assertEquals(interval, o.getInterval());

        Assert.assertEquals(expected, numberParser.isValid(data));

        Assert.assertEquals(formattedData, numberParser.getFormattedData(data));

    }

}
