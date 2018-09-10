package modules;

import dagger.Module;
import dagger.Provides;
import daos.ISalesTaxDAO;
import daos.SalesTaxDAO;
import managers.ObjectDBManager;

@Module
public class DAOModule {
    @Provides
    public static ISalesTaxDAO provideSalesTaxDAO(final ObjectDBManager objectDBManager){
        return new SalesTaxDAO(objectDBManager);
    }
}
