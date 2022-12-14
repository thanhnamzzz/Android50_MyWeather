package com.myweather;

public class Global {

    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    public static final String API_KEY_M = "b272f357dce93b64982cc431978d6f01";//My API
//    public static final String API_KEY = "fdfe4db14d94b779bbc518a81f17d9fa";
    private static final String URL_IMGSTATUS = "https://openweathermap.org/img/wn/";
    private static final String PICTURE_FORMAT ="@2x.png";
    private static final String PICTURE_FS_FORMAT ="@1x.png";

    public static final String CELSIUS = "\u2103";
    public static final String FAHRENHEIT = "\u2109";

    public static String convertKtoC(float kelvin){
        return Math.round(kelvin -272.15) + CELSIUS;
    }
    public static String getImgStatus(String imgId) {
        return URL_IMGSTATUS+imgId+PICTURE_FORMAT;
    }
    public static String getImgForecast(String imgId) {
        return URL_IMGSTATUS+imgId+PICTURE_FS_FORMAT;
    }
}
