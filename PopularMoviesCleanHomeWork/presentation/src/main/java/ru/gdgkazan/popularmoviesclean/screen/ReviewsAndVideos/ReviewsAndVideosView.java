package ru.gdgkazan.popularmoviesclean.screen.ReviewsAndVideos;

import android.support.annotation.NonNull;

import ru.gdgkazan.popularmoviesclean.domain.model.ReviewsAndVideos;
import ru.gdgkazan.popularmoviesclean.screen.general.LoadingView;

/**
 * Created by DIMON on 05.09.2017.
 */

public interface ReviewsAndVideosView extends LoadingView {
    void showReviewsAndVideos(@NonNull ReviewsAndVideos reviewsAndVideos);

    void showError();
}
