package ru.gdgkazan.githubmvp.di;

import javax.inject.Singleton;

import dagger.Subcomponent;
import ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughActivity;

/**
 * Created by DIMON on 20.09.2017.
 */

@Singleton
@Subcomponent(modules = {WalkthroughPresenterModule.class})
public interface WalkthroughComponent {

    void injectPresenter(WalkthroughActivity walkthroughActivity);

}
