package ru.gdgkazan.popularmoviesclean.domain;

import rx.Observable;

/**
 * Created by DIMON on 05.09.2017.
 */

public interface ReviewsAndVideosRepository {

    Observable<ru.gdgkazan.popularmoviesclean.domain.model.ReviewsAndVideos> getReviewsAndVideos(int movie_id);

}
