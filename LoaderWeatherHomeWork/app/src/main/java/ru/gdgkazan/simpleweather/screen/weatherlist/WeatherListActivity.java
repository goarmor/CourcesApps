package ru.gdgkazan.simpleweather.screen.weatherlist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.gdgkazan.simpleweather.R;
import ru.gdgkazan.simpleweather.model.City;
import ru.gdgkazan.simpleweather.screen.general.LoadingDialog;
import ru.gdgkazan.simpleweather.screen.general.LoadingView;
import ru.gdgkazan.simpleweather.screen.general.SimpleDividerItemDecoration;
import ru.gdgkazan.simpleweather.screen.weather.RetrofitWeatherLoader;
import ru.gdgkazan.simpleweather.screen.weather.WeatherActivity;

/**
 * @author Artur Vasilov
 */
public class WeatherListActivity extends AppCompatActivity implements CitiesAdapter.OnItemClick, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.empty)
    View mEmptyView;

    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private CitiesAdapter mAdapter;

    private LoadingView mLoadingView;

    private List<City> cityes;

    private HashMap<String, City> cityesFind;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);
        cityesFind = new HashMap<>();
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimaryDark), getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.white));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this, false));
        mAdapter = new CitiesAdapter(getInitialCities(), this);
        cityes = getInitialCities();
        mRecyclerView.setAdapter(mAdapter);
        mLoadingView = LoadingDialog.view(getSupportFragmentManager());
        if (savedInstanceState != null) {
            cityesFind = (HashMap<String, City>)savedInstanceState.getSerializable("cityes");
        } else loadWeather(false);


        /**
         * TODO : task
         *
         * 1) Load all cities forecast using one or multiple loaders
         * 2) Try to run these requests as most parallel as possible
         * or better do as less requests as possible
         * 3) Show loading indicator during loading process
         * 4) Allow to update forecasts with SwipeRefreshLayout
         * 5) Handle configuration changes
         *
         * Note that for the start point you only have cities names, not ids,
         * so you can't load multiple cities in one request.
         *
         * But you should think how to manage this case. I suggest you to start from reading docs mindfully.
         */
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("cityes", cityesFind);

        super.onSaveInstanceState(outState);

    }

    private void loadWeather(boolean restart) {
        mLoadingView.showLoadingIndicator();
        LoaderManager.LoaderCallbacks<List<City>> callbacks = new WeatherListActivity.WeatherCallbacks();
        if (restart) {
            getSupportLoaderManager().restartLoader(R.id.weather_loader_id, Bundle.EMPTY, callbacks);
        } else {
           getSupportLoaderManager().initLoader(R.id.weather_loader_id, Bundle.EMPTY, callbacks);

        }
        mLoadingView.hideLoadingIndicator();
    }


    @Override
    public void onItemClick(@NonNull City city) {
        startActivity(WeatherActivity.makeIntent(this, cityesFind.get(city.getName())));
    }

    @NonNull
    private List<City> getInitialCities() {
        List<City> cities = new ArrayList<>();
        String[] initialCities = getResources().getStringArray(R.array.initial_cities);
        for (String city : initialCities) {
            cities.add(new City(city));
        }
        return cities;
    }

    @Override
    public void onRefresh() {
        System.out.println("RefreshLayout");
        loadWeather(true);
        refreshLayout.setRefreshing(false);

    }

    private class WeatherCallbacks implements LoaderManager.LoaderCallbacks<List<City>> {

        @Override
        public Loader<List<City>> onCreateLoader(int id, Bundle args) {
            System.out.println("Start load");
            return new RetrofitWeatherLoader(WeatherListActivity.this, cityes);
        }

        @Override
        public void onLoadFinished(Loader<List<City>> loader, List<City> cityes) {
            for (City c: cityes)
                cityesFind.put(c.getName(), c);

        }

        @Override
        public void onLoaderReset(Loader<List<City>> loader) {
            // Do nothing
        }
    }
}
