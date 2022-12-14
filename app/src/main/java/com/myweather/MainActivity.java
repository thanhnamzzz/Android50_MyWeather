package com.myweather;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.myweather.hours_weather.ForecastWeather;
import com.myweather.hours_weather.HoursAdapter;
import com.myweather.hours_weather.WeatherHours;
import com.myweather.models.CurrentWeather;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";

    @BindView(R.id.tvNameTp)
    TextView tvNameTp;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvTemp)
    TextView tvTemp;
    @BindView(R.id.tvTempFelt)
    TextView tvTempFelt;
    @BindView(R.id.tvHumidity)
    TextView tvHumidity;
    @BindView(R.id.tvSeaLevel)
    TextView tvSeaLevel;
    @BindView(R.id.tvSw)
    TextView tvSw;
    @BindView(R.id.tvTempMax)
    TextView tvTempMax;
    @BindView(R.id.tvAirPressure)
    TextView tvAirPressure;
    @BindView(R.id.imgStatus)
    ImageView imgStatus;
    @BindView(R.id.imgLocation)
    ImageView imgLocation;
    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    @BindView(R.id.rvHours)
    RecyclerView rvHours;

    private WeatherServices mWeatherServices;
    private ArrayList<WeatherHours> mListWeather;
    private HoursAdapter mHoursAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inData();
        inView();
        mWeatherServices = RetrofitClient.getServices(Global.BASE_URL, WeatherServices.class);
//        mWeatherServices.getWeatherByCityName("Hà Nội", Global.API_KEY_M).enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (response.isSuccessful()) {
//                    if (response.code() == 200) {
////                        Log.d(TAG, "onResponse: " + response.body());
//                    } else {
////                        Log.d(TAG, "onResponse: " + response.code() + " | " + response.message());
//                    }
//                } else {
////                    Log.d(TAG, "onResponse: unsucessfull");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
////                Log.d(TAG, "onFailure: " +t.getMessage());
//            }
//        });
        mWeatherServices.getWeatherByCityNameModel("Hà Nội", Global.API_KEY_M).enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        CurrentWeather model = response.body();
                        bindCurrentWeather(model);
//                        Log.d(TAG, "onResponse: " + model.getName());
                    } else {
//                        Log.d(TAG, "onResponse: " + response.code() + " | " + response.message());
                    }
                } else {
//                    Log.d(TAG, "onResponse: unsucessfull");
                }
            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
            }
        });
    }

    private void inData() {
        mListWeather = new ArrayList<>();
        mWeatherServices = RetrofitClient.getServices(Global.BASE_URL, WeatherServices.class);
        mWeatherServices.getWeatherByCityNameHours("Hà Nội", Global.API_KEY_M).enqueue(new Callback<ForecastWeather>() {
            @Override
            public void onResponse(Call<ForecastWeather> call, Response<ForecastWeather> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        ForecastWeather forecastWeather = response.body();
//                        Log.d(TAG, "onResponse: "+forecastWeather.getList().get(0).getDtTxt());
                        mListWeather.clear();
//                        mListWeather.addAll(response.body().getList());
                        for (int i = 2; i < 10; i++) {
                            mListWeather.add(forecastWeather.getList().get(i));
                        }
                        mHoursAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ForecastWeather> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStart() {
        super.onStart();
        requestLocation();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Log.d(TAG, "onSuccess: " + location.getLatitude() + " | " + location.getLongitude());
                        }
                    }
                });
    }

    private void inView() {
        ButterKnife.bind(this);
        imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mHoursAdapter = new HoursAdapter(mListWeather);
        rvHours.setAdapter(mHoursAdapter);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Search_City.class);
                startActivity(intent);
            }
        });
    }

    //Cấp quyền truy cập định vị cho app
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void requestLocation() {
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                        .RequestMultiplePermissions(), result -> {
                    Boolean fineLocationGranted = result.getOrDefault(
                            Manifest.permission.ACCESS_FINE_LOCATION, false);
                    Boolean coarseLocationGranted = result.getOrDefault(
                            Manifest.permission.ACCESS_COARSE_LOCATION, false);
                    if (fineLocationGranted != null && fineLocationGranted) {
                        // Precise location access granted.
                    } else if (coarseLocationGranted != null && coarseLocationGranted) {
                        // Only approximate location access granted.
                    } else {
                        // No location access granted.
                    }
                });
        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }

    private void bindCurrentWeather(CurrentWeather model) {
        tvNameTp.setText(model.getName());
        tvDescription.setText(model.getWeather().get(0).getDescription());
        tvTemp.setText(Global.convertKtoC(model.getMain().getTemp()));
        tvTempFelt.setText(Global.convertKtoC(model.getMain().getFeelsLike()));
        tvHumidity.setText(model.getMain().getHumidity() + " %");
        tvSeaLevel.setText(model.getMain().getSeaLevel() + "");
        tvSw.setText(model.getWind().getSpeed() + " m/s");
        tvTempMax.setText(Global.convertKtoC(model.getMain().getTempMax()));
        tvAirPressure.setText(model.getMain().getPressure() + " hPa");

        Glide.with(getApplicationContext()).load(Global.getImgStatus(model.getWeather().get(0).getIcon())).into(imgStatus);
    }
}