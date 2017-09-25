package ru.gdgkazan.githubmvp.di;

import javax.inject.Singleton;

import dagger.Subcomponent;
import ru.gdgkazan.githubmvp.screen.repositories.RepositoriesActivity;

/**
 * Created by DIMON on 21.09.2017.
 */
@Singleton
@Subcomponent(modules = {RepositoriesPresenterModule.class})
public interface RepositoriesComponent {

    void injectPresenter(RepositoriesActivity repositoriesActivity);

}
