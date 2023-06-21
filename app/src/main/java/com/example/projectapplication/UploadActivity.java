package com.example.projectapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UploadActivity extends AppCompatActivity {

    EditText uploadName, uploadAge, uploadGender, uploadPhone, uploadAddress;
    ImageView uploadImage;
    Button saveButton;
    private Uri uri;
    private Bitmap bitmapImage;
    MyDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        uploadImage = findViewById(R.id.uploadProfileImage);
        uploadName = findViewById(R.id.uploadProfileName);
        uploadAge = findViewById(R.id.uploadProfileAge);
        uploadGender = findViewById(R.id.uploadProfileGender);
        uploadPhone = findViewById(R.id.uploadProfilePhone);
        uploadAddress = findViewById(R.id.uploadProfileAddress);
        saveButton = findViewById(R.id.saveProfileButton);
        myDatabase = new MyDatabase(this);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult
                (new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            assert data != null;
                            uri = data.getData();
                            try{
                                bitmapImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            } catch (Exception e){
                                Toast.makeText(UploadActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            uploadImage.setImageBitmap(bitmapImage);
                        } else {
                            Toast.makeText(UploadActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    activityResultLauncher.launch(intent);
                } catch (Exception e){
                    Toast.makeText(UploadActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeImage();
            }
        });
    }

    private void storeImage() {
        if (!uploadName.getText().toString().isEmpty() &&
                !uploadAge.getText().toString().isEmpty() &&
                !uploadGender.getText().toString().isEmpty() &&
                !uploadPhone.getText().toString().isEmpty() &&
                !uploadAddress.getText().toString().isEmpty() &&
                    uploadImage.getDrawable() != null && bitmapImage != null){
            myDatabase.storeData(new ModelClass(uploadName.getText().toString(),
                uploadGender.getText().toString(),
                uploadAddress.getText().toString(),
                uploadPhone.getText().toString(),
                    Integer.valueOf(uploadAge.getText().toString()), bitmapImage));
            Intent intent = new Intent(UploadActivity.this, Profile.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Fields are Mandatory", Toast.LENGTH_SHORT).show();
        }
    }
}