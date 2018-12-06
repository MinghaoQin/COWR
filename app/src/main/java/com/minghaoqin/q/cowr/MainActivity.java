package com.minghaoqin.q.cowr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
   public void startDefault(View v)
    {
        Intent intent= new Intent(this,DefaultActivity.class);
        startActivity(intent);
    }
    public void startCustom(View v){
        Intent intent2= new Intent(MainActivity.this,CustomActivity.class);
        startActivity(intent2);
    }
}
