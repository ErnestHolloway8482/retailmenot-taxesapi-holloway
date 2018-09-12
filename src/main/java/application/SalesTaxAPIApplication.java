package application;

import controllers.SalesTaxRestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ComponentScan(basePackageClasses = SalesTaxRestController.class)
@EnableAutoConfiguration
@EnableWebMvc
@SpringBootApplication
public class SalesTaxAPIApplication {
    public static void main(String[] args) {
        SpringApplication.run(application.SalesTaxAPIApplication.class, args);
    }
}
