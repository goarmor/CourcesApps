package ru.gdgkazan.githubmosby.screen.commits;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;
import ru.gdgkazan.githubmosby.R;
import ru.gdgkazan.githubmosby.content.Commit;
import ru.gdgkazan.githubmosby.content.Repository;
import ru.gdgkazan.githubmosby.screen.general.LoadingDialog;
import ru.gdgkazan.githubmosby.screen.general.LoadingView;
import ru.gdgkazan.githubmosby.widget.DividerItemDecoration;
import ru.gdgkazan.githubmosby.widget.EmptyRecyclerView;

/**
 * @author Artur Vasilov
 */
public class CommitsActivity extends MvpActivity<CommitsView, CommitsPresenter> implements CommitsView {

    private static final String REPO_NAME_KEY = "repo_name_key";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recyclerView)
    EmptyRecyclerView mRecyclerView;

    @BindView(R.id.empty)
    View mEmptyView;

    LoadingView mLoadingView;

    private CommitsAdapter mAdapter;

    private CommitsPresenter mPresenter;

    String repositoryName;

    public static void start(@NonNull Activity activity, @NonNull Repository repository) {
        Intent intent = new Intent(activity, CommitsActivity.class);
        intent.putExtra(REPO_NAME_KEY, repository.getName());
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        repositoryName = getIntent().getStringExtra(REPO_NAME_KEY);

        mLoadingView = LoadingDialog.view(getSupportFragmentManager());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setEmptyView(mEmptyView);

        mAdapter = new CommitsAdapter(new ArrayList<>());
        mAdapter.attachToRecyclerView(mRecyclerView);


        getPresenter().init(repositoryName);


        /**
         * TODO : task
         *
         * Load commits info and display them
         * Use MVP pattern for managing logic and UI and Repository for requests and caching
         *
         * API docs can be found here https://developer.github.com/v3/repos/commits/
         */
    }

    @NonNull
    @Override
    public CommitsPresenter createPresenter() {
        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        return new CommitsPresenter(lifecycleHandler);
    }

    @Override
    public void showLoading() {
        mLoadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        mLoadingView.hideLoading();
    }

    @Override
    public void showCommits(List<Commit> commits) {
        if (commits.isEmpty())
            Snackbar.make(mRecyclerView, "Not implemented for " + repositoryName + " yet", Snackbar.LENGTH_LONG).show();
        else mAdapter.changeDataSet(commits);
    }

    @Override
    public void showError() {
        mAdapter.clear();
    }
}
