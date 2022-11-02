package cane.brothers.spring;

import net.lecousin.reactive.data.relational.LcReactiveDataRelationalInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    LcReactiveDataRelationalInitializer.init();
    SpringApplication.run(Application.class, args);
  }

}
