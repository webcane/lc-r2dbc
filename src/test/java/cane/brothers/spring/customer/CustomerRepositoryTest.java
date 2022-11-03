package cane.brothers.spring.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cane.brothers.spring.Config;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.test.StepVerifier;

@DataR2dbcTest
class CustomerRepositoryTest {

  @Autowired
  DatabaseClient client;

  @Autowired
  private CustomerRepository customerRepository;

  @Test
  void test_save() {
    var customer = Customer.builder().firstName("K1").lastName("Abc").build();

    StepVerifier.create(customerRepository.save(customer))
        .assertNext(c -> {
          assertEquals("K1", c.firstName());
          assertEquals("Abc", c.lastName());
        })
        .verifyComplete();
  }

  @Test
  void test_findByLastName() {
    var customer = Customer.builder().firstName("K1").lastName("Abc").build();

    client.sql("INSERT INTO customer (first_name, last_name) VALUES (\'K2\', \'Abc\')")
        .then().block(Duration.ofSeconds(5));

    StepVerifier.create(customerRepository.findByLastName("Abc"))
        .assertNext(c -> {
          assertEquals("K2", c.firstName());
        })
        .verifyComplete();

    StepVerifier.create(customerRepository.findByLastName("Qwerty"))
        .expectNextCount(0)
        .verifyComplete();
  }

  @Configuration
  @Import(Config.class)
  static class TestConfig {
  }
}