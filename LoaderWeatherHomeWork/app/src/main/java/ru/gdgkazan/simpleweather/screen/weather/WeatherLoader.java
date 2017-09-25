package ru.gdgkazan.simpleweather.screen.weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.gdgkazan.simpleweather.model.City;
import ru.gdgkazan.simpleweather.network.ApiFactory;

/**
 * @author Artur Vasilov
 */
public class WeatherLoader extends AsyncTaskLoader<List<City>> {

    private final List<City> cityes;

    public WeatherLoader(Context context, @NonNull List<City> cityNames) {
        super(context);
        cityes = cityNames;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<City> loadInBackground() {
        List<City> citiesRet = new ArrayList<>();
        try {
            for (int i = 0; i < cityes.size(); i++) {
                citiesRet.add(ApiFactory.getWeatherService().getWeather(cityes.get(i).getName()).execute().body());
            }
            return citiesRet;
        } catch (IOException e) {
            return null;
        }
    }
}


