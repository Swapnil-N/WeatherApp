package com.example.swapn.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class WeatherAppActivity extends AppCompatActivity {

    JSONObject CurrentWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_app);

        AsyncThread weatherThread = new AsyncThread();
        weatherThread.execute();

    }

    public class AsyncThread extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL Currenturl = new URL("http://api.openweathermap.org/data/2.5/weather?zip=08852&APPID=5244998c89fd54420cc25028d86b55e8");
                URLConnection urlConnection = Currenturl.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String Cstring = bufferedReader.readLine();
                CurrentWeather = new JSONObject(Cstring);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }



}
