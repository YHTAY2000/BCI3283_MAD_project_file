package com.example.projectapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class Drawer extends AppCompatActivity {

    LinearLayout linearLayout;
    ImageView navImage;
    TextView navName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer);

        linearLayout = findViewById(R.id.nav_view);
        navImage = findViewById(R.id.navImage);
        navName = findViewById(R.id.navName);

        MyDatabase myDatabase = new MyDatabase(this);
        Cursor cursor = myDatabase.getUser();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Profile Details", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                navName.setText("" + cursor.getString(1));
                byte[] imageByte = cursor.getBlob(6);

                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                navImage.setImageBitmap(bitmap);
            }
        }
    }
}
