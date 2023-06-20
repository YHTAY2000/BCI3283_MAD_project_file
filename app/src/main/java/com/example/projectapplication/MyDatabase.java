package com.example.projectapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

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
    private static final String COLUMN_EVENT_NAME4 = "event_name";
    private static final String COLUMN_NAME4 = "guest_name";
    private static final String COLUMN_GENDER4 = "guest_gender";
    private static final String COLUMN_AGE4 = "guest_age";
    private static final String COLUMN_ADDRESS4 = "guest_address";
    private static final String COLUMN_PHONE4 = "guest_phonenum";

    //Check-in Table
    private static final String TABLE_NAME2 = "check_in";
    private static final String COLUMN_ID2 = "id";
    private static final String COLUMN_NAME2 = "guest_name";

    private static final String event_name2 = "event_name";

    //Event Table
    private static final String TABLE_NAME3 = "event_list";
    private static final String COLUMN_ID3 = "event_id";
    private static final String COLUMN_EVENT = "event_name";
    private static final String COLUMN_ORGANIZER = "event_organizer";
    private static final String COLUMN_DATE = "event_date";
    private static final String COLUMN_TIME = "event_time";
    private static final String COLUMN_IMAGE = "event_image";
    private static final String COLUMN_LOCATION = "event_location";
    private static final String COLUMN_ACTIVITY = "event_activity";

    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] byteImage;

    private static String createTableQuery = "Create table ProfileUser(id INTEGER PRIMARY KEY AUTOINCREMENT" +
            ",name TEXT" +
            ",age INTEGER" +
            ",gender TEXT" +
            ",phone TEXT" +
            ",address TEXT" +
            ",image BLOB)";

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
                " ( " + COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + event_name2 + " TEXT, " +
                COLUMN_NAME2 + " TEXT ); ";

        String CREATE_EVENT_TABLE = "CREATE TABLE " + TABLE_NAME3 + "("
                + COLUMN_ID3 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EVENT + " TEXT,"
                + COLUMN_ORGANIZER + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_TIME + " TEXT,"
                + COLUMN_IMAGE + " BLOB,"
                + COLUMN_LOCATION + "TEXT,"
                + COLUMN_ACTIVITY + "TEXT );";

        String query3 = "CREATE TABLE " + TABLE_NAME4 +
                " ( " + COLUMN_ID4 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME4 + " TEXT, " +
                COLUMN_GENDER4 + " TEXT, " +
                COLUMN_EVENT_NAME4 + " TEXT, " +  // Add the missing column here
                COLUMN_AGE4 + " INTEGER, " +
                COLUMN_ADDRESS4 + " TEXT, " +
                COLUMN_PHONE4 + " TEXT );";


        db.execSQL(query);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL(CREATE_EVENT_TABLE);
        db.execSQL(createTableQuery);
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
    public boolean addGuestforGuestMgm(String name, String gender, int age, String address, String phone, String event_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_GENDER, gender);
        cv.put(COLUMN_AGE, age);
        cv.put(COLUMN_ADDRESS, address);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_EVENT_NAME4, event_name);

        long result = db.insert(TABLE_NAME4, null, cv);
        if(result == -1){
            return  false;
        }else{
            return true;
        }
    }

    public boolean addGuestCheckIn(String name, String myname){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME2, name);
        cv.put(event_name2, myname);

        long result = db.insert(TABLE_NAME2, null, cv);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getGuestList(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME4 + " WHERE " + COLUMN_EVENT_NAME4 + " = ?", new String[]{name});
        return  data;
    }

    public Cursor getID(String name, String event) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME4 + " WHERE " + COLUMN_NAME + " = ?" + " AND " + COLUMN_EVENT_NAME4 + " = ?", new String[]{name, event});
        return data;
    }


    public Cursor getCheckInList(String myname2){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE " + event_name2 + " = ?", new String[]{myname2});
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
    public long addEvent(String event, String organizer, String date, String time, byte[] image, String loaction, String activity){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT, event);
        values.put(COLUMN_ORGANIZER, organizer);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_IMAGE, image);
        values.put(COLUMN_LOCATION, loaction);
        values.put(COLUMN_ACTIVITY, activity);
        long eventId = db.insert(TABLE_NAME3, null, values);
        db.close();

        return eventId;
    }

    //get event and display it in list
    public List<Event> getAllEvents() {
        List<Event> eventList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME3;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID3));
                String eventName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT));
                String organizer = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORGANIZER));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION));
                String activity = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ACTIVITY));
                Event event = new Event(id, eventName, organizer, date, time, image, location, activity);
                eventList.add(event);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return eventList;
    }

    public ArrayList<getEventNameOnly> getAllEvents2() {
        ArrayList<getEventNameOnly> eventList = new ArrayList<>();

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
            String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
            getEventNameOnly event = new getEventNameOnly(eventId, eventName, time, date, image);
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

    public int updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT, event.getEventName());
        values.put(COLUMN_ORGANIZER, event.getEventOrganizer());
        values.put(COLUMN_DATE, event.getEventDate());
        values.put(COLUMN_TIME, event.getEventTime());
        values.put(COLUMN_IMAGE, event.getEventImage());
        values.put(COLUMN_LOCATION, event.getEventLocation());
        values.put(COLUMN_ACTIVITY, event.getEventActivity());
        return db.update(TABLE_NAME3, values, COLUMN_ID3 + " = ?",
                new String[]{String.valueOf(event.getId())});
    }

    public int deleteEvent(int eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME3, COLUMN_ID3 + " = ?",
                new String[]{String.valueOf(eventId)});
    }

    public void storeData(ModelClass modelClass){
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bitmapImage = modelClass.getImage();

        byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStream);
        byteImage = byteArrayOutputStream.toByteArray();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", modelClass.getName());
        contentValues.put("age", modelClass.getAge());
        contentValues.put("gender", modelClass.getGender());
        contentValues.put("phone", modelClass.getPhone());
        contentValues.put("address", modelClass.getAddress());
        contentValues.put("image", byteImage);

        long checkQuery = db.insert("ProfileUser", null, contentValues);
        if (checkQuery != -1){
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
            db.close();
        } else {
            Toast.makeText(context, "Something went Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from ProfileUser", null);
        return cursor;
    }

    public void updateUser(ModelClass modelClass){
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put("name", modelClass.getName());
        values.put("age", modelClass.getAge());
        values.put("gender", modelClass.getGender());
        values.put("phone", modelClass.getPhone());
        values.put("address", modelClass.getAddress());
        values.put("image", byteImage);

        db.update("ProfileUSer", values, "id" + " = " + 1, null);

    }

}
