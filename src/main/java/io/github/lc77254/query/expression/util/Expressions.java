package io.github.lc77254.query.expression.util;

import io.github.lc77254.query.expression.core.Expression;
import lombok.NoArgsConstructor;

import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static lombok.AccessLevel.PRIVATE;

/**
 * Basic util for {@link Expression} AST.
 *
 * @author Chen Li
 */
@NoArgsConstructor(access = PRIVATE)
public class Expressions {

    /**
     * Print {@link Expression} in yaml format.
     *
     * @param expression an expression AST.
     * @return formatted expression string.
     */
    public static String print(Expression expression) {
        if (expression == null || expression == Expression.EMPTY) {
            return "";
        }
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        add(expression, sj, 0);
        return sj.toString();
    }

    static void add(Expression expression, StringJoiner sj, int depth) {
        if (expression.getOperator().isConditional()) {
            String content = "'" + expression.getOperator().getOp() + "': # " + expression.getOperator().getDesc();
            if (depth != 0) {
                content = "- " + content;
            }
            sj.add(leftPad(depth * 2) + content);
            add((Expression) expression.getLeft(), sj, depth + 1);
            add((Expression) expression.getRight(), sj, depth + 1);
        } else {
            String content = "- " + expression + " # " + expression.getOperator().getDesc();
            sj.add(leftPad(depth * 2) + content);
        }
    }

    static String leftPad(int size) {
        return Stream.generate(() -> " ").limit(size).collect(Collectors.joining());
    }
}
