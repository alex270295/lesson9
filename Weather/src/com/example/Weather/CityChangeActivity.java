package com.example.Weather;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CityChangeActivity extends Activity {
    Button btnDelete;
    Button btnSave;
    EditText editText;
    boolean isNew;
    int id;
    SQLiteDatabase sqLiteDatabase;
    MyDateBaseHelper myDateBaseHelper;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_change_activity);
        Bundle bundle = getIntent().getExtras();
        String oldName = bundle.getString(MyActivity.OLD_NAME);
        isNew = bundle.getBoolean(MyActivity.IS_NEW);
        id = bundle.getInt(MyActivity.ID) + 1;
        editText = (EditText) findViewById(R.id.editText);
        editText.setText(oldName);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDateBaseHelper = new MyDateBaseHelper(getApplicationContext());
                sqLiteDatabase = myDateBaseHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MyDateBaseHelper.CITY, editText.getText().toString());
                contentValues.put(MyDateBaseHelper.ID, id);
                contentValues.put(MyDateBaseHelper.MAX_TEMPERATURE, 0);
                contentValues.put(MyDateBaseHelper.MIN_TEMPERATURE, 0);
                contentValues.put(MyDateBaseHelper.WIND_DIRECTION, "");
                contentValues.put(MyDateBaseHelper.WIND_SPEED, 0);
                if ("".equals(editText.getText().toString())) {

                    if (isNew) {
                        sqLiteDatabase.insert(MyDateBaseHelper.DATABASE_NAME, null, contentValues);
                    } else {
                        sqLiteDatabase.update(MyDateBaseHelper.DATABASE_NAME, contentValues, MyDateBaseHelper.ID + "=" + id, null);
                    }
                    myDateBaseHelper.close();
                    sqLiteDatabase.close();
                    finish();
                }
            }
        }

        );

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDateBaseHelper = new MyDateBaseHelper(getApplicationContext());
                sqLiteDatabase = myDateBaseHelper.getWritableDatabase();
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MyDateBaseHelper.DATABASE_NAME + id);
                sqLiteDatabase.delete(MyDateBaseHelper.DATABASE_NAME, MyDateBaseHelper.ID + "=" + id, null);
                myDateBaseHelper.close();
                sqLiteDatabase.close();
                finish();
            }
        }

        );

        if (isNew)
        {
            btnDelete.setVisibility(View.GONE);
        } else

        {
            btnDelete.setVisibility(View.VISIBLE);
        }

    }
}