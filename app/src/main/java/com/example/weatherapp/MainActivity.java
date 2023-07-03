package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText txtCity, txtCountry;
    private TextView tvDisplay;
    private final String URL = "https://api.openweathermap.org/data/2.5/weather";
    private final String apiKey = "Enter your API key here";

    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCity = (EditText) findViewById(R.id.city);
        txtCountry = (EditText) findViewById(R.id.country);
        tvDisplay = (TextView) findViewById(R.id.txtDisplay);

    }

    public void getWeatherDetails(View view)
    {
        String tempurl = "";
        String GetCity = txtCity.getText().toString().trim();
        String GetCountry = txtCountry.getText().toString().trim();

        if(GetCity.equals(""))
        {
            tvDisplay.setText("City cannot be empty");
            Log.i("Response: ", tempurl );
        }
        else
        {
            if(!GetCountry.equals(""))
            {
                tempurl = URL + "?q="+ GetCity + "," + GetCountry + "&appid="+ apiKey;
            }
            else
            {
                tempurl = URL + "?q="+ GetCity + "&appid="+ apiKey;
            }
            StringRequest request = new StringRequest(Request.Method.POST, tempurl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response)
                {
                    String output = "";

                    try
                    {
                        JSONObject jsonResponse = new JSONObject(response);

                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");

                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");

                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        double feelsLike = jsonObjectMain.getInt("feels_like") - 273.15;
                        float pressure = jsonObjectMain.getInt("pressure");
                        int humidity = jsonObjectMain.getInt("humidity");

                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                        String wind = jsonObjectWind.getString("speed");

                        JSONObject jsonObjectCloud = jsonResponse.getJSONObject("clouds");
                        String clouds = jsonObjectCloud.getString("all");

                        JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                        String Country = jsonObjectSys.getString("country");
                        String City = jsonResponse.getString("name");

                        output = "Weather in " + City + ", "+Country+"\n\n"
                                +"Temperature: " + df.format(temp) + "°C\n"
                                +"Feels Like: " + df.format(feelsLike) + "°C\n"
                                +"Humidity: " + humidity + " %\n"
                                +"Description: " + description +"\n"
                                +"Wind Speed: " + wind + "m/s \n"
                                +"Cloudiness: " + clouds + "%\n"
                                +"Pressure: " + pressure + "hpa";

                        tvDisplay.setTextColor(Color.rgb(68, 134, 199));
                        tvDisplay.setText(output);
                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener()
        {
            public void onErrorResponse(VolleyError error){
                Toast.makeText(view.getContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
            RequestQueue queue = Volley.newRequestQueue(view.getContext());
            queue.add(request);
        }
    }


    //this was copied above to the on response bit
    public void onResponse(String response)
    {
        String output = "";

        try
        {
            JSONObject jsonresponse = new JSONObject(response);

            JSONArray jsonArray = jsonresponse.getJSONArray("weather");

            JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
            String description = jsonObjectWeather.getString("description");

            JSONObject jsonObjectMain = jsonresponse.getJSONObject("main");
            double temp = jsonObjectMain.getDouble("temp") - 273.15;
            double feelsLike = jsonObjectMain.getInt("feels_like") - 273.15;
            float pressure = jsonObjectMain.getInt("pressure");
            int humidity = jsonObjectMain.getInt("humidity");

            JSONObject jsonObjectWind = jsonresponse.getJSONObject("wind");
            String wind = jsonObjectWind.getString("speed");

            JSONObject jsonObjectCloud = jsonresponse.getJSONObject("clouds");
            String clouds = jsonObjectCloud.getString("all");

            JSONObject jsonObjectSys = jsonresponse.getJSONObject("sys");
            String Country = jsonObjectSys.getString("country");
            String City = jsonresponse.getString("name");

            output = "Weather in " + City + ", "+Country+"\n\n"
                    +"Temperature: " + df.format(temp) + "°C\n"
                    +"Feels Like: " + df.format(feelsLike) + "°C\n"
                    +"Humidity: " + humidity + " %\n"
                    +"Description: " + description +"\n"
                    +"Wind Speed: " + wind + "m/s \n"
                    +"Cloudiness: " + clouds + "%\n"
                    +"Pressure: " + pressure + "hpa";

            tvDisplay.setTextColor(Color.rgb(68, 134, 199));
            tvDisplay.setText(output);
        }catch (JSONException e)
        {
           e.printStackTrace();
        }

    }
}