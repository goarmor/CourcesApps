package ru.gdgkazan.popularmoviesclean.data.cache;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.gdgkazan.popularmoviesclean.data.model.content.Video;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by DIMON on 05.09.2017.
 */

public class VideosCacheTransformer implements Observable.Transformer<List<Video>, List<Video>> {

    private final Func1<List<Video>, Observable<List<Video>>> mSaveFunc = videos -> {
        Realm.getDefaultInstance().executeTransaction(realm -> {
            realm.delete(Video.class);
            realm.insert(videos);
        });
        return Observable.just(videos);
    };

    private final Func1<Throwable, Observable<List<Video>>> mCacheErrorHandler = throwable -> {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Video> results = realm.where(Video.class).findAll();
        return Observable.just(realm.copyFromRealm(results));
    };

    @Override
    public Observable<List<Video>> call(Observable<List<Video>> moviesObservable) {
        return moviesObservable
                .flatMap(mSaveFunc)
                .onErrorResumeNext(mCacheErrorHandler);
    }
}
