package ru.gdgkazan.githubmvp.di;

import javax.inject.Singleton;

import dagger.Subcomponent;
import ru.gdgkazan.githubmvp.screen.auth.AuthActivity;

/**
 * Created by DIMON on 20.09.2017.
 */

@Singleton
@Subcomponent (modules = {AuthPresenterModule.class})
public interface AuthComponent {

    void injectPresenter(AuthActivity authActivity);


}
