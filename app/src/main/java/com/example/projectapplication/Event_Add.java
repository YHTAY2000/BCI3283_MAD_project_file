package com.example.projectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.InputStream;
import java.util.Calendar;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Event_Add extends AppCompatActivity {

    private ImageView eventImage;
    private Button uploadBTN;
    private EditText eventNameText;
    private EditText eventOrganizerText;
    private EditText date;
    private EditText time;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Calendar calendar;
    private MyDatabase db;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

        //Initialize views
        eventImage = findViewById(R.id.eventImage);
        uploadBTN = findViewById(R.id.uploadButton);
        eventNameText = findViewById(R.id.eventNameTxt);
        eventOrganizerText = findViewById(R.id.organizerNameTxt);
        date = findViewById(R.id.dateText);
        time = findViewById(R.id.timeText);
        calendar = Calendar.getInstance();

        //Initialize database helper
        db = new MyDatabase(this);

        //set click listener for upload button
        uploadBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        //show the calender picker and time picker
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        //handles the add event
        Button addButton = findViewById(R.id.addEventBTN);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addEvent();
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                eventImage.setImageBitmap(bitmap);
            } catch(IOException e){
                e.printStackTrace();
            }

        }
    }

    private void addEvent() {
        //Get entered event data
        String event_name = eventNameText.getText().toString().trim();
        String event_organizer = eventOrganizerText.getText().toString().trim();
        String event_date = date.getText().toString().trim();
        String event_time = time.getText().toString().trim();

        // Check if an image is selected
        if (selectedImageUri == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert the image to a byte array
        byte[] imageBytes = convertImageToByteArray(selectedImageUri);

        // Save the event to the database
        long eventId = db.addEvent(event_name, event_organizer, event_date, event_time, imageBytes);
        if (eventId != -1) {
            Toast.makeText(this, "Event added", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error: Failed to add event", Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] convertImageToByteArray(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void showDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Update the EditText field with the selected date
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        date.setText(selectedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    private void showTimePicker() {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Convert the selected time to 12-hour format
                        String format;
                        int hourOfDay12 = hourOfDay % 12;
                        if (hourOfDay12 == 0) {
                            hourOfDay12 = 12;
                        }
                        if (hourOfDay < 12) {
                            format = "AM";
                        } else {
                            format = "PM";
                        }

                        // Update the EditText field with the selected time
                        String selectedTime = String.format("%02d:%02d %s", hourOfDay12, minute, format);
                        time.setText(selectedTime);
                    }
                }, hour, minute, false); // Set the last parameter to false to display in 12-hour format

        timePickerDialog.show();
    }

}