package ru.gdgkazan.githubmvp.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.gdgkazan.githubmvp.repository.KeyValueStorage;

/**
 * @author Artur Vasilov
 */
@Singleton
@Component(modules = {DataModule.class})
public interface AppComponent {

    KeyValueStorage keyValueStorage();

    AuthComponent authComponent();

    RepositoriesComponent repositoriesComponent();

    WalkthroughComponent walkthroughComponent();

}
