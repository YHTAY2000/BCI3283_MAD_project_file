package com.example.projectapplication;

// Tan Shao Kang

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GuestList extends AppCompatActivity {

    RecyclerView recyclerView;
    Button addbutton;

    MyDatabase myDB;
    ArrayList<String> guest_id, guest_name, guest_gender, guest_age, guest_address, guest_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_list);

        recyclerView = findViewById(R.id.guestRecyclerView);
        addbutton = findViewById(R.id.Add_button);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddGuest();
            }
        });

        myDB = new MyDatabase(GuestList.this);
        guest_id = new ArrayList<>();
        guest_name = new ArrayList<>();
        guest_gender = new ArrayList<>();
        guest_age = new ArrayList<>();
        guest_address = new ArrayList<>();
        guest_phone = new ArrayList<>();

        storeGuestDataArrays();
    }

    void storeGuestDataArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                guest_id.add(cursor.getString(0));
                guest_name.add(cursor.getString(1));
                guest_gender.add(cursor.getString(2));
                guest_age.add(cursor.getString(3));
                guest_address.add(cursor.getString(4));
                guest_phone.add(cursor.getString(5));
            }
        }
    }

    private void AddGuest() {
        Intent intent = new Intent(GuestList.this, GuestAdd.class);
        startActivity(intent);
    }
}
