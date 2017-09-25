package ru.gdgkazan.githubmosby.repository;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.arturvasilov.rxloader.RxUtils;
import ru.gdgkazan.githubmosby.api.ApiFactory;
import ru.gdgkazan.githubmosby.content.Authorization;
import ru.gdgkazan.githubmosby.content.Commit;
import ru.gdgkazan.githubmosby.content.CommitResponse;
import ru.gdgkazan.githubmosby.content.Repository;
import ru.gdgkazan.githubmosby.utils.AuthorizationUtils;
import ru.gdgkazan.githubmosby.utils.PreferenceUtils;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class DefaultGithubRepository implements GithubRepository {

    @NonNull
    @Override
    public Observable<List<Repository>> repositories() {
        return ApiFactory.getGithubService()
                .repositories()
                .flatMap(repositories -> {
                    Realm.getDefaultInstance().executeTransaction(realm -> {
                        realm.delete(Repository.class);
                        realm.insert(repositories);
                    });
                    return Observable.just(repositories);
                })
                .onErrorResumeNext(throwable -> {
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<Repository> repositories = realm.where(Repository.class).findAll();
                    return Observable.just(realm.copyFromRealm(repositories));
                })
                .compose(RxUtils.async());
    }

    @NonNull
    public Observable<Authorization> auth(@NonNull String login, @NonNull String password) {
        String authorizationString = AuthorizationUtils.createAuthorizationString(login, password);
        return ApiFactory.getGithubService()
                .authorize(authorizationString, AuthorizationUtils.createAuthorizationParam())
                .flatMap(authorization -> {
                    PreferenceUtils.saveToken(authorization.getToken());
                    PreferenceUtils.saveUserName(login);
                    ApiFactory.recreate();
                    return Observable.just(authorization);
                })
                .compose(RxUtils.async());
    }
    @NonNull
    public Observable<List<Commit>> commits(@NonNull String user, @NonNull String repo) {
        return ApiFactory.getGithubService()
                .commits(user, repo)
                .flatMap(commitsResponses -> {
                    List<Commit> commits = new ArrayList<Commit>();
                    for (CommitResponse cr: commitsResponses) {
                        commits.add(cr.getCommit());
                    }
                    Realm.getDefaultInstance().executeTransaction(realm -> {
                        realm.delete(Commit.class);
                        realm.insert(commits);
                    });
                    return Observable.just(commits);
                })
                .onErrorResumeNext(throwable -> {
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<Commit> commits = realm.where(Commit.class).findAll();
                    return Observable.just(realm.copyFromRealm(commits));
                })
                .compose(RxUtils.async());
    }
}
