package ru.gdgkazan.githubmvp.di;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;
import ru.gdgkazan.githubmvp.repository.GithubRepository;
import ru.gdgkazan.githubmvp.repository.KeyValueStorage;
import ru.gdgkazan.githubmvp.screen.auth.AuthActivity;
import ru.gdgkazan.githubmvp.screen.auth.AuthPresenter;

/**
 * Created by DIMON on 20.09.2017.
 */

@Module
public class AuthPresenterModule {

    @Provides
    @Singleton
    AuthPresenter provideAuthPresenter(@NonNull GithubRepository githubRepository, @NonNull KeyValueStorage keyValueStorage) {
        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(AuthActivity.authActivity, AuthActivity.loaderManager);
        return new AuthPresenter(githubRepository, keyValueStorage, lifecycleHandler, AuthActivity.authActivity);
    }

}
