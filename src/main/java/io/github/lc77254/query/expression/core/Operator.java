package io.github.lc77254.query.expression.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;

/**
 * Basic operator supported in an Expression String.
 *
 * @author Chen Li
 */
@AllArgsConstructor
@Getter
public enum Operator {

    // Relational Operators
    EQ('=', "equal"),

    NE('≠', "not equal"),

    LK('≈', "like"),

    GT('>', "greater than"),

    GN('≥', "greater than or equal"),

    LT('<', "less than"),

    LN('≤', "less than or equal"),

    IN('∈', "in"),

    NI('∉', "not in"),

    // Conditional Operators
    AND('&', "and"),

    OR('|', "or"),

    // Group Operators
    LP('(', "left parenthesis"),

    RP(')', "right parenthesis"),

    // No Operator
    NP('\0', "no operator");

    public static final Map<Character, Operator> RELATIONAL = Stream.of(EQ, NE, LK, GT, GN, LT, LN, IN, NI)
            .collect(Collectors.toMap(Operator::getOp, identity()));

    public static final Map<Character, Operator> CONDITIONAL = Stream.of(AND, OR)
            .collect(Collectors.toMap(Operator::getOp, identity()));

    public static final Map<Character, Operator> OPS = Stream.of(values())
            .collect(Collectors.toMap(Operator::getOp, identity()));

    final Character op;

    final String desc;

    public static Operator of(String op) {
        if (op == null || "".equals(op)) {
            return NP;
        }
        return of(op.charAt(0));
    }

    public static Operator of(Character op) {
        if (op == null) {
            return NP;
        }
        return OPS.getOrDefault(op, NP);
    }

    public boolean isRelational() {
        return RELATIONAL.containsKey(getOp());
    }

    public boolean isConditional() {
        return CONDITIONAL.containsKey(getOp());
    }
}
