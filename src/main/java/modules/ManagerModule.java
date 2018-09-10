package modules;

import dagger.Module;
import dagger.Provides;
import managers.ObjectDBManager;
import managers.SalesTaxFileManager;

import javax.inject.Singleton;

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
