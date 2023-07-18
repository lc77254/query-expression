package io.github.lc77254.query.expression.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class ExpressionParserTest {

    ExpressionParser parser = new ExpressionParser();

    @DisplayName("Base parsing.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a=1&b=2|c=3",
            "a=1&(b=2|c=3)",
            "(a=1|b=2)&(c=3|d=4)"
    })
    void base(String expression) {
        Assertions.assertEquals(expression, parser.parse(expression).toString());
    }

    @DisplayName("Empty parsing.")
    @ParameterizedTest
    @CsvSource({
            "'',''",
            "&,''",
            "&a=1,a=1",
            "a=1&,a=1",
            "a=1&b,a=1",
            "a=1&b=,a=1",
            "a=1&=2,a=1",
            "(a=1&b=2)&c=3,a=1&b=2&c=3"
    })
    void empty(String expression, String expected) {
        Assertions.assertEquals(expected, parser.parse(expression).toString());
    }
}