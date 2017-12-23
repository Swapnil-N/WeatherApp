package com.example.swapn.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
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

    EditText editText;
    Button button;
    TextView textView;

    String zipcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_app);

        editText = (EditText) findViewById(R.id.id_editText);
        button = (Button) findViewById(R.id.id_button);
        textView = (TextView) findViewById(R.id.id_textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zipcode = editText.getText().toString();
            }
        });

        AsyncThread weatherThread = new AsyncThread();
        weatherThread.execute();


    }

    public class AsyncThread extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL Currenturl = new URL("http://api.openweathermap.org/data/2.5/weather?zip=08810&APPID=5244998c89fd54420cc25028d86b55e8");
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

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //print from JSON object
            try {

                JSONObject Ctemp = new JSONObject(CurrentWeather.getString("main"));
                JSONObject thing = new JSONObject(String.valueOf(CurrentWeather.getJSONArray("weather").get(0)));
                textView.setText(Ctemp.getString("temp")+ "   "+ thing.getString("description"));
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }



}
