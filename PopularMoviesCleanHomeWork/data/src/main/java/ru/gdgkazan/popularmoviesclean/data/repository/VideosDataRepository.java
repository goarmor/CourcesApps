package ru.gdgkazan.popularmoviesclean.data.repository;

import java.util.List;

import ru.gdgkazan.popularmoviesclean.data.cache.VideosCacheTransformer;
import ru.gdgkazan.popularmoviesclean.data.mapper.VideosMapper;
import ru.gdgkazan.popularmoviesclean.data.model.response.VideosResponse;
import ru.gdgkazan.popularmoviesclean.data.network.ApiFactory;
import ru.gdgkazan.popularmoviesclean.domain.VideosRepository;
import ru.gdgkazan.popularmoviesclean.domain.model.Video;
import rx.Observable;

/**
 * Created by DIMON on 05.09.2017.
 */

public class VideosDataRepository implements VideosRepository {

    @Override
    public Observable<List<Video>> getVideos(int movie_id) {
        return ApiFactory.getMoviesService()
                .getVideos(movie_id)
                .map(VideosResponse::getVideos)
                .compose(new VideosCacheTransformer())
                .flatMap(Observable::from)
                .map(new VideosMapper())
                .toList();
    }
}
