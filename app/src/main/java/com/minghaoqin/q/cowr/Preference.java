package com.minghaoqin.q.cowr;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preference {
    private static Preference mInstance;
    private Context mContext;
    //
    private SharedPreferences mMyPreferences;

    private Preference(){ }

    public static Preference getInstance(){
        if (mInstance == null) mInstance = new Preference();
        return mInstance;
    }

    public void Initialize(Context ctxt){
        mContext = ctxt;
        //
        mMyPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void writePreference(String key, String value){
        SharedPreferences.Editor e = mMyPreferences.edit();
        e.putString(key, value);
        e.commit();
    }
    public void writePreferenceInt(String key, int value){
        SharedPreferences.Editor e = mMyPreferences.edit();
        e.putInt(key, value);
        e.commit();
    }
}
