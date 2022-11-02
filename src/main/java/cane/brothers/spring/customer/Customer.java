package cane.brothers.spring.customer;

import lombok.Builder;
import net.lecousin.reactive.data.relational.annotations.GeneratedValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Table
public record Customer (
  @Id
  @GeneratedValue(strategy = GeneratedValue.Strategy.SEQUENCE, sequence = "customer_id_seq")
  Integer id,

  @Column("first_name")
  String firstName,
  @Column("last_name")
  String lastName) {

  public Customer updateBy(Customer customer) {
    return new Customer(this.id, customer.firstName(), customer.lastName());
  }

}
