package ru.gdgkazan.githubmvp.di;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;
import ru.gdgkazan.githubmvp.repository.GithubRepository;
import ru.gdgkazan.githubmvp.screen.repositories.RepositoriesActivity;
import ru.gdgkazan.githubmvp.screen.repositories.RepositoriesPresenter;

/**
 * Created by DIMON on 21.09.2017.
 */
@Module
public class RepositoriesPresenterModule {

    @Provides
    @Singleton
    RepositoriesPresenter provideRepositoriesPresenter(@NonNull GithubRepository githubRepository) {
        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(RepositoriesActivity.repositoriesActivity, RepositoriesActivity.loaderManager);
        return new RepositoriesPresenter(githubRepository,  lifecycleHandler, RepositoriesActivity.repositoriesActivity);
    }

}
