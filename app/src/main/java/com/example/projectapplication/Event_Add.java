package com.example.projectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Event_Add extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button uploadBTN;
    private ImageView eventImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

        uploadBTN = findViewById(R.id.uploadBTN);
        eventImage = findViewById(R.id.eventImage);

        uploadBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openFileChooser();
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
            eventImage.setImageURI(imageUri);
        }
    }
}