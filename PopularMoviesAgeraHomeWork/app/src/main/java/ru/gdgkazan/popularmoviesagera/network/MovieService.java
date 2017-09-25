package ru.gdgkazan.popularmoviesagera.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.gdgkazan.popularmoviesagera.model.response.MoviesResponse;
import ru.gdgkazan.popularmoviesagera.model.response.ReviewsResponse;
import ru.gdgkazan.popularmoviesagera.model.response.VideosResponse;

/**
 * @author Artur Vasilov
 */
public interface MovieService {

    @GET("popular/")
    Call<MoviesResponse> popularMovies();
    @GET("{movie_id}/videos")
    Call<VideosResponse> getVideos(@Path("movie_id") int movie_id);
    @GET("{movie_id}/reviews")
    Call<ReviewsResponse> getReviews(@Path("movie_id") int movie_id);

}
