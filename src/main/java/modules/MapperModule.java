package modules;


import dagger.Module;
import dagger.Provides;
import mappers.SalesTaxMapper;

@Module
public class MapperModule {
    @Provides
    SalesTaxMapper provideSalesTaxMapper(){
        return new SalesTaxMapper();
    }
}
