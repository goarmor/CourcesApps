package ru.gdgkazan.popularmoviesclean.data.repository;

import java.util.List;

import ru.gdgkazan.popularmoviesclean.data.cache.ReviewsCacheTransformer;
import ru.gdgkazan.popularmoviesclean.data.mapper.ReviewsMapper;
import ru.gdgkazan.popularmoviesclean.data.model.response.ReviewsResponse;
import ru.gdgkazan.popularmoviesclean.data.network.ApiFactory;
import ru.gdgkazan.popularmoviesclean.domain.ReviewsRepository;
import ru.gdgkazan.popularmoviesclean.domain.model.Review;
import rx.Observable;

/**
 * Created by DIMON on 05.09.2017.
 */

public class ReviewsDataRepository implements ReviewsRepository {

    @Override
    public Observable<List<Review>> getReviews(int movie_id) {
        return ApiFactory.getMoviesService()
                .getReviews(movie_id)
                .map(ReviewsResponse::getReviews)
                .compose(new ReviewsCacheTransformer())
                .flatMap(Observable::from)
                .map(new ReviewsMapper())
                .toList();
    }
}
