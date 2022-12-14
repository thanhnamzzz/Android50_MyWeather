package com.myweather;

import com.google.gson.JsonObject;
import com.myweather.hours_weather.ForecastWeather;
import com.myweather.models.CurrentWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherServices {

//    @GET("weather?")
////muốn điền nội dung gì đó có thay đổi trong link thì cho nó vào trong {}
//    Call<JsonObject> getWeatherByCityName(@Query("q") String cityName, @Query("appid") String apiKey);

    @GET("weather?")
    Call<CurrentWeather> getWeatherByCityNameModel(@Query("q") String cityName, @Query("appid") String apiKey);

    @GET("forecast?")
    Call<ForecastWeather> getWeatherByCityNameHours(@Query("q") String cityName, @Query("appid") String apiKey);

//    @GET("forecast?")
//    Call<JsonObject> getWeatherByCityNameHoursWeather(@Query("q") String cityName, @Query("appid") String apiKey);
}
