package ru.gdgkazan.simpleweather.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public class WeatherCity implements Parcelable {

    private final int mCityId;

    private final String mCityName;

    public WeatherCity(int cityId, @NonNull String cityName) {
        mCityId = cityId;
        mCityName = cityName;
    }

    public int getCityId() {
        return mCityId;
    }

    @NonNull
    public String getCityName() {
        return mCityName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mCityId);
        dest.writeString(mCityName);
    }

    public static final Parcelable.Creator<WeatherCity> CREATOR = new Parcelable.Creator<WeatherCity>() {
        // распаковываем объект из Parcel
        public WeatherCity createFromParcel(Parcel in) {
            return new WeatherCity(in);
        }

        public WeatherCity[] newArray(int size) {
            return new WeatherCity[size];
        }
    };
    private WeatherCity(Parcel parcel) {
        mCityId = parcel.readInt();
        mCityName = parcel.readString();
    }
}
