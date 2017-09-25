package ru.gdgkazan.simpleweather.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DIMON on 01.09.2017.
 */

public class CitiesWeathersResponse {
    @SerializedName("cnt")
    int cnt;
    @SerializedName("list")
    List<City> cities;

    public int getCnt() {
        return cnt;
    }

    public List<City> getCities() {
        return cities;
    }
}
