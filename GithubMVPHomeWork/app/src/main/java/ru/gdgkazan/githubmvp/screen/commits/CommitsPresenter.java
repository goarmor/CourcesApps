package ru.gdgkazan.githubmvp.screen.commits;

import android.support.annotation.NonNull;

import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;
import ru.gdgkazan.githubmvp.utils.PreferenceUtils;

/**
 * Created by DIMON on 08.09.2017.
 */

public class CommitsPresenter {

    private final LifecycleHandler mLifecycleHandler;
    private final CommitsView mView;

    public CommitsPresenter(@NonNull LifecycleHandler lifecycleHandler,
                                @NonNull CommitsView view) {
        mLifecycleHandler = lifecycleHandler;
        mView = view;
    }

    void init(String repositoryName) {
        RepositoryProvider.provideGithubRepository()
                .commits(PreferenceUtils.getUserName(), repositoryName)
                .doOnSubscribe(mView::showLoading)
                .doOnTerminate(mView::hideLoading)
                .compose(mLifecycleHandler.load(R.id.commits_request))
                .subscribe(mView::showCommits, throwable -> mView.showError());
    }
}
