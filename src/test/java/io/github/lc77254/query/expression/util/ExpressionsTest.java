package io.github.lc77254.query.expression.util;

import io.github.lc77254.query.expression.core.ExpressionParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ExpressionsTest {

    ExpressionParser parser = new ExpressionParser();

    @DisplayName("Print expression as a AST.")
    @ParameterizedTest
    @ValueSource(strings = "(a=1|b=2)&(c=3|d=4)")
    void print(String expression) {
        String actual = Expressions.print(parser.parse(expression));
        String br = System.lineSeparator();
        String expected =
                "'&': # and" + br +
                        "  - '|': # or" + br +
                        "    - a=1 # equal" + br +
                        "    - b=2 # equal" + br +
                        "  - '|': # or" + br +
                        "    - c=3 # equal" + br +
                        "    - d=4 # equal";
        Assertions.assertEquals(expected, actual);
    }

    @DisplayName("Empty case.")
    @Test
    void empty() {
        Assertions.assertEquals("", Expressions.print(parser.parse("")));
    }
}