package com.minghaoqin.q.cowr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DefaultActivity extends AppCompatActivity {

    RelativeLayout mRelativeLayout;
    TextView mintempTxt,maxtempTxt,condtionsTxt;
    ImageView wear;
    RequestQueue queue;
    Double temp_min,temp_max;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        mintempTxt=findViewById(R.id.mintempTxt);
        maxtempTxt=findViewById(R.id.maxtempTxt);
        condtionsTxt=findViewById(R.id.conditionsTxt);
        mRelativeLayout=findViewById(R.id.relativelayout);
        wear= findViewById(R.id.weardefault);
        queue = Volley.newRequestQueue(this);
        getWeather();

    }
    public void getWeather()    //Json parsing of data from api
    {
        String url= "http://api.openweathermap.org/data/2.5/weather?q=Piscataway&APPID=5a4bdedb13354853072b4ab26254c444"; //api request
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    JSONObject main_object=response.getJSONObject("main");
                    JSONArray weather=response.getJSONArray("weather");
                    JSONObject weather_main= weather.getJSONObject(0);
                    String temp_min_string,temp_max_string,weather_condition;
                     temp_min=(Double.parseDouble(main_object.getString("temp_min"))-273.15)*9/5+32;
                     temp_max=(Double.parseDouble(main_object.getString("temp_max"))-273.15)*9/5+32;

                    temp_min_string=String.format("%.2f",(temp_min));
                    temp_max_string=String.format("%.2f",(temp_max));
                    weather_condition=weather_main.getString("main");
                    mintempTxt.setText("Low Temprature:"+temp_min_string);
                    maxtempTxt.setText("High Temprature:"+temp_max_string);
                    condtionsTxt.setText(weather_condition);

                   //basic testing of reccomendations
                    if (temp_min>20)
                    {
                        wear.setImageResource(R.drawable.winterjacket);
                    }
                    else
                    {
                        wear.setImageResource(R.drawable.hoodie);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                mintempTxt.setText("Josn problems");

            }
        }
        );
        queue.add(request);
    }
}
