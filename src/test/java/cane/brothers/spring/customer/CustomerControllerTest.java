package cane.brothers.spring.customer;

import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@WebFluxTest(controllers = CustomerController.class)
class CustomerControllerTest {

  @MockBean
  CustomerService customerService;

  @Autowired
  private WebTestClient webClient;

  @Test
  void test_findAll() {
    Flux customers = Flux.just(
        Customer.builder().id(1).firstName("K1").lastName("Abc").build(),
        Customer.builder().id(2).firstName("K2").lastName("Abc").build(),
        Customer.builder().id(3).firstName("K3").lastName("Qwerty").build());

    given(this.customerService.getAll())
        .willReturn(customers);

    webClient.get().uri("/customers")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Customer.class)
        .hasSize(3);
  }

  @Test
  void test_findByLastNamePos() {
    Flux customers = Flux.just(
        Customer.builder().id(3).firstName("K3").lastName("Qwerty").build());

    given(this.customerService.getByLastName("Qwerty"))
        .willReturn(customers);

    webClient.get().uri("/customers?lastName={lastName}", "Qwerty")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Customer.class)
        .hasSize(1);
  }

  @Test
  void test_findByLastNameNeg() {
    Flux customers = Flux.empty();

    given(this.customerService.getByLastName("Qwerty"))
        .willReturn(customers);

    webClient.get().uri("/customers?lastName={lastName}", "Unknown")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Customer.class)
        .hasSize(0);
  }

  @Test
  void test_create() {
    var customerWithId = Customer.builder().id(1).firstName("K1").lastName("Abc").build();
    var customer = Customer.builder().firstName("K1").lastName("Abc").build();

    given(this.customerService.save(customer))
        .willReturn(Mono.just(customerWithId));

    webClient.post().uri("/customers")
        .body(Mono.just(customer), Customer.class)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Customer.class);
  }

  @Test
  void test_getPos() {
    var customerId = 1;
    var customerWithId = Customer.builder().id(customerId).firstName("K1").lastName("Abc").build();

    given(this.customerService.get(Long.valueOf(customerId)))
        .willReturn(Mono.just(customerWithId));

    webClient.get().uri("/customers/{id}", customerId)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Customer.class);
  }

  @Test
  void test_getNeg() {
    var customerId = 666;

    given(this.customerService.get(Long.valueOf(customerId)))
        .willReturn(Mono.empty());

    webClient.get().uri("/customers/{id}", customerId)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Customer.class);
  }

  @Test
  @Disabled
  void update() {
  }

  @Test
  @Disabled
  void delete() {
  }

  @EnableWebFlux
  @TestConfiguration
  static class TestConfig {
  }
}