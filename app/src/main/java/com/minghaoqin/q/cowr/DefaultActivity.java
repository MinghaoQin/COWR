package com.minghaoqin.q.cowr;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Random;

public class DefaultActivity extends AppCompatActivity {

    RelativeLayout mRelativeLayout;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    TextView mintempTxt,maxtempTxt,condtionsTxt,locationTxt;
    String weather;
    ImageView wear,weathimg,extra,umbrella,wearbottom;
    RequestQueue queue;
    Double temp_min,temp_max;
    Switch notificationsw;
    ImageHelper myDb = new ImageHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        mintempTxt=findViewById(R.id.mintempTxt);
        maxtempTxt=findViewById(R.id.maxtempTxt);
        condtionsTxt=findViewById(R.id.conditionsTxt);
        locationTxt=findViewById(R.id.locationText);
        umbrella=findViewById(R.id.umbrellaimg);
        extra=findViewById(R.id.extrasimg);
        wearbottom=findViewById(R.id.bottomimg);



        notificationsw=findViewById(R.id.notificationswitch);
        //mRelativeLayout=findViewById(R.id.relativelayout);
        locationTxt.setText(Preference.getInstance().getPreference("Address"));

        dl = findViewById(R.id.drawerLayout);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        t.setDrawerIndicatorEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.temp_settings:
                        //Toast.makeText(DefaultActivity.this, "Settings",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), TempConfigureActivity.class);
                        startActivity(i);
                        break;
                    case R.id.loc_settings:
                        //Toast.makeText(DefaultActivity.this, "Settings",Toast.LENGTH_SHORT).show();
                        Intent i2 = new Intent(getApplicationContext(), LocationActivity.class);
                        startActivity(i2);
                        break;
                    case R.id.clothes_settings:
                        //Toast.makeText(DefaultActivity.this, "Settings",Toast.LENGTH_SHORT).show();
                        Intent i3 = new Intent(getApplicationContext(), CustomActivity.class);
                        startActivity(i3);
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

        notificationsw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    Intent serviceIntent = new Intent(getApplicationContext(),notificationService.class);
                    startService(serviceIntent);
                }
            }
        });

        weathimg = (ImageView)findViewById(R.id.weatherIcon);
        wear= findViewById(R.id.weardefault);
        queue = Volley.newRequestQueue(this);
        getWeather();

    }
    public void getWeather()    //Json parsing of data from api
    {
        float lat = Preference.getInstance().getPreferenceFloat("Latitude");
        float lon = Preference.getInstance().getPreferenceFloat("Longitude");
        String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&APPID=5a4bdedb13354853072b4ab26254c444";
        //String url= "http://api.openweathermap.org/data/2.5/weather?q=Piscataway&APPID=5a4bdedb13354853072b4ab26254c444"; //api request
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    JSONObject main_object=response.getJSONObject("main");
                    JSONArray weather=response.getJSONArray("weather");
                    JSONObject weather_main= weather.getJSONObject(0);
                    String temp_min_string,temp_max_string,weather_condition,weather_icon;
                     temp_min=(Double.parseDouble(main_object.getString("temp_min"))-273.15)*9/5+32;
                     temp_max=(Double.parseDouble(main_object.getString("temp_max"))-273.15)*9/5+32;

                    temp_min_string = String.format("%.0f",(temp_min));
                    temp_max_string = String.format("%.0f",(temp_max));
                    weather_condition=weather_main.getString("main");
                    weather_icon = weather_main.getString("icon");
                    //Toast.makeText(getBaseContext(),weather_icon,Toast.LENGTH_LONG).show();
                    Picasso.get().load("http://openweathermap.org/img/w/"+weather_icon+".png").into(weathimg);
                    mintempTxt.setText("Low Temperature: "+ temp_min_string + " °F");
                    maxtempTxt.setText("High Temperature: "+temp_max_string+ " °F");
                    condtionsTxt.setText(weather_condition);
                    //make reccomendation call here
                    makeRecommendation();
                   //basic testing of reccomendations
                    /*if (temp_min>20)
                    {
                        wear.setImageResource(R.drawable.winterjacket);
                    }
                    else
                    {
                        wear.setImageResource(R.drawable.hoodie);
                    }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                mintempTxt.setText("Please Check Internet Connection");

            }
        }
        );
        queue.add(request);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume(){
        super.onResume();
        locationTxt.setText(Preference.getInstance().getPreference("Address"));
        getWeather();
    }

    public void startRefresh(View v)
    {
        //Intent intent = getIntent();
        //finish();
        //startActivity(intent);'
        locationTxt.setText(Preference.getInstance().getPreference("Address"));
        getWeather();
        toastMsg("Weather is up to date");
    }

    public void makeRecommendation(){//called after getting weather data
        int cold = Preference.getInstance().getPreferenceInt("Cold");
        int warm = Preference.getInstance().getPreferenceInt("Warm");
        int hot = Preference.getInstance().getPreferenceInt("Hot");
        int temp = temp_min.intValue();
        if (temp < cold) {
            weather = "freezing";
            wear.setImageResource(R.drawable.winterjacket);//set default value
            wearbottom.setImageResource(R.drawable.sweatpants);
            extra.setImageResource(R.drawable.winterextras);

        } else if (temp >= cold & temp < warm) {
            weather = "cold";
            wear.setImageResource(R.drawable.hoodie);//set default value
            wearbottom.setImageResource(R.drawable.jeans);


        } else if (temp >= warm & temp < hot) {
            weather = "warm";
            wear.setImageResource(R.drawable.longsleeveshirt);//set default value
            wearbottom.setImageResource(R.drawable.jeans);

        } else if (temp >= hot) {
            weather = "hot";
            wear.setImageResource(R.drawable.tshirt);//set default value
            wearbottom.setImageResource(R.drawable.shorts);

        }
        final ArrayList<Bitmap> bitmap = new ArrayList<Bitmap>();
        final ArrayList<Bitmap> bitmap2 = new ArrayList<Bitmap>();

        Cursor res = myDb.getRec(weather,"top");
        Cursor res2 = myDb.getRec(weather,"bottom");
        int i = 0;
        Random r = new Random();

        if (res.getCount() > 0) {

            while (res.moveToNext()) {
                byte[] data = res.getBlob(res.getColumnIndex("imageblob"));
                ByteArrayInputStream imageStream = new ByteArrayInputStream(data);
                Bitmap image = BitmapFactory.decodeStream(imageStream);
                bitmap.add(image);
                i++;

            }
            final int ll = r.nextInt(i );
            wear.setImageBitmap(bitmap.get(ll));
        }

        int j = 0;
        if (res2.getCount() > 0) {

            while (res2.moveToNext()) {
                byte[] data2 = res2.getBlob(res2.getColumnIndex("imageblob"));
                ByteArrayInputStream imageStream2 = new ByteArrayInputStream(data2);
                Bitmap image2 = BitmapFactory.decodeStream(imageStream2);
                bitmap2.add(image2);
                j++;

            }
            final int ll = r.nextInt(j );
            wearbottom.setImageBitmap(bitmap2.get(ll));



        }
    }
    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();

    }
}
