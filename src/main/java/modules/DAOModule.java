package modules;

import dagger.Module;
import dagger.Provides;
import daos.ISalesTaxDAO;
import daos.SalesTaxDAO;
import managers.ObjectDBManager;

/**
 * @author ernestholloway
 *
 * This is a module class for injecting database access objects (DAO's) for corresponding database objects.
 */
@Module
public class DAOModule {
    @Provides
    public static ISalesTaxDAO provideSalesTaxDAO(final ObjectDBManager objectDBManager) {
        return new SalesTaxDAO(objectDBManager);
    }
}
