package modules;

import application.SalesTaxAPIApplication;
import controllers.SalesTaxRestController;
import dagger.BindsInstance;
import dagger.Component;
import facades.SalesTaxSeederFacade;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        DAOModule.class,
        FacadeModule.class,
        ManagerModule.class,
        MapperModule.class,
        SalesTaxRestController.class})
public interface AppComponent {
    void inject(SalesTaxSeederFacade salesTaxSeederFacade);

    @Component.Builder
    public interface Builder {
        @BindsInstance
        Builder application(SalesTaxAPIApplication application);

        AppComponent build();
    }
}
