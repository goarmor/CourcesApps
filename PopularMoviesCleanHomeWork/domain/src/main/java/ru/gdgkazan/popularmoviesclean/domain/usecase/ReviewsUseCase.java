package ru.gdgkazan.popularmoviesclean.domain.usecase;

import java.util.List;

import ru.gdgkazan.popularmoviesclean.domain.ReviewsRepository;
import ru.gdgkazan.popularmoviesclean.domain.model.Review;
import rx.Observable;

/**
 * Created by DIMON on 05.09.2017.
 */

public class ReviewsUseCase {

    private final ReviewsRepository mRepository;
    private final Observable.Transformer<List<Review>, List<Review>> mAsyncTransformer;

    public ReviewsUseCase(ReviewsRepository repository,
                         Observable.Transformer<List<Review>, List<Review>> asyncTransformer) {
        mRepository = repository;
        mAsyncTransformer = asyncTransformer;
    }

    public Observable<List<Review>> getReviews(int movie_id) {
        return mRepository.getReviews(movie_id)
                .compose(mAsyncTransformer);
    }

}
