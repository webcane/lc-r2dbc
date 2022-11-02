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
  @Disabled
  void create() {
  }

  @Test
  @Disabled
  void get() {
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