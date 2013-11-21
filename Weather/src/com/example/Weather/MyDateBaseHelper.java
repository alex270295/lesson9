package com.example.Weather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDateBaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dbweather";
    public static final String ID = "_id";
    public static final String CITY = "city";
    public static final String MIN_TEMPERATURE = "minTemp";
    public static final String MAX_TEMPERATURE = "maxTemp";
    public static final String WIND_DIRECTION = "windDirect";
    public static final String WIND_SPEED = "windSpeed";
    public static final String DATE = "date";

    public MyDateBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String temp = "CREATE TABLE " + DATABASE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CITY + " TEXT NOT NULL," + MIN_TEMPERATURE + " INTEGER NOT NULL," + MAX_TEMPERATURE + " INTEGER," + WIND_DIRECTION + " TEXT NOT NULL," + DATE + " TEXT NOT NULL," + WIND_SPEED + " INTEGER);";
        db.execSQL(temp);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            String temp = "DROP TABLE IF EXISTS " + DATABASE_NAME;
            db.execSQL(temp);
            onCreate(db);
        }
    }
}
