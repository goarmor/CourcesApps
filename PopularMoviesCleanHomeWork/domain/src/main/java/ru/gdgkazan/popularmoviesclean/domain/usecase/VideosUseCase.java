package ru.gdgkazan.popularmoviesclean.domain.usecase;

import java.util.List;

import ru.gdgkazan.popularmoviesclean.domain.VideosRepository;
import ru.gdgkazan.popularmoviesclean.domain.model.Video;
import rx.Observable;

/**
 * Created by DIMON on 05.09.2017.
 */

public class VideosUseCase {

    private final VideosRepository mRepository;
    private final Observable.Transformer<List<Video>, List<Video>> mAsyncTransformer;

    public VideosUseCase(VideosRepository repository,
                         Observable.Transformer<List<Video>, List<Video>> asyncTransformer) {
        mRepository = repository;
        mAsyncTransformer = asyncTransformer;
    }

    public Observable<List<Video>> getVideos(int movie_id) {
        return mRepository.getVideos(movie_id)
                .compose(mAsyncTransformer);
    }

}
