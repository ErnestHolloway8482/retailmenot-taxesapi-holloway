package modules;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        DAOModule.class,
        FacadeModule.class,
        ManagerModule.class,
        MapperModule.class,
        ControllerModule.class})
public interface AppComponent {
    AppComponent build();
}
