package modules;


import dagger.Module;
import dagger.Provides;
import mappers.SalesTaxMapper;

/**
 * @author ernestholloway
 *
 * This is the module class for injecting mappers.
 */
@Module
public class MapperModule {
    @Provides
    SalesTaxMapper provideSalesTaxMapper() {
        return new SalesTaxMapper();
    }
}
