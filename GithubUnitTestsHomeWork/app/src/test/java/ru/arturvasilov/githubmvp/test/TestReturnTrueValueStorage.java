package ru.arturvasilov.githubmvp.test;

import android.support.annotation.NonNull;

import ru.gdgkazan.githubmvp.repository.KeyValueStorage;
import rx.Observable;

/**
 * Created by DIMON on 17.09.2017.
 */

public class TestReturnTrueValueStorage implements KeyValueStorage {
    @Override
    public void saveToken(@NonNull String token) {

    }

    @NonNull
    @Override
    public String getToken() {
        return null;
    }

    @Override
    public void saveUserName(@NonNull String userName) {

    }

    @NonNull
    @Override
    public Observable<String> getUserName() {
        return null;
    }

    @Override
    public void saveWalkthroughPassed() {

    }

    @Override
    public boolean isWalkthroughPassed() {
        return true;
    }
}
