package com.minghaoqin.q.cowr;


import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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
    ImageHelper db;
    ListView dataList;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_listview);

         dataList = (ListView) findViewById(R.id.list);
        db = new ImageHelper(listview.this);
        populateList();
        dataList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selected =((TextView)view.findViewById(R.id.txtid)).getText().toString();
                //Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_SHORT).show();
                Cursor c = db.getAll();
                if (c.getCount() > 0) {
                    Bitmap[] array = new Bitmap[c.getCount()];
                    byte[] bytes = new byte[c.getCount()];
                    //c.moveToFirst();
                    while (c.moveToNext()) {

                        bytes = c.getBlob(c.getColumnIndex("imageblob"));
                        String type = c.getString(c.getColumnIndex("type"));
                        int id = c.getInt(c.getColumnIndex("_id"));
                        for(int j=0;j<imageArry.size();j++)
                        {

                          int check=  imageArry.get(j).get_id();
                            Log.d("Check",selected);
                            Log.d("Check",String.valueOf(check)+"   "+String.valueOf(j));



                          if (Integer.parseInt(selected)==check)
                          {

                              Log.d("Check","Success");
                              db.delete(check);

                              Log.d("Check","deleted");

                              Intent intent = getIntent();
                              finish();
                              startActivity(intent);

                          }

                        }

                        //array[i1] = BitmapFactory.decodeByteArray(bytes, 0, 0);
                        //c.moveToNext();

                    }
                }



                //populateList();

               return true;
            }
        });

    }
    public void home(View v)
    {
        Intent intent=new Intent(getApplicationContext(),DefaultActivity.class);
        startActivity(intent);

    }
    public void populateList(){
// get image from drawable
        Cursor c = db.getAll();
        int i = 0;
        if (c.getCount() > 0) {
            Bitmap[] array = new Bitmap[c.getCount()];
            byte[] bytes = new byte[c.getCount()];
            //c.moveToFirst();
            while (c.moveToNext()) {

                bytes = c.getBlob(c.getColumnIndex("imageblob"));
                String type = c.getString(c.getColumnIndex("type"));
                int id= c.getInt(c.getColumnIndex("_id"));
                imageArry.add(new contact(id,type, bytes));

                array[i] = BitmapFactory.decodeByteArray(bytes, 0, 0);
                //c.moveToNext();
                i++;
                Log.d("Check1",String.valueOf(i));

            }
            Log.e("Bitmap length", "" + array.length);
            Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


// Reading all contacts from database




            adapter = new adapter(this, R.layout.listview_layout,
                    imageArry);
            dataList.setAdapter(adapter);


            dataList.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    return true;
                }
            });

        }
    }
}
