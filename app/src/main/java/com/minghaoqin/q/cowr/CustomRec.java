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
        ImageView mimageView2 = (ImageView) CustomRec.this.findViewById(R.id.image2);

        int lowest = Preference.getInstance().getPreferenceInt("Cold");
        int middle = Preference.getInstance().getPreferenceInt("Warm");
        int highest = Preference.getInstance().getPreferenceInt("Hot");
        int temp = 60;
        if (temp < lowest) {
            weather = "freezing";
        } else if (temp >= lowest & temp < middle) {
            weather = "cold";
        } else if (temp >= middle & temp < highest) {
            weather = "warm";
        } else if (highest >= 75) {
            weather = "hot";
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
            mimageView.setImageBitmap(bitmap.get(ll));



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
            mimageView2.setImageBitmap(bitmap2.get(ll));



        }


    }


    public void startRec(View v){
        Intent intent2= new Intent(CustomRec.this,CustomActivity.class);
        startActivity(intent2);
    }

}


