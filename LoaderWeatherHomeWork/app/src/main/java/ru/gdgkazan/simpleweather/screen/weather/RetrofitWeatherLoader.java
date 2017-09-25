package ru.gdgkazan.simpleweather.screen.weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.gdgkazan.simpleweather.model.City;
import ru.gdgkazan.simpleweather.network.ApiFactory;

/**
 * @author Artur Vasilov
 */
public class RetrofitWeatherLoader extends Loader<List<City>> {

    private Call<City>[] mCall;

    @Nullable
    private List<City> cities;

    public RetrofitWeatherLoader(Context context, @NonNull List<City> cityes) {
        super(context);
        mCall = new Call[cityes.size()];
        for (int i = 0; i < cityes.size(); i++) {
            mCall[i] = ApiFactory.getWeatherService().getWeather(cityes.get(i).getName());
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (cities != null) {
            deliverResult(cities);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onForceLoad() {
        cities = new ArrayList<>();
        Callback<City> cityCallback = new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                cities.add(response.body());
                if (cities.size() == mCall.length)
                    deliverResult(cities);
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                deliverResult(null);
            }
        };
        super.onForceLoad();
        for (int i = 0; i < mCall.length; i++) {
            mCall[i].enqueue(cityCallback);
        }
    }

    @Override
    protected void onStopLoading() {
        for (int i = 0; i < mCall.length; i++)
            mCall[i].cancel();

        super.onStopLoading();
    }
}

