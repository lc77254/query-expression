package io.github.lc77254.query.expression.core;

import java.util.Set;

/**
 * Basic parser can parse an Expression String to a {@link Expression} AST.
 *
 * @author Chen Li
 */
public class ExpressionParser {

    public Expression parse(String expression) {
        if (expression == null || expression.isEmpty()) {
            return Expression.EMPTY;
        }
        int index;
        if ((index = lastIndexOfGroup(expression)) != -1) {
            return getGroupExpression(expression, index);
        }
        if ((index = lastIndexOf(expression, Operator.CONDITIONAL.keySet())) != -1) {
            return getConditionalExpression(expression, index);
        }
        if ((index = lastIndexOf(expression, Operator.RELATIONAL.keySet())) != -1) {
            return getRelationalExpression(expression, index);
        }
        return Expression.EMPTY;
    }

    int lastIndexOfGroup(String expression) {
        if (expression.charAt(expression.length() - 1) != Operator.RP.getOp()) {
            return -1;
        }
        int index = expression.length() - 1;
        int count = 0;
        for (; index > -1; index--) {
            char op = expression.charAt(index);
            if (Operator.RP.getOp() == op) count++;
            if (Operator.LP.getOp() == op) count--;
            if (count == 0) break;
        }
        return index;
    }

    Expression getGroupExpression(String expression, int index) {
        if (index == 0) {
            return parse(expression.substring(1, expression.length() - 1));
        }
        return getConditionalExpression(expression, index - 1);
    }

    int lastIndexOf(String expression, Set<Character> operators) {
        for (int i = expression.length() - 1; i > -1; i--) {
            if (operators.contains(expression.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    Expression getConditionalExpression(String expression, int index) {
        Operator operator = Operator.CONDITIONAL.get(expression.charAt(index));
        return Expression.of(
                parse(expression.substring(0, index)),
                operator,
                parse(expression.substring(index + 1))
        );
    }

    Expression getRelationalExpression(String expression, int index) {
        Operator operator = Operator.RELATIONAL.get(expression.charAt(index));
        return Expression.of(
                expression.substring(0, index),
                operator,
                expression.substring(index + 1)
        );
    }
}
