package com.example.projectapplication;


import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Event.db";
    private static final int DATABASE_VERSION = 1;

    //use for profile details
    private static final String TABLE_NAME = "profile_info";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "guest_name";
    private static final String COLUMN_GENDER = "guest_gender";
    private static final String COLUMN_AGE = "guest_age";
    private static final String COLUMN_ADDRESS = "guest_address";
    private static final String COLUMN_PHONE = "guest_phonenum";

    //Add Guest Table
    private static final String TABLE_NAME4 = "guest_list";
    private static final String COLUMN_ID4 = "id";
    private static final String COLUMN_NAME4 = "guest_name";
    private static final String COLUMN_GENDER4 = "guest_gender";
    private static final String COLUMN_AGE4 = "guest_age";
    private static final String COLUMN_ADDRESS4 = "guest_address";
    private static final String COLUMN_PHONE4 = "guest_phonenum";

    //Check-in Table
    private static final String TABLE_NAME2 = "check_in";
    private static final String COLUMN_ID2 = "id";
    private static final String COLUMN_NAME2 = "guest_name";

    //Event details Table
    private static final String TABLE_NAME3 = "event";
    private static final String COLUMN_ID3 = "event_id";
    private static final String COLUMN_EVENT = "event_name";
    private static final String COLUMN_ORGANIZER = "event_organizer";
    private static final String COLUMN_DATE = "event_date";
    private static final String COLUMN_TIME = "event_time";
    private static final String COLUMN_IMAGE = "event_image";

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

        String insertEvent = "CREATE TABLE " + TABLE_NAME3 +
                "( " + COLUMN_ID3 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EVENT + " TEXT, " +
                COLUMN_ORGANIZER + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT," +
                COLUMN_IMAGE + " BLOB );";

        String query3 = "CREATE TABLE " + TABLE_NAME4 +
                " ( " + COLUMN_ID4 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME4 + " TEXT, " +
                COLUMN_GENDER4 + " TEXT, " +
                COLUMN_AGE4 + " INTEGER, " +
                COLUMN_ADDRESS4 + " TEXT, " +
                COLUMN_PHONE4 + " TEXT );";


        db.execSQL(query);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL(insertEvent);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
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

    //add guest - TAY
    public boolean addGuestforGuestMgm(String name, String gender, int age, String address, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_GENDER, gender);
        cv.put(COLUMN_AGE, age);
        cv.put(COLUMN_ADDRESS, address);
        cv.put(COLUMN_PHONE, phone);

        long result = db.insert(TABLE_NAME4, null, cv);
        if(result == -1){
            return  false;
        }else{
            return true;
        }
    }

    public boolean addGuestCheckIn(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME2, name);
        
        long result = db.insert(TABLE_NAME2, null, cv);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getGuestList(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME4, null);
        return  data;
    }

    public Cursor getID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME4 + " WHERE " + COLUMN_NAME + " = ?", new String[]{name});
        return data;
    }


    public Cursor getCheckInList(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME2, null);
        return  data;
    }

    public void edit(String name, String type, String id){
        SQLiteDatabase db = this.getWritableDatabase();

        if (type.equals("name")){
            db.execSQL("UPDATE " + TABLE_NAME4 + " SET " + COLUMN_NAME4 + " = ? WHERE " + COLUMN_ID4 + " = " + id , new String[]{name});

        }else if (type.equals("age")){
            db.execSQL("UPDATE " + TABLE_NAME4 + " SET " + COLUMN_AGE4 + " = ? WHERE " + COLUMN_ID4 + " = " + id , new String[]{name});

        }else if (type.equals("gender")) {
            db.execSQL("UPDATE " + TABLE_NAME4 + " SET " + COLUMN_GENDER4 + " = ? WHERE " + COLUMN_ID4 + " = " + id , new String[]{name});

        }else if (type.equals("address")) {
            db.execSQL("UPDATE " + TABLE_NAME4 + " SET " + COLUMN_ADDRESS4 + " = ? WHERE " + COLUMN_ID4 + " = " + id , new String[]{name});

        }else if (type.equals("number")) {
            db.execSQL("UPDATE " + TABLE_NAME4 + " SET " + COLUMN_PHONE4 + " = ? WHERE " + COLUMN_ID4 + " = " + id , new String[]{name});
        }
    }

    public boolean delete(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String data = "DELETE FROM " + TABLE_NAME4 + " WHERE " + COLUMN_ID4 + " = " + id;

        Cursor cursor = db.rawQuery(data, null);

        if (cursor.moveToNext()){
            return false;
        }else{
            return  true;
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

    //insert event
    public long addEvent(String event, String organizer, String date, String time, byte[] image){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT, event);
        values.put(COLUMN_ORGANIZER, organizer);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_IMAGE, image);
        long eventId = db.insert("event", null, values);
        db.close();

        return eventId;
    }

    //get event and display it in list
    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> eventList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                COLUMN_ID3,
                COLUMN_EVENT,
                COLUMN_ORGANIZER,
                COLUMN_DATE,
                COLUMN_TIME,
                COLUMN_IMAGE
        };

        Cursor cursor = db.query(TABLE_NAME3, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int eventId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID3));
            String eventName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT));
            String eventOrganizer = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORGANIZER));
            String eventDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
            String eventTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME));
            byte[] eventImageBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));

            Event event = new Event(eventId, eventName, eventOrganizer, eventDate, eventTime, eventImageBytes);
            eventList.add(event);
        }

        // Close cursor and database connection
        cursor.close();
        db.close();

        // Return the list of events
        return eventList;
    }

    public Cursor searchGuestInfo(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = ?", new String[]{name});
        return  data;
    }
}
