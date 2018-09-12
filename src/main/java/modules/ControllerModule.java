package modules;

import controllers.SalesTaxRestController;
import dagger.Module;
import dagger.Provides;
import facades.SalesTaxSeederFacade;

/**
 * @author ernestholloway
 * <p>
 * This is a module class for injecting Spring based RESTFUL API Controllers.
 */
@Module
public class ControllerModule {
    @Provides
    SalesTaxRestController provideSalesTaxRestController() {
        return new SalesTaxRestController();
    }
}
