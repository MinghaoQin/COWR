package com.minghaoqin.q.cowr;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.apptik.widget.MultiSlider;

public class TempConfigureActivity extends AppCompatActivity {

    MultiSlider temp_slider;
    TextView tZone[] = new TextView[4];
    TextView tTemp[] = new TextView[3];
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_configure);

        temp_slider = (MultiSlider) findViewById(R.id.temp_slider);
        temp_slider.getThumb(4).setInvisibleThumb(true);
        temp_slider.getThumb(0).setInvisibleThumb(true);

        temp_slider.getThumb(1).setValue(25);
        temp_slider.getThumb(2).setValue(50);
        temp_slider.getThumb(3).setValue(74);


        temp_slider.getThumb(4).setRange(new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff6ee19, 0xffff0000}));
        temp_slider.getThumb(3).setRange(new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xFFFFCC00, 0xfff6ee19}));
        temp_slider.getThumb(2).setRange(new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xFFADD8E6, 0xFFFFCC00}));
        temp_slider.getThumb(1).setRange(new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xFF000080, 0xFFADD8E6}));
        tZone[3] = (TextView) findViewById(R.id.textHot);
        tZone[2] = (TextView) findViewById(R.id.textWarm);
        tZone[1] = (TextView) findViewById(R.id.textCold);
        tZone[0] = (TextView) findViewById(R.id.textFreezing);
        tTemp[0] = (TextView) findViewById(R.id.textTemp1);
        tTemp[1] = (TextView) findViewById(R.id.textTemp2);
        tTemp[2] = (TextView) findViewById(R.id.textTemp3);
        button = (Button) findViewById(R.id.button);
        DisplayMetrics dM = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dM);
        final int height = dM.heightPixels;
        final int width = dM.widthPixels;

        tTemp[0].setText(temp_slider.getThumb(1).getValue() + "°F");
        tTemp[1].setText(temp_slider.getThumb(2).getValue() + "°F");
        tTemp[2].setText(temp_slider.getThumb(3).getValue() + "°F");
        temp_slider.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider,
                                       MultiSlider.Thumb thumb,
                                       int thumbIndex,
                                       int value) {
                //Toast.makeText(getBaseContext(), "Index " + thumbIndex + " val: " + value, Toast.LENGTH_SHORT).show();
                int posTop = height - width * multiSlider.getThumb(thumbIndex + 1).getValue() / multiSlider.getMax() - (height - (int) (width / 0.85));
                int posBot = height - width * multiSlider.getThumb(thumbIndex - 1).getValue() / multiSlider.getMax() - (height - (int) (width / 0.85));
                int pos = height - width * value / multiSlider.getMax() - (height - (int) (width / 0.85));
                int posMidTop = (posTop + pos) / 2;
                int posMidBot = (posBot + pos) / 2;
                //Toast.makeText(getBaseContext()," val: " + pos, Toast.LENGTH_SHORT).show();
                tTemp[thumbIndex - 1].setY(pos);
                tTemp[thumbIndex - 1].setText(value + "°F");
                tZone[thumbIndex - 1].setY(posMidBot);
                tZone[thumbIndex].setY(posMidTop);

            }
        });

    }
    public void confirm(View v){
        Preference p = Preference.getInstance();
        p.writePreferenceInt("Cold", temp_slider.getThumb(1).getValue());

        p.writePreferenceInt("Warm", temp_slider.getThumb(2).getValue());

        p.writePreferenceInt("Hot", temp_slider.getThumb(3).getValue());
    }
}
