package cane.brothers.spring.customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

  Flux<Customer> getAll();

  Flux<Customer> getByLastName(String lastName);

  Mono<Customer> save(Customer customer);

  Mono<Customer> get(Long id);

  Mono<Customer> update(Long id, Customer customer);

  Mono<Void> delete(Long id);
}
