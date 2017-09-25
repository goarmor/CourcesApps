package ru.gdgkazan.githubmosby.screen.commits;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdgkazan.githubmosby.R;
import ru.gdgkazan.githubmosby.repository.RepositoryProvider;
import ru.gdgkazan.githubmosby.utils.PreferenceUtils;

/**
 * Created by DIMON on 08.09.2017.
 */

public class CommitsPresenter extends MvpBasePresenter<CommitsView> {

    private final LifecycleHandler mLifecycleHandler;

    public CommitsPresenter(@NonNull LifecycleHandler lifecycleHandler) {
        mLifecycleHandler = lifecycleHandler;
    }

    void init(String repositoryName) {
        if (!isViewAttached() || getView() == null) {
            return;
        }
        RepositoryProvider.provideGithubRepository()
                .commits(PreferenceUtils.getUserName(), repositoryName)
                .doOnSubscribe(getView()::showLoading)
                .doOnTerminate(getView()::hideLoading)
                .compose(mLifecycleHandler.load(R.id.commits_request))
                .subscribe(getView()::showCommits, throwable -> getView().showError());
    }
}
