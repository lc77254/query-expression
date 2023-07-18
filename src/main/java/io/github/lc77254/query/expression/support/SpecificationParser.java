package io.github.lc77254.query.expression.support;

import io.github.lc77254.query.expression.core.Expression;
import io.github.lc77254.query.expression.core.ExpressionParser;
import io.github.lc77254.query.expression.core.Operator;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import java.util.stream.Stream;

/**
 * Basic parser can parse an Expression String or an {@link Expression} AST to a {@link Specification} AST. <br>
 * A {@link ConversionService} is included to covert the Expression String Value to an Object Value.
 *
 * @author Chen Li
 */
public class SpecificationParser {

    private final ExpressionParser expressionParser;

    private final ConversionService conversionService;

    public SpecificationParser() {
        this(new DefaultConversionService());
    }

    public SpecificationParser(ConversionService conversionService) {
        this.expressionParser = new ExpressionParser();
        this.conversionService = conversionService;
    }

    public <T> Specification<T> parse(String expression) {
        return parse(expressionParser.parse(expression));
    }

    public <T> Specification<T> parse(Expression expression) {
        if (expression == Expression.EMPTY) {
            return Specification.where(null);
        }
        if (expression.getOperator() == Operator.AND) {
            Specification<T> left = parse((Expression) expression.getLeft());
            Specification<T> right = parse((Expression) expression.getRight());
            return Specification.where(left).and(right);
        } else if (expression.getOperator() == Operator.OR) {
            Specification<T> left = parse((Expression) expression.getLeft());
            Specification<T> right = parse((Expression) expression.getRight());
            return Specification.where(left).or(right);
        } else {
            return convert(expression);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    <T> Specification<T> convert(Expression expression) {
        switch (expression.getOperator()) {
            case EQ:
                return (root, query, builder) -> builder.equal(getPath(root, expression), getValue(root, expression));
            case NE:
                return (root, query, builder) -> builder.notEqual(getPath(root, expression), getValue(root, expression));
            case LK:
                return (root, query, builder) -> builder.like(getPath(root, expression), "%" + getValue(root, expression) + "%");
            case GT:
                return (root, query, builder) -> builder.greaterThan(getPath(root, expression), (Comparable) getValue(root, expression));
            case GN:
                return (root, query, builder) -> builder.greaterThanOrEqualTo(getPath(root, expression), (Comparable) getValue(root, expression));
            case LT:
                return (root, query, builder) -> builder.lessThan(getPath(root, expression), (Comparable) getValue(root, expression));
            case LN:
                return (root, query, builder) -> builder.lessThanOrEqualTo(getPath(root, expression), (Comparable) getValue(root, expression));
            case IN:
                return (root, query, builder) -> getPath(root, expression).in((Object[]) getValue(root, expression));
            case NI:
                return (root, query, builder) -> getPath(root, expression).in((Object[]) getValue(root, expression)).not();
            default:
                return Specification.where(null);
        }
    }

    <X, Y> Path<Y> getPath(Path<X> root, Expression expression) {
        return root.get((String) expression.getLeft());
    }

    <X> Object getValue(Path<X> root, Expression expression) {
        Class<?> type = root.get((String) expression.getLeft()).getJavaType();
        Object value = expression.getRight();
        if (value instanceof String && ((String) value).contains(",")) {
            return Stream.of(((String) value).split(",")).map(v -> conversionService.convert(v, type)).toArray();
        } else {
            return conversionService.convert(value, type);
        }
    }
}
