package ru.gdgkazan.popularmoviesclean.domain;

import java.util.List;

import ru.gdgkazan.popularmoviesclean.domain.model.Review;
import rx.Observable;

/**
 * Created by DIMON on 05.09.2017.
 */

public interface ReviewsRepository {

    Observable<List<Review>> getReviews(int movie_id);

}
