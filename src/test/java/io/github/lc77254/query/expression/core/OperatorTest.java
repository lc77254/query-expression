package io.github.lc77254.query.expression.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OperatorTest {

    @Test
    void nullable() {
        Assertions.assertEquals(Operator.NP, Operator.of((String) null));
        Assertions.assertEquals(Operator.NP, Operator.of((Character) null));
    }
}