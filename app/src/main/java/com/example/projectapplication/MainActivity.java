package com.example.projectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText name, phonenum, txtage;
    Button save;
    Member member;

    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, "Database Execute Successful", Toast.LENGTH_LONG).show();

        name = (EditText) findViewById(R.id.name);
        phonenum = (EditText) findViewById(R.id.phonenum);
        txtage = (EditText) findViewById(R.id.age);
        save = (Button) findViewById(R.id.save);
        member = new Member();
        ref = FirebaseDatabase.getInstance().getReference().child("Member");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int age = Integer.parseInt(txtage.getText().toString().trim());
                Long pnum = Long.parseLong(phonenum.getText().toString().trim());

                member.setName(name.getText().toString());
                member.setAge(age);
                member.setPhonenum(pnum);

                ref.push().setValue(member);
                Toast.makeText(MainActivity.this, "Data inserted successfully", Toast.LENGTH_LONG).show();
            }
        });


    }
}