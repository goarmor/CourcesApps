package ru.gdgkazan.popularmovies.screen.details;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import ru.gdgkazan.popularmovies.R;
import ru.gdgkazan.popularmovies.model.content.Movie;
import ru.gdgkazan.popularmovies.model.content.Review;
import ru.gdgkazan.popularmovies.model.content.Video;
import ru.gdgkazan.popularmovies.model.content.VideoAndReview;
import ru.gdgkazan.popularmovies.model.response.ReviewsResponse;
import ru.gdgkazan.popularmovies.model.response.VideosResponse;
import ru.gdgkazan.popularmovies.screen.loading.LoadingDialog;
import ru.gdgkazan.popularmovies.screen.loading.LoadingView;
import ru.gdgkazan.popularmovies.screen.movies.MoviesActivity;
import ru.gdgkazan.popularmovies.utils.Images;
import ru.gdgkazan.popularmovies.utils.ReviewInLayout;
import ru.gdgkazan.popularmovies.utils.Videos;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String MAXIMUM_RATING = "10";

    public static final String IMAGE = "image";
    public static final String EXTRA_MOVIE = "extraMovie";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbar;

    @BindView(R.id.image)
    ImageView mImage;

    @BindView(R.id.title)
    TextView mTitleTextView;

    @BindView(R.id.overview)
    TextView mOverviewTextView;

    @BindView(R.id.rating)
    TextView mRatingTextView;

    Observable<VideoAndReview> observable;

    Subscription mSubscription;

    List<Video> videos;

    public static void navigate(@NonNull AppCompatActivity activity, @NonNull View transitionImage,
                                @NonNull Movie movie) {
        Intent intent = new Intent(activity, MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareWindowForAnimation();
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        ViewCompat.setTransitionName(findViewById(R.id.app_bar), IMAGE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        downloadVideosAndReviews(movie, savedInstanceState);

        /**
         * TODO : task
         *
         * Load movie trailers and reviews and display them
         *
         * 1) See http://docs.themoviedb.apiary.io/#reference/movies/movieidtranslations/get?console=1
         * http://docs.themoviedb.apiary.io/#reference/movies/movieidtranslations/get?console=1
         * for API documentation
         *
         * 2) Add requests to {@link ru.gdgkazan.popularmovies.network.MovieService} for trailers and videos
         *
         * 3) Execute requests in parallel and show loading progress until both of them are finished
         *
         * 4) Save trailers and videos to Realm and use cached version when error occurred
         *
         * 5) Handle lifecycle changes any way you like
         */
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSubscription != null)
        mSubscription.unsubscribe();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (observable != null)
        mSubscription = observable.subscribe(this::showVideosAndReviews);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void prepareWindowForAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    private void downloadVideosAndReviews(@NonNull Movie movie, Bundle saveInstanceState)
    {
        LoadingView loadingView = LoadingDialog.view(getSupportFragmentManager());
        Observable<VideosResponse> videosResponseObservable = MoviesActivity.movieService.getVideos(movie.getId());
        Observable<ReviewsResponse> reviewsResponseObservable = MoviesActivity.movieService.getReviews(movie.getId());
        if (saveInstanceState != null) {
            Realm realm = Realm.getDefaultInstance();
            RealmResults<Video> videosRes = realm.where(Video.class).findAll();
            RealmResults<Review> reviewsRes = realm.where(Review.class).findAll();
            observable = Observable.just(new VideoAndReview(reviewsRes, videosRes))
                    .doOnSubscribe(loadingView::showLoadingIndicator)
                    .doAfterTerminate(loadingView::hideLoadingIndicator)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            mSubscription = observable.subscribe(this::showVideosAndReviews);
        } else {
            observable = Observable.zip(videosResponseObservable, reviewsResponseObservable, this::createVideoAndReview)
                    .flatMap(videoAndReview -> {
                        List<Video> videos = videoAndReview.getVideos();
                        List<Review> reviews = videoAndReview.getReviews();
                        Realm.getDefaultInstance().executeTransaction(realm -> {
                            realm.delete(Video.class);
                            realm.insert(videos);
                            realm.delete(Review.class);
                            realm.insert(reviews);
                        });

                        return Observable.just(videoAndReview);
                    })
                    .onErrorResumeNext(throwable -> {
                        Realm realm = Realm.getDefaultInstance();
                        RealmResults<Video> videosRes = realm.where(Video.class).findAll();
                        RealmResults<Review> reviewsRes = realm.where(Review.class).findAll();
                        return Observable.just(new VideoAndReview(reviewsRes, videosRes));
                    })
                    .doOnSubscribe(loadingView::showLoadingIndicator)
                    .doAfterTerminate(loadingView::hideLoadingIndicator)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            mSubscription = observable.subscribe(this::showVideosAndReviews);
        }

        showMovie(movie);
    }

    @OnClick(R.id.showTrailers)
    public void showDialog(View v)
    {
       AlertDialog().show();
    }


    private AlertDialog.Builder AlertDialog()
    {
        String[] items = new String[videos.size()];
        for (int i = 0; i < videos.size(); i++)
        {
            items[i] = videos.get(i).getName();
        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MovieDetailsActivity.this);
        alertDialog.setTitle("Select Trailers");

        alertDialog.setItems(items, (dialog, witch) -> {
            Video vi = videos.get(witch);
            Videos.browseVideo(getBaseContext(), vi);
        });
        return alertDialog;
    }



    private void showVideosAndReviews(VideoAndReview VAR)
    {
        videos = VAR.getVideos();
        ReviewInLayout ril = new ReviewInLayout((LinearLayout) findViewById(R.id.movie_details), VAR.getReviews());
        ril.setReviewsInLayout();
    }

    private void showMovie(@NonNull Movie movie) {
        String title = getString(R.string.movie_details);
        mCollapsingToolbar.setTitle(title);
        mCollapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));

        Images.loadMovie(mImage, movie, Images.WIDTH_780);

        String year = movie.getReleasedDate().substring(0, 4);
        mTitleTextView.setText(getString(R.string.movie_title, movie.getTitle(), year));
        mOverviewTextView.setText(movie.getOverview());

        String average = String.valueOf(movie.getVoteAverage());
        average = average.length() > 3 ? average.substring(0, 3) : average;
        average = average.length() == 3 && average.charAt(2) == '0' ? average.substring(0, 1) : average;
        mRatingTextView.setText(getString(R.string.rating, average, MAXIMUM_RATING));
    }


    private VideoAndReview createVideoAndReview(VideosResponse mVideos, ReviewsResponse mReviews) {
           return new VideoAndReview(mReviews.getReviews(), mVideos.getVideos());
    }

}
