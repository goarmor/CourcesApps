package ru.gdgkazan.githubmvp.di;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.gdgkazan.githubmvp.repository.KeyValueStorage;
import ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughActivity;
import ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughPresenter;

/**
 * Created by DIMON on 20.09.2017.
 */

@Module
public class WalkthroughPresenterModule {

    @Provides
    @Singleton
    WalkthroughPresenter provideWalkthroughPresenter(@NonNull KeyValueStorage keyValueStorage) {
        return new WalkthroughPresenter(keyValueStorage, WalkthroughActivity.walkthroughActivity);
    }

}
