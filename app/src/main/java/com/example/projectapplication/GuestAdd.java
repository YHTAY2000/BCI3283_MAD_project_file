package com.example.projectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GuestAdd extends AppCompatActivity {

    EditText name, age, gender, address, phone;
    Button submit_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_guest);

        name = findViewById(R.id.name_input);
        age = findViewById(R.id.age_input);
        gender = findViewById(R.id.gender_input);
        address = findViewById(R.id.address_input);
        phone = findViewById(R.id.phone_input);
        submit_button = findViewById(R.id.submit_button);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabase myDB = new MyDatabase(GuestAdd.this);
                myDB.addGuest(
                        name.getText().toString().trim(),
                        gender.getText().toString().trim(),
                        Integer.valueOf(age.getText().toString().trim()),
                        phone.getText().toString().trim(),
                        address.getText().toString().trim()
                );
            }
        });
    }
}