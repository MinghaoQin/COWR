package com.minghaoqin.q.cowr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ImageHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="abhi.db";
    private static final int SCHEMA_VERSION=1;

    public ImageHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE Image(_id INTEGER PRIMARY KEY AUTOINCREMENT,imageblob BLOB, type TEXT);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public Cursor getAll() {
        return(getReadableDatabase().rawQuery("SELECT imageblob,type FROM Image",null));
    }
    public void insert(byte[] bytes, String type)
    {
        ContentValues cv=new ContentValues();

        cv.put("imageblob",bytes);
        cv.put("type",type);
        Log.e("inserted", "inserted");
        getWritableDatabase().insert("Image",null,cv);

    }
    public byte[] getImage(Cursor c)
    {
        return(c.getBlob(1));
    }
}