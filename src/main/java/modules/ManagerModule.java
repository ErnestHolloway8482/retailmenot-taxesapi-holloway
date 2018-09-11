package modules;

import dagger.Module;
import dagger.Provides;
import managers.ObjectDBManager;
import managers.SalesTaxFileManager;

import javax.inject.Singleton;

/**
 * @author ernestholloway
 *
 * This is the module class for injecting manager type objects.
 */
@Module
public class ManagerModule {

    @Singleton
    @Provides
    ObjectDBManager provideObjectDBManager() {
        return new ObjectDBManager();
    }

    @Singleton
    @Provides
    SalesTaxFileManager provideSalesTaxFileManager() {
        return new SalesTaxFileManager();
    }
}
