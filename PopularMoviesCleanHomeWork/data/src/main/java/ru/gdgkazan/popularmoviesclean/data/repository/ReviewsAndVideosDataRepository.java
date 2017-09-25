package ru.gdgkazan.popularmoviesclean.data.repository;

import java.util.List;

import ru.gdgkazan.popularmoviesclean.data.cache.ReviewsCacheTransformer;
import ru.gdgkazan.popularmoviesclean.data.cache.VideosCacheTransformer;
import ru.gdgkazan.popularmoviesclean.data.mapper.ReviewsMapper;
import ru.gdgkazan.popularmoviesclean.data.mapper.VideosMapper;
import ru.gdgkazan.popularmoviesclean.data.model.response.ReviewsResponse;
import ru.gdgkazan.popularmoviesclean.data.model.response.VideosResponse;
import ru.gdgkazan.popularmoviesclean.data.network.ApiFactory;
import ru.gdgkazan.popularmoviesclean.domain.ReviewsAndVideosRepository;
import ru.gdgkazan.popularmoviesclean.domain.model.Review;
import ru.gdgkazan.popularmoviesclean.domain.model.ReviewsAndVideos;
import ru.gdgkazan.popularmoviesclean.domain.model.Video;
import rx.Observable;

/**
 * Created by DIMON on 05.09.2017.
 */

public class ReviewsAndVideosDataRepository implements ReviewsAndVideosRepository {
    @Override
    public Observable<ReviewsAndVideos> getReviewsAndVideos(int movie_id) {
       Observable<List<Review>> observableReviews = ApiFactory.getMoviesService()
                .getReviews(movie_id)
                .map(ReviewsResponse::getReviews)
                .compose(new ReviewsCacheTransformer())
                .flatMap(Observable::from)
                .map(new ReviewsMapper())
                .toList();
        Observable<List<Video>> observableVideos = ApiFactory.getMoviesService()
                .getVideos(movie_id)
                .map(VideosResponse::getVideos)
                .compose(new VideosCacheTransformer())
                .flatMap(Observable::from)
                .map(new VideosMapper())
                .toList();

        Observable<ReviewsAndVideos> observable = Observable.zip(observableReviews, observableVideos, (reviews, videos) -> {
            return new ReviewsAndVideos(reviews, videos);
        });
        return observable;
    }
}
