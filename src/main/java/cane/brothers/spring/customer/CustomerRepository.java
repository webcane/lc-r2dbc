package cane.brothers.spring.customer;

import net.lecousin.reactive.data.relational.repository.LcR2dbcRepository;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Flux;

public interface CustomerRepository extends LcR2dbcRepository<Customer, Long> {

  @Query("SELECT * FROM customer WHERE last_name = :lastname")
  Flux<Customer> findByLastName(String lastName);
}
