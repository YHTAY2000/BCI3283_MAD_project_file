package com.example.projectapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Event.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "guest";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "guest_name";
    private static final String COLUMN_GENDER = "guest_gender";
    private static final String COLUMN_AGE = "guest_age";
    private static final String COLUMN_ADDRESS = "guest_address";
    private static final String COLUMN_PHONE = "guest_phonenum";

    public MyDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                       " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                       COLUMN_NAME + " TEXT, " +
                       COLUMN_GENDER + " TEXT, " +
                       COLUMN_AGE + " INTEGER, " +
                       COLUMN_ADDRESS + " TEXT, " +
                       COLUMN_PHONE + " TEXT );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addGuest(String name, String gender, int age, String address, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_GENDER, gender);
        cv.put(COLUMN_AGE, age);
        cv.put(COLUMN_ADDRESS, address);
        cv.put(COLUMN_PHONE, phone);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Fail To Insert", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Insert Successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
