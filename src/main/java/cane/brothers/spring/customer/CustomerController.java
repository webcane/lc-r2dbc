package cane.brothers.spring.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/customers")
@RequiredArgsConstructor
class CustomerController {

  private final CustomerService customerService;

  @GetMapping("")
  public Flux<Customer> find(@RequestParam(value = "lastName", required = false) String lastName) {
    if (StringUtils.hasText(lastName)) {
      return this.customerService.getByLastName(lastName);
    }
    return this.customerService.getAll();
  }

  @PostMapping("")
  public Mono<Customer> create(@RequestBody Customer post) {
    return this.customerService.save(post);
  }

  @GetMapping("/{id}")
  public Mono<Customer> get(@PathVariable("id") Long id) {
    return this.customerService.get(id);
  }

  @PutMapping("/{id}")
  public Mono<Customer> update(@PathVariable("id") Long id, @RequestBody Customer post) {
    return this.customerService.update(id, post);
  }

  @DeleteMapping("/{id}")
  public Mono<Void> delete(@PathVariable("id") Long id) {
    return this.customerService.delete(id);
  }
}
