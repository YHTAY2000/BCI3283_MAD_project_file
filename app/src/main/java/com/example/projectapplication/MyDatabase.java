package com.example.projectapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    //Check-in Table
    private static final String TABLE_NAME2 = "check_in";
    private static final String COLUMN_ID2 = "id";
    private static final String COLUMN_NAME2 = "guest_name";


    public MyDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context.getApplicationContext();
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

        String query2 = "CREATE TABLE " + TABLE_NAME2 +
                " ( " + COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME2 + " TEXT ); ";

        db.execSQL(query);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
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

    void addGuestCheckIn(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME2, name);
        
        long result = db.insert(TABLE_NAME2, null, cv);
        if(result == -1){
            Toast.makeText(context, "Fail To Insert", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Insert Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getGuestList(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return  data;
    }

    public Cursor getID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = ?", new String[]{name});
        return data;
    }


    public Cursor getCheckInList(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME2, null);
        return  data;
    }



    public boolean delete(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String data = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id;

        Cursor cursor = db.rawQuery(data, null);

        if (cursor.moveToNext()){
            return true;
        }else{
            return  false;
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
