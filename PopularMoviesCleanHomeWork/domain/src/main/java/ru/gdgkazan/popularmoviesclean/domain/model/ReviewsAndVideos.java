package ru.gdgkazan.popularmoviesclean.domain.model;

import java.util.List;

/**
 * Created by DIMON on 05.09.2017.
 */

public class ReviewsAndVideos {
    private List<Review> reviews;
    private List<Video> videos;

    public ReviewsAndVideos(List<Review> reviews, List<Video> videos) {
        this.reviews = reviews;
        this.videos = videos;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
