package ru.gdgkazan.popularmoviesclean.domain;

import java.util.List;

import ru.gdgkazan.popularmoviesclean.domain.model.Video;
import rx.Observable;

/**
 * Created by DIMON on 05.09.2017.
 */

public interface VideosRepository {

    Observable<List<Video>> getVideos(int movie_id);

}
