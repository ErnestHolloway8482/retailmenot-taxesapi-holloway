package modules;

import dagger.Module;
import dagger.Provides;
import daos.SalesTaxDAO;
import facades.SalesTaxSeederFacade;
import managers.ObjectDBManager;
import managers.SalesTaxFileManager;
import mappers.SalesTaxMapper;

import javax.inject.Singleton;

@Module
public class FacadeModule {

    @Singleton
    @Provides
    SalesTaxSeederFacade provideSalesTaxSeederFacade(final SalesTaxFileManager salesTaxFileManager, final SalesTaxMapper salesTaxMapper, final SalesTaxDAO salesTaxDAO, final ObjectDBManager objectDBManager) {
        return new SalesTaxSeederFacade(salesTaxFileManager, salesTaxMapper, salesTaxDAO, objectDBManager);
    }
}