package com.minghaoqin.q.cowr;


import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class listview extends Activity {
    ArrayList<contact> imageArry = new ArrayList<contact>();
    adapter adapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_listview);


        ImageHelper db = new ImageHelper(listview.this);
// get image from drawable
        Cursor c = db.getAll();
        int i = 0;
        if (c.getCount() > 0) {
            Bitmap[] array = new Bitmap[c.getCount()];
            byte[] bytes = new byte[c.getCount()];
            //c.moveToFirst();
            while (c.moveToNext()) {

                bytes = c.getBlob(c.getColumnIndex("imageblob"));
                String type = c.getString(1);
                imageArry.add(new contact(type, bytes));
                array[i] = BitmapFactory.decodeByteArray(bytes, 0, 0);
                //c.moveToNext();
                i++;
            }
            Log.e("Bitmap length", "" + array.length);
            Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


// Reading all contacts from database




            adapter = new adapter(this, R.layout.listview_layout,
                    imageArry);
            ListView dataList = (ListView) findViewById(R.id.list);
            dataList.setAdapter(adapter);
        }
    }
}
