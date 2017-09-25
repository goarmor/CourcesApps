package ru.gdgkazan.githubmvp.screen.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.gdgkazan.githubmvp.AppDelegate;
import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.screen.general.LoadingDialog;
import ru.gdgkazan.githubmvp.screen.general.LoadingView;
import ru.gdgkazan.githubmvp.screen.repositories.RepositoriesActivity;

public class AuthActivity extends AppCompatActivity implements AuthView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.loginEdit)
    EditText mLoginEdit;

    @BindView(R.id.passwordEdit)
    EditText mPasswordEdit;

    @BindView(R.id.loginInputLayout)
    TextInputLayout mLoginInputLayout;

    @BindView(R.id.passwordInputLayout)
    TextInputLayout mPasswordInputLayout;

    private LoadingView mLoadingView;

    @Inject
    AuthPresenter mPresenter;

    public static AuthActivity authActivity;

    public static LoaderManager loaderManager;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, AuthActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        authActivity = this;
        loaderManager = getSupportLoaderManager();

        AppDelegate.getAppComponent().authComponent().injectPresenter(this);
        mLoadingView = LoadingDialog.view(getSupportFragmentManager());

        mPresenter.init();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.logInButton)
    public void onLogInButtonClick() {
        String login = mLoginEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();
        mPresenter.tryLogIn(login, password);
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
    public void showLoginError() {
        mLoginInputLayout.setError(getString(R.string.error));
    }

    @Override
    public void showPasswordError() {
        mPasswordInputLayout.setError(getString(R.string.error));
    }

    @Override
    public void openRepositoriesScreen() {
        RepositoriesActivity.start(this);
        finish();
    }


}
