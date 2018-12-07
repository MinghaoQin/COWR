package com.minghaoqin.q.cowr;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Random;

public class CustomRec extends AppCompatActivity {
    ImageHelper myDb = new ImageHelper(this);
    String weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customrec);
        ImageView mimageView = (ImageView) CustomRec.this.findViewById(R.id.image);


        int temp = 60;
        if (temp < 25) {
            weather = "freezing";
        } else if (temp >= 25 & temp < 50) {
            weather = "cold";
        } else if (temp >= 50 & temp < 75) {
            weather = "warm";
        } else if (temp >= 75) {
            weather = "hot";
        }
        final ArrayList<Bitmap> bitmap = new ArrayList<Bitmap>();
        Cursor res = myDb.getRec(weather);
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
            mimageView.setImageBitmap(bitmap.get(ll));



        }
    }


    public void startRec(View v){
        Intent intent2= new Intent(CustomRec.this,CustomActivity.class);
        startActivity(intent2);
    }

}
