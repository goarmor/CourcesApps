package ru.gdgkazan.simpleweather.utils;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.gdgkazan.simpleweather.data.model.WeatherCity;

/**
 * Created by DIMON on 03.09.2017.
 */

public class WeatherListGenerator {
    private String text;
    private List<WeatherCity> wc;
    private Integer timesToExtract;

    public WeatherListGenerator(String text) {
        this.text = text;
        wc = getListOfWeatherCities(text);
    }

    public List<WeatherCity> getListOfWeatherCities(String text) {
        List<WeatherCity> weatherCities = new ArrayList<>();
        String idOfCity = "";
        String cityName = "";
        final int SIZE = text.length();
        for (int i = 0; i < SIZE; i++) {
            while (text.charAt(i) != '\n') {
                i++;
            }
            if(i != (SIZE - 1)) {
                i++;
            } else {
                System.out.println("i == length-1");
                return weatherCities;
            }
            while (text.charAt(i) != '\t') {
                idOfCity += text.charAt(i);
                i++;
            }
            i++;
            while (text.charAt(i) != '\t') {
                cityName += text.charAt(i);
                i++;
            }
            weatherCities.add(new WeatherCity(Integer.decode(idOfCity), cityName));
            idOfCity = "";
            cityName = "";
        }
        return weatherCities;

    }
    public String Next20WeatherCities()
    {
        List<WeatherCity> wcToSend = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if(!wc.isEmpty()) {
                wcToSend.add(wc.get(0));
                wc.remove(0);
            }

        }
        timesToExtract--;
        return convertToIDs(wcToSend);
    }
    public boolean isEmpty()
    {
        if (wc.isEmpty()) return true;
        else return false;
    }

    public Integer getTimesToExtract() {
        return timesToExtract;
    }

    public void setTimesToExtract(Integer timesToExtract) {
        this.timesToExtract = timesToExtract;
    }

    @NonNull
    private String convertToIDs (List<WeatherCity> wcs)
    {
        String IDs = "";
        Integer in;

        for (WeatherCity wc : wcs) {
            in = wc.getCityId();
            IDs += in.toString() + ",";
        }
        //substring is not work :(
        String newstr = "data/2.5/group?id=";
        for (int i = 0; i < IDs.length() - 1; i++)
            newstr += IDs.charAt(i);
               /* newstr += IDs.substring(0, IDs.length() - 2);*/
        return newstr;
    }
}
