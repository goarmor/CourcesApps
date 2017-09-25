package ru.gdgkazan.popularmovies.model.content;

import java.util.List;

/**
 * Created by DIMON on 23.08.2017.
 */

public class VideoAndReview {

    private List<Review> reviews;
    private List<Video> videos;

    public VideoAndReview (List<Review> reviews, List<Video> videos)
    {
        this.reviews = reviews;
        this.videos = videos;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<Video> getVideos() {
        return videos;
    }
}
