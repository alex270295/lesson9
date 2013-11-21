package com.example.Weather;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class WeatherActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        Bundle bundle = getIntent().getExtras();
        TextView textView = (TextView) findViewById(R.id.textView);
        String result;
    }
}