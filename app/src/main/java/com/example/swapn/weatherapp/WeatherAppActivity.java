package com.example.swapn.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

    JSONObject currentWeather;
    JSONObject futureWeather;

    EditText editText;
    Button button;
    TextView maintextView;
    ImageView mainImage;

    String zipcode;

    TextView text1T;
    TextView text2T;
    TextView text3T;
    TextView text4T;
    TextView text5T;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_app);

        editText = (EditText) findViewById(R.id.id_editText);
        button = (Button) findViewById(R.id.id_button);
        maintextView = (TextView) findViewById(R.id.id_textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            zipcode = editText.getText().toString();
            AsyncThread weatherThread = new AsyncThread();
            weatherThread.execute();
            }
        });

        text1T = (TextView)findViewById(R.id.textView1T);
        text2T = (TextView)findViewById(R.id.textView2T);
        text3T = (TextView)findViewById(R.id.textView3T);
        text4T = (TextView)findViewById(R.id.textView4T);
        text5T = (TextView)findViewById(R.id.textView5T);


    }

    public static double converter(double num){
        return Math.round((num*(9.0/5.0) - 459.67)*100.0)/100.0 ;
    }

    public class AsyncThread extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url1 = new URL("http://api.openweathermap.org/data/2.5/weather?zip="+zipcode+"&APPID=5244998c89fd54420cc25028d86b55e8");
                URLConnection urlConnection1 = url1.openConnection();
                InputStream inputStream1 = urlConnection1.getInputStream();
                BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1));
                currentWeather = new JSONObject(bufferedReader1.readLine());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                URL url2 = new URL("http://api.openweathermap.org/data/2.5/forecast?zip="+zipcode+"&APPID=5244998c89fd54420cc25028d86b55e8");
                URLConnection urlConnection2 = url2.openConnection();
                InputStream inputStream2 = urlConnection2.getInputStream();
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream2));
                futureWeather = new JSONObject(bufferedReader2.readLine());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                JSONObject Otemp = new JSONObject(currentWeather.getString("main"));
                double temp = converter(Otemp.getDouble("temp"));
                JSONObject Cdescription = new JSONObject(String.valueOf(currentWeather.getJSONArray("weather").get(0)));
                maintextView.setText(temp +" F and "+ Cdescription.getString("description"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONObject rootObj1 = futureWeather.getJSONArray("list").getJSONObject(0);
                double max1 = converter(rootObj1.getJSONObject("main").getDouble("temp_max"));
                double min1 = converter(rootObj1.getJSONObject("main").getDouble("temp_min"));
                text1T.setText("Max: "+ max1 + " F\nMin: " + min1+" F\n description:\n"+ rootObj1.getJSONArray("weather").getJSONObject(0).getString("description"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONObject rootObj2 = futureWeather.getJSONArray("list").getJSONObject(1);
                double max2 = converter(rootObj2.getJSONObject("main").getDouble("temp_max"));
                double min2 = converter(rootObj2.getJSONObject("main").getDouble("temp_min"));
                text2T.setText("Max: "+ max2 + "\nMin: " + min2+"\n description:\n"+ rootObj2.getJSONArray("weather").getJSONObject(0).getString("description"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONObject rootObj3 = futureWeather.getJSONArray("list").getJSONObject(2);
                double max3 = converter(rootObj3.getJSONObject("main").getDouble("temp_max"));
                double min3 = converter(rootObj3.getJSONObject("main").getDouble("temp_min"));
                text3T.setText("Max: "+ max3 + "\nMin: " + min3+"\n description:\n"+ rootObj3.getJSONArray("weather").getJSONObject(0).getString("description"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONObject rootObj4 = futureWeather.getJSONArray("list").getJSONObject(3);
                double max4 = converter(rootObj4.getJSONObject("main").getDouble("temp_max"));
                double min4 = converter(rootObj4.getJSONObject("main").getDouble("temp_min"));
                text4T.setText("Max: "+ max4 + "\nMin: " + min4+"\n description:\n"+ rootObj4.getJSONArray("weather").getJSONObject(0).getString("description"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONObject rootObj5 = futureWeather.getJSONArray("list").getJSONObject(4);
                double max5 = converter(rootObj5.getJSONObject("main").getDouble("temp_max"));
                double min5 = converter(rootObj5.getJSONObject("main").getDouble("temp_min"));
                text5T.setText("Max: "+ max5 + "\nMin: " + min5+"\n description:\n"+ rootObj5.getJSONArray("weather").getJSONObject(0).getString("description"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }



}
