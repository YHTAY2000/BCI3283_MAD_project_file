package com.example.projectapplication;

// Tan Shao Kang

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class GuestList extends AppCompatActivity {

    RecyclerView recyclerView;
    Button addbutton;

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
    }

    private void AddGuest() {
        Intent intent = new Intent(GuestList.this, GuestAdd.class);
        startActivity(intent);
    }
}
