package com.example.projectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Event_Edit extends AppCompatActivity {

    private ImageView editEventImage;
    private Button uploadBTN, backButton, saveButton;
    private EditText editEventName, editOrganizerName, editdate, edittime;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Calendar calendar;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        // Initialize views
        editEventName = findViewById(R.id.editEventName);
        editOrganizerName = findViewById(R.id.editOrganizerName);
        editdate = findViewById(R.id.editDateText);
        edittime = findViewById(R.id.editTimeText);
        editEventImage = findViewById(R.id.editEventImage);
        backButton = findViewById(R.id.backButton);
        calendar = Calendar.getInstance();
        uploadBTN = findViewById(R.id.uploadBTN);
        saveButton = findViewById(R.id.saveEventButton);


        Intent intent = getIntent();
        if (intent != null) {
            Event event = (Event) intent.getSerializableExtra("event");
            if (event != null) {
                // Populate event details in UI components
                editEventImage.setImageBitmap(BitmapUtils.getBitmapFromByteArray(event.getEventImage()));
                editEventName.setText(event.getEventName());
                editOrganizerName.setText(event.getEventOrganizer());
                editdate.setText(event.getEventDate());
                edittime.setText(event.getEventTime());
                this.event = event;
            }
        }

        uploadBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        editdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        edittime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Event_Edit.this, Event_Home.class);
                startActivity(intent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                saveEvent();
            }
        });

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            editEventImage.setImageURI(imageUri);
        }
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
                        editdate.setText(selectedDate);
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
                        edittime.setText(selectedTime);
                    }
                }, hour, minute, false); // Set the last parameter to false to display in 12-hour format
        timePickerDialog.show();
    }

    private void saveEvent() {
        // Retrieve the updated event details from the UI components
        String eventName = editEventName.getText().toString().trim();
        String organizer = editOrganizerName.getText().toString().trim();
        String date = editdate.getText().toString().trim();
        String time = edittime.getText().toString().trim();
        Bitmap eventImage = ((BitmapDrawable) editEventImage.getDrawable()).getBitmap();

        // Create an Event object with the updated details
        Event updatedEvent = new Event(event.getId(), eventName, organizer, date, time, BitmapUtils.getByteArrayFromBitmap(eventImage));

        // Update the event in the database
        MyDatabase db = new MyDatabase(this);
        int rowsAffected = db.updateEvent(updatedEvent);
        if (rowsAffected > 0) {
            // Event updated successfully
            Toast.makeText(this, "Event updated", Toast.LENGTH_SHORT).show();
            // Go back to Event_Home
            Intent intent = new Intent(Event_Edit.this, Event_Home.class);
            startActivity(intent);
        } else {
            // Failed to update event
            Toast.makeText(this, "Failed to update event", Toast.LENGTH_SHORT).show();
        }
    }
}