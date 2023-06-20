package com.example.projectapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {


    EditText editName, editAge, editGender, editPhone, editAddress;
    ImageView editImage;
    Button updateButton;
    private Uri uri;
    private Bitmap bitmapImage;
    MyDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editImage = findViewById(R.id.editProfileImage);
        editName = findViewById(R.id.editProfileName);
        editAge = findViewById(R.id.editProfileAge);
        editGender = findViewById(R.id.editProfileGender);
        editPhone = findViewById(R.id.editProfilePhone);
        editAddress = findViewById(R.id.editProfileAddress);
        updateButton = findViewById(R.id.updateProfileButton);
        myDatabase = new MyDatabase(this);

        MyDatabase myDatabase = new MyDatabase(this);
        Cursor cursor = myDatabase.getUser();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Profile Details", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                editName.setText("" + cursor.getString(1));
                editAge.setText("" + cursor.getInt(2));
                editGender.setText("" + cursor.getString(3));
                editPhone.setText("" + cursor.getString(4));
                editAddress.setText("" + cursor.getString(5));
                byte[] imageByte = cursor.getBlob(6);

                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                editImage.setImageBitmap(bitmap);
            }
        }
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
                                        Toast.makeText(EditActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    editImage.setImageBitmap(bitmapImage);
                                } else {
                                    Toast.makeText(EditActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    activityResultLauncher.launch(intent);
                } catch (Exception e){
                    Toast.makeText(EditActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateImage();
            }
        });
    }

    private void updateImage() {
            myDatabase.storeData(new ModelClass(editName.getText().toString(),
                    editGender.getText().toString(),
                    editAddress.getText().toString(),
                    editPhone.getText().toString(),
                    Integer.valueOf(editAge.getText().toString()), bitmapImage));
            Intent intent = new Intent(EditActivity.this, HomePage.class);
            startActivity(intent);
    }
}