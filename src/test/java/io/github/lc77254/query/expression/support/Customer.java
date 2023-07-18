package io.github.lc77254.query.expression.support;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Customer extends AbstractPersistable<Long> {

    private String firstname;
    private String lastname;
    private Integer age;
}
