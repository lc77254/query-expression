package io.github.lc77254.query.expression.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

/**
 * Basic implementation for {@link Expression}.
 *
 * @author Chen Li
 */
@AllArgsConstructor(access = PRIVATE)
@Getter
public class ExpressionImpl implements Expression {

    static final Expression EMPTY = new ExpressionImpl(null, Operator.NP, null);

    private Object left;

    private Operator operator;

    private Object right;

    public static Expression of(Object left, Operator operator, Object right) {
        if (operator == null || operator == Operator.NP) {
            return EMPTY;
        }
        if (operator.isConditional()) {
            if (isEmptyExpression(left) && isEmptyExpression(right)) {
                return EMPTY;
            }
            if (isEmptyExpression(left)) {
                return (Expression) right;
            }
            if (isEmptyExpression(right)) {
                return (Expression) left;
            }
        } else if (operator.isRelational()) {
            if (left == null || "".equals(left) || right == null || "".equals(right)) {
                return EMPTY;
            }
        } else {
            return EMPTY;
        }
        return new ExpressionImpl(left, operator, right);
    }

    static boolean isEmptyExpression(Object expression) {
        return !(expression instanceof Expression) || expression == EMPTY;
    }

    @Override
    public String toString() {
        if (operator == Operator.NP) {
            return "";
        }
        return wrap(getLeft()) + getOperator().getOp() + wrap(getRight());
    }

    String wrap(Object child) {
        if (isEmptyExpression(child)) {
            return String.valueOf(child);
        }
        Expression childExpression = (Expression) child;
        if (getOperator() == Operator.AND && childExpression.getOperator() == Operator.OR) {
            return Operator.LP.getOp() + String.valueOf(child) + Operator.RP.getOp();
        } else {
            return String.valueOf(child);
        }
    }
}
