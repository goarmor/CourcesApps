package ru.gdgkazan.popularmoviesclean.domain.usecase;

import ru.gdgkazan.popularmoviesclean.domain.ReviewsAndVideosRepository;
import ru.gdgkazan.popularmoviesclean.domain.model.ReviewsAndVideos;
import rx.Observable;

/**
 * Created by DIMON on 05.09.2017.
 */

public class ReviewsAndVideosUseCase {
    private final ReviewsAndVideosRepository mRepository;
    private final Observable.Transformer<ReviewsAndVideos, ReviewsAndVideos> mAsyncTransformer;

    public ReviewsAndVideosUseCase(ReviewsAndVideosRepository repository,
                          Observable.Transformer<ReviewsAndVideos, ReviewsAndVideos> asyncTransformer) {
        mRepository = repository;
        mAsyncTransformer = asyncTransformer;
    }

    public Observable<ReviewsAndVideos> getReviewsAndVideos(int movie_id) {
        return mRepository.getReviewsAndVideos(movie_id)
                .compose(mAsyncTransformer);
    }
}
