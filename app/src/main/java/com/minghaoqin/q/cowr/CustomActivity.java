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
    Button homebtn;




    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom);


        homebtn=findViewById(R.id.homebtn);
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
    int hello2;
    boolean checked2;
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        checked = ((RadioButton) view).isChecked();
        hello = view.getId();
        // Check which radio button was clicked

    }
    String clothtype = "";
    public void RadioClick(View view) {
        // Is the button now checked?
        checked2 = ((RadioButton) view).isChecked();
        hello2 = view.getId();
        // Check which radio button was clicked
        if (hello2 == R.id.top){
            clothtype = "top";
            //Toast.makeText(getApplicationContext(),"Please take a picture to add it.", Toast.LENGTH_SHORT).show();
        } else if (hello2 == R.id.bottom) {
            //Toast.makeText(getApplicationContext(), "Please.", Toast.LENGTH_SHORT).show();
            clothtype = "bottom";
        }

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
                if (checked&&checked2)
                    //Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT).show();
                myDb.insert(byteArray,"warm",clothtype);
                break;
            case R.id.hot:
                if (checked&&checked2)
                    myDb.insert(byteArray,"hot",clothtype);
                break;
            case R.id.freezing:
                if (checked&&checked2)
                    myDb.insert(byteArray,"freezing",clothtype);
                break;
            case R.id.cold:
                if (checked&&checked2)
                    myDb.insert(byteArray,"cold",clothtype);
                break;
            default:
                Toast.makeText(getApplicationContext(),"Please select the temperature preference.", Toast.LENGTH_SHORT).show();
                return;


        }
        if (!checked2)
        {                Toast.makeText(getApplicationContext(),"Please select the clothing type.", Toast.LENGTH_SHORT).show();
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
    public void back(View view) {
        Intent bintent = new Intent(getApplicationContext(), DefaultActivity.class);
        startActivity(bintent);
    }
}

