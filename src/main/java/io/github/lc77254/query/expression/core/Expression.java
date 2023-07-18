package io.github.lc77254.query.expression.core;

/**
 * Basic unit for an expression AST.
 *
 * @author Chen Li
 */
public interface Expression {

    /**
     * The empty Expression.
     */
    Expression EMPTY = ExpressionImpl.EMPTY;

    /**
     * Create a new Expression.
     *
     * @param left     fieldName or expression.
     * @param operator operator in {@link Operator}.
     * @param right    fieldValue or expression.
     * @return {@link Expression}.
     * @see Operator
     */
    static Expression of(Object left, Operator operator, Object right) {
        return ExpressionImpl.of(left, operator, right);
    }

    /**
     * Get the left-hand operand.
     *
     * @return {@link Expression} or {@link String}.
     */
    Object getLeft();

    /**
     * Get the operator.
     *
     * @return {@link Operator}.
     */
    Operator getOperator();

    /**
     * Get the right-hand operand.
     *
     * @return {@link Expression} or {@link Object}.
     */
    Object getRight();

    /**
     * ANDs the given {@link Expression} to the current one.
     *
     * @param other fieldValue or expression.
     * @return The conjunction of the expressions.
     */
    default Expression and(Expression other) {
        return of(this, Operator.AND, other);
    }

    /**
     * ANDs a new {@link Expression} to the current one.
     *
     * @param left     fieldName or expression.
     * @param operator operator in {@link Operator}.
     * @param right    fieldValue or expression.
     * @return The conjunction of the expressions.
     */
    default Expression and(Object left, Operator operator, Object right) {
        return and(of(left, operator, right));
    }

    /**
     * ORs the given {@link Expression} to the current one.
     *
     * @param other fieldValue or expression.
     * @return The conjunction of the expressions.
     */
    default Expression or(Expression other) {
        return of(this, Operator.OR, other);
    }

    /**
     * ORs a new {@link Expression} to the current one.
     *
     * @param left     fieldName or expression.
     * @param operator operator in {@link Operator}.
     * @param right    fieldValue or expression.
     * @return The conjunction of the expressions.
     */
    default Expression or(Object left, Operator operator, Object right) {
        return or(of(left, operator, right));
    }
}