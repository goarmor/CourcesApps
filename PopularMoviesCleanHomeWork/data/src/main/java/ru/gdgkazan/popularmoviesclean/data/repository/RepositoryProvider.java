package ru.gdgkazan.popularmoviesclean.data.repository;

import ru.gdgkazan.popularmoviesclean.domain.MoviesRepository;
import ru.gdgkazan.popularmoviesclean.domain.ReviewsAndVideosRepository;
import ru.gdgkazan.popularmoviesclean.domain.ReviewsRepository;
import ru.gdgkazan.popularmoviesclean.domain.VideosRepository;

/**
 * @author Artur Vasilov
 */
public class RepositoryProvider {

    private static MoviesRepository sMoviesRepository;
    private static ReviewsRepository sReviewsRepository;
    private static VideosRepository sVideosRepository;
    private static ReviewsAndVideosRepository sReviewsAndVideosRepository;

    public static MoviesRepository getMoviesRepository() {
        if (sMoviesRepository == null) {
            sMoviesRepository = new MoviesDataRepository();
        }
        return sMoviesRepository;
    }

    public static ReviewsAndVideosRepository getReviewsAndVideosRepository() {
        if (sReviewsAndVideosRepository == null) {
            sReviewsAndVideosRepository = new ReviewsAndVideosDataRepository();
        }
        return sReviewsAndVideosRepository;
    }

    public static ReviewsRepository getsReviewsRepository() {
        if (sReviewsRepository == null) {
            sReviewsRepository = new ReviewsDataRepository();
        }
        return sReviewsRepository;
    }

    public static VideosRepository getsVideosRepository() {
        if (sVideosRepository == null) {
            sVideosRepository = new VideosDataRepository();
        }
        return sVideosRepository;
    }

    public static void setsReviewsRepository(ReviewsRepository sReviewsRepository) {
        sReviewsRepository = sReviewsRepository;
    }

    public static void setsVideosRepository(VideosRepository sVideosRepository) {
        sVideosRepository = sVideosRepository;
    }

    public static void setMoviesRepository(MoviesRepository moviesRepository) {
        sMoviesRepository = moviesRepository;
    }


}
