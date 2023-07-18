package io.github.lc77254.query.expression.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ExpressionTest {

    @DisplayName("Append AND Expression.")
    @ParameterizedTest
    @CsvSource(value = {
            "'',=,'',a=1",
            "b,'',2,a=1",
            "b,'(',2,a=1",
            "b,=,2,a=1&b=2"
    }, nullValues = "null")
    void and(String key, String op, Object value, String expected) {
        Expression expression = Expression.of("a", Operator.EQ, 1);
        expression = expression.and(key, Operator.of(op), value);
        Assertions.assertEquals(expected, expression.toString());
    }

    @DisplayName("Append OR Expression.")
    @ParameterizedTest
    @CsvSource(value = {
            "'',=,'',a=1",
            "b,'',2,a=1",
            "b,')',2,a=1",
            "b,=,2,a=1|b=2"
    }, nullValues = "null")
    void or(String key, String op, Object value, String expected) {
        Expression expression = Expression.of("a", Operator.EQ, 1);
        expression = expression.or(key, Operator.of(op), value);
        Assertions.assertEquals(expected, expression.toString());
    }
}