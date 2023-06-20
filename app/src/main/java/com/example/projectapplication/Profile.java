package com.example.projectapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu, profileImg;
    LinearLayout home, profile, settings, about, logout;
    Button editBtn;
    TextView profileName, profileAge, profileGender, profilePhone, profileAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        profile = findViewById(R.id.profile);
        settings = findViewById(R.id.setting);
        about = findViewById(R.id.about);
        logout = findViewById(R.id.logout);

        profileImg = findViewById(R.id.profileImage);
        profileName = findViewById(R.id.profileName);
        profileAge = findViewById(R.id.profileAge);
        profileGender = findViewById(R.id.profileGender);
        profilePhone = findViewById(R.id.profilePhone);
        profileAddress = findViewById(R.id.profileAddress);
        editBtn = findViewById(R.id.editBtn);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Profile.this, HomePage.class);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Profile.this, Setting.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Profile.this, About.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Profile.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            }
        });

//        editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Profile.this, UploadActivity.class);
//                startActivity(intent);
//            }
//        });

        MyDatabase myDatabase = new MyDatabase(this);
        Cursor cursor = myDatabase.getUser();

        if (cursor.getCount() == 0){
            Toast.makeText(this, "No Profile Details", Toast.LENGTH_SHORT).show();
            editBtn.setText("Add Profile");
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Profile.this, UploadActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            while (cursor.moveToNext()){
                profileName.setText(""+cursor.getString(1));
                profileAge.setText(""+cursor.getInt(2));
                profileGender.setText(""+cursor.getString(3));
                profilePhone.setText(""+cursor.getString(4));
                profileAddress.setText(""+cursor.getString(5));
                byte[] imageByte = cursor.getBlob(6);

                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte,0,imageByte.length);
                profileImg.setImageBitmap(bitmap);
                editBtn.setText("Edit Profile");
                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Profile.this, EditActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }
}