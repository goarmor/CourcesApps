package ru.gdgkazan.popularmoviesclean.screen.ReviewsAndVideos;

import android.support.annotation.NonNull;

import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdgkazan.popularmoviesclean.R;
import ru.gdgkazan.popularmoviesclean.domain.usecase.ReviewsAndVideosUseCase;

/**
 * Created by DIMON on 05.09.2017.
 */

public class ReviewsAndVideosPresenter {
    private final ReviewsAndVideosView mReviewsAndVideosView;
    private final ReviewsAndVideosUseCase mReviewsAndVideosUseCase;
    private final LifecycleHandler mLifecycleHandler;
    private int movie_id;

    public ReviewsAndVideosPresenter(@NonNull ReviewsAndVideosView reviewsAndVideosView, @NonNull ReviewsAndVideosUseCase reviewsAndVideosUseCase,
                           @NonNull LifecycleHandler lifecycleHandler, int movie_id) {
        mReviewsAndVideosView = reviewsAndVideosView;
        mReviewsAndVideosUseCase = reviewsAndVideosUseCase;
        mLifecycleHandler = lifecycleHandler;
        this.movie_id = movie_id;
    }

    public void init() {
        mReviewsAndVideosUseCase.getReviewsAndVideos(movie_id)
                .doOnSubscribe(mReviewsAndVideosView::showLoadingIndicator)
                .doAfterTerminate(mReviewsAndVideosView::hideLoadingIndicator)
                .compose(mLifecycleHandler.load(R.id.movies_request_id))
                .subscribe(mReviewsAndVideosView::showReviewsAndVideos, throwable -> mReviewsAndVideosView.showError());
    }
}
