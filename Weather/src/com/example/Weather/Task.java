package com.example.Weather;

import android.content.ContentValues;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task extends AsyncTask<Weather, Void, Void> {

    Weather result;

    String download(Weather weather) throws IOException {
        String now = (new SimpleDateFormat("yyyy-mm-dd")).format(new Date());
        String requestUrl = MyActivity.API_URL + "?key=" + MyActivity.API_KEY + "&q=" + weather.city + "&cc=no" + "&date=" + now + "&format=json";
        URL url = new URL(requestUrl);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.connect();
        String line = null;
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
        StringBuilder strBuilder = new StringBuilder();
        while ((line = buffReader.readLine()) != null) {
            strBuilder.append(line + '\n');
        }
        if (strBuilder != null) {
            return strBuilder.toString();
        } else return null;
    }

    void update() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDateBaseHelper.CITY, result.city);
        contentValues.put(MyDateBaseHelper.ID, result.id);
        contentValues.put(MyDateBaseHelper.MAX_TEMPERATURE, result.tempMin);
        contentValues.put(MyDateBaseHelper.MIN_TEMPERATURE, result.tempMax);
        contentValues.put(MyDateBaseHelper.WIND_DIRECTION, result.windDirect);
        contentValues.put(MyDateBaseHelper.WIND_SPEED, result.windSpeed);
        MyActivity.sqLiteDatabase.update(MyDateBaseHelper.DATABASE_NAME, contentValues, MyDateBaseHelper.ID + "=" + result.id, null);
    }

    @Override
    protected Void doInBackground(Weather... params) {
        String temp = null;
        result = params[0];
        try {
            temp = download(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (temp != null) {
            JSONObject object = null;
            try {
                object = (JSONObject) new JSONTokener(temp).nextValue();
                JSONArray jsonArray = object.getJSONObject("data").getJSONArray("weather");
                object = jsonArray.getJSONObject(0);
                result.tempMin = object.getInt("tempMinC");
                result.date = object.getString("date");
                result.tempMax = object.getInt("tempMaxC");
                result.windSpeed = object.getInt("windspeedKmph");
                result.windDirect = object.getString("winddir16Point");
                update();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }
}
