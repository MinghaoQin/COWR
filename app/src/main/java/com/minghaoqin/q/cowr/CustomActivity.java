package com.minghaoqin.q.cowr;


import android.app.Activity;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import android.widget.Button;

import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;


public class CustomActivity extends AppCompatActivity {
    ImageHelper myDb = new ImageHelper(this);
    private static final int CAMERA_REQUEST = 1888;

    ImageView mimageView;




    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom);


        mimageView = (ImageView) this.findViewById(R.id.image_from_camera);

        Button button = (Button) this.findViewById(R.id.take_image_from_camera);

    }



    public void takeImageFromCamera(View view) {

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }


    Bitmap mphoto;
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            mphoto = (Bitmap) data.getExtras().get("data");
           // mimageView.setRotation(90);
            mimageView.setImageBitmap(mphoto);


        }

    }
    boolean checked;
    int hello;
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        checked = ((RadioButton) view).isChecked();
        hello = view.getId();
        // Check which radio button was clicked

    }

    public void SaveClothing(View view){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (mphoto==null)
        {
            Toast.makeText(getApplicationContext(),"Please take a picture to add it.", Toast.LENGTH_SHORT).show();
            return;
        }
        mphoto.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        switch(hello) {
            case R.id.warm:
                if (checked)
                    Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT).show();
                myDb.insert(byteArray,"warm");
                break;
            case R.id.hot:
                if (checked)
                    myDb.insert(byteArray,"hot");
                break;
            case R.id.freezing:
                if (checked)
                    myDb.insert(byteArray,"freezing");
                break;
            case R.id.cold:
                if (checked)
                    myDb.insert(byteArray,"cold");
                break;
            default:
                Toast.makeText(getApplicationContext(),"Please take select the temperature preference.", Toast.LENGTH_SHORT).show();
                return;


        }
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void MyClothes(View view){
        Intent intent = new Intent (CustomActivity.this, listview.class);
        startActivity(intent);
    }
}