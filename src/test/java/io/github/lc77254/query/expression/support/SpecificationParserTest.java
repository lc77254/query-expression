package io.github.lc77254.query.expression.support;

import io.github.lc77254.query.expression.core.Expression;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SpecificationParserTest {

    @Autowired
    CustomerRepository customerRepository;

    SpecificationParser specificationParser = new SpecificationParser();

    @BeforeEach
    public void setUp() {
        ArrayList<Customer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer();
            customer.setFirstname("firstname-" + i);
            customer.setLastname("lastname-" + i);
            customer.setAge(20 + i);
            list.add(customer);
        }
        customerRepository.saveAll(list);
    }

    @AfterEach
    public void tearDown() {
        customerRepository.deleteAll();
    }

    @DisplayName("Parse Expression String.")
    @ParameterizedTest
    @CsvSource(value = {
            "#10",
            "firstname=firstname-1#1",
            "firstname≈firstname#10",
            "firstname≠firstname-1#9",
            "age>20#9",
            "age≥20#10",
            "age<29#9",
            "age≤29#10",
            "(age∈20,21,22)#3",
            "(age∉20,21,22)#7",
            "firstname=firstname-1|lastname=lastname-2#2",
            "firstname=firstname-1&lastname=lastname-2#0",
            "(age>20&age<22)|(age>28)#2",
            "(age≥20&age≤22)|(age≥28)#5"
    }, delimiter = '#', nullValues = "null")
    void parse(String expression, String expected) {
        Specification<Customer> specification = specificationParser.parse(expression);
        List<Customer> list = customerRepository.findAll(specification);
        Assertions.assertEquals(Integer.parseInt(expected), list.size());
    }

    @DisplayName("Empty Case.")
    @Test
    void empty() {
        Specification<Customer> specification = specificationParser.convert(Expression.EMPTY);
        List<Customer> list = customerRepository.findAll(specification);
        Assertions.assertEquals(10, list.size());
    }
}