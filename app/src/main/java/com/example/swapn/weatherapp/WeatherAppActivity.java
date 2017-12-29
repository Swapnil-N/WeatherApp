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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

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

    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    ImageView image5;

    TextView text1B;
    TextView text2B;
    TextView text3B;
    TextView text4B;
    TextView text5B;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_app);

        editText = (EditText) findViewById(R.id.id_editText);
        button = (Button) findViewById(R.id.id_button);
        maintextView = (TextView) findViewById(R.id.id_textView);
        mainImage = (ImageView) findViewById(R.id.CimageView);

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

        image1 = (ImageView) findViewById(R.id.imageView1);
        image2 = (ImageView) findViewById(R.id.imageView2);
        image3 = (ImageView) findViewById(R.id.imageView3);
        image4 = (ImageView) findViewById(R.id.imageView4);
        image5 = (ImageView) findViewById(R.id.imageView5);

        text1B = (TextView)findViewById(R.id.textView1B);
        text2B = (TextView)findViewById(R.id.textView2B);
        text3B = (TextView)findViewById(R.id.textView3B);
        text4B = (TextView)findViewById(R.id.textView4B);
        text5B = (TextView)findViewById(R.id.textView5B);

    }

    public static double converter(double num){
        return Math.round((num*(9.0/5.0) - 459.67)*10.0)/10.0 ;
    }

    public static String timefixer(String input){
        //0,3,6,9,12,15,18,21
        //7P, 10P, 1A, 4A, 7A, 10A, 1P, 4P
        String hour = input.substring(11,13);
        if (hour.equals("00")){
            return "7:00 P.M.";
        }
        else if (hour.equals("03")){
            return "10:00 P.M.";
        }
        else if (hour.equals("06")){
            return "1:00 A.M.";
        }
        else if (hour.equals("09")){
            return "4:00 A.M.";
        }
        else if (hour.equals("12")){
            return "7:00 A.M.";
        }
        else if (hour.equals("15")){
            return "10:00 A.M.";
        }
        else if (hour.equals("18")){
            return "1:00 P.M.";
        }
        else if (hour.equals("21")){
            return "4:00 P.M.";
        }

        return "not a valid time";
    }

    public static void setImage(ImageView tempImage, String string){
        if (string.equals("01d"))
            tempImage.setImageResource(R.drawable.clearsky01d);
        else if (string.equals("01n"))
            tempImage.setImageResource(R.drawable.clearsky01d);
        else if (string.equals("02d"))
            tempImage.setImageResource(R.drawable.fewclouds02d);
        else if (string.equals("02n"))
            tempImage.setImageResource(R.drawable.fewclouds02n);
        else if (string.equals("10d"))
            tempImage.setImageResource(R.drawable.rain10d);
        else if (string.equals("10n"))
            tempImage.setImageResource(R.drawable.rain10n);

        else if (string.substring(0,2).equals("03"))
            tempImage.setImageResource(R.drawable.cloudy03d);
        else if (string.substring(0,2).equals("04"))
            tempImage.setImageResource(R.drawable.cloud04d);
        else if (string.substring(0,2).equals("09"))
            tempImage.setImageResource(R.drawable.srain09d);
        else if (string.substring(0,2).equals("11"))
            tempImage.setImageResource(R.drawable.thunder11d);
        else if (string.substring(0,2).equals("13"))
            tempImage.setImageResource(R.drawable.snow13d);
        else if (string.substring(0,2).equals("50"))
            tempImage.setImageResource(R.drawable.mist50d);
        else
            tempImage.setImageResource(R.drawable.weathermeme);
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
                setImage(mainImage,Cdescription.getString("icon"));
                maintextView.setText(temp +" F and "+ Cdescription.getString("description"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONObject rootObj1 = futureWeather.getJSONArray("list").getJSONObject(0);
                double max1 = converter(rootObj1.getJSONObject("main").getDouble("temp_max"));
                double min1 = converter(rootObj1.getJSONObject("main").getDouble("temp_min"));
                text1T.setText(timefixer(rootObj1.getString("dt_txt"))+"\nhigh: "+ max1+" F");
                text1B.setText("Low: " + min1+" F\n description:\n"+ rootObj1.getJSONArray("weather").getJSONObject(0).getString("description"));
                setImage(image1,rootObj1.getJSONArray("weather").getJSONObject(0).getString("icon"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONObject rootObj2 = futureWeather.getJSONArray("list").getJSONObject(1);
                double max2 = converter(rootObj2.getJSONObject("main").getDouble("temp_max"));
                double min2 = converter(rootObj2.getJSONObject("main").getDouble("temp_min"));
                text2T.setText(timefixer(rootObj2.getString("dt_txt"))+"\nhigh: "+ max2+" F");
                text2B.setText("Low: " + min2+" F\n description:\n"+ rootObj2.getJSONArray("weather").getJSONObject(0).getString("description"));
                setImage(image2,rootObj2.getJSONArray("weather").getJSONObject(0).getString("icon"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONObject rootObj3 = futureWeather.getJSONArray("list").getJSONObject(2);
                double max3 = converter(rootObj3.getJSONObject("main").getDouble("temp_max"));
                double min3 = converter(rootObj3.getJSONObject("main").getDouble("temp_min"));
                text3T.setText(timefixer(rootObj3.getString("dt_txt"))+"\nhigh: "+ max3+" F");
                text3B.setText("Low: " + min3+" F\n description:\n"+ rootObj3.getJSONArray("weather").getJSONObject(0).getString("description"));
                setImage(image3,rootObj3.getJSONArray("weather").getJSONObject(0).getString("icon"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONObject rootObj4 = futureWeather.getJSONArray("list").getJSONObject(3);
                double max4 = converter(rootObj4.getJSONObject("main").getDouble("temp_max"));
                double min4 = converter(rootObj4.getJSONObject("main").getDouble("temp_min"));
                text4T.setText(timefixer(rootObj4.getString("dt_txt"))+"\nhigh: "+ max4+" F");
                text4B.setText("Low: " + min4+" F\n description:\n"+ rootObj4.getJSONArray("weather").getJSONObject(0).getString("description"));
                setImage(image4,rootObj4.getJSONArray("weather").getJSONObject(0).getString("icon"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONObject rootObj5 = futureWeather.getJSONArray("list").getJSONObject(4);
                double max5 = converter(rootObj5.getJSONObject("main").getDouble("temp_max"));
                double min5 = converter(rootObj5.getJSONObject("main").getDouble("temp_min"));
                text5T.setText(timefixer(rootObj5.getString("dt_txt"))+"\nhigh: "+ max5+" F");
                text5B.setText("Low: " + min5+" F\n description:\n"+ rootObj5.getJSONArray("weather").getJSONObject(0).getString("description"));
                setImage(image5,rootObj5.getJSONArray("weather").getJSONObject(0).getString("icon"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }



}
