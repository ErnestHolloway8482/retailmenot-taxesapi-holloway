package application;

import facades.SalesTaxSeederFacade;
import modules.AppComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.inject.Inject;

@SpringBootApplication
public class SalesTaxAPIApplication {
    private static AppComponent appComponent;

    @Inject
    SalesTaxSeederFacade salesTaxSeederFacade;

    public static void main(String[] args) {
        //Sets up the Dagger dependency injection graph for the entire application.
//        appComponent = DaggerAppComponent.builder().application(this).build();

        SpringApplication.run(application.SalesTaxAPIApplication.class, args);
    }
}
