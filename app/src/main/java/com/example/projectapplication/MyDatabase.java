package com.example.projectapplication;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                       " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                       COLUMN_NAME + " TEXT, " +
                       COLUMN_GENDER + "TEXT, " +
                       COLUMN_AGE + "INTEGER, " +
                       COLUMN_ADDRESS + "TEXT, " +
                       COLUMN_PHONE + "TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
