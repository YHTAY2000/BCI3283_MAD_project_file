package com.example.projectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Guest_Details extends AppCompatActivity {

    TextView name, gender, age, address, phoneNum;
    Button back, edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_details);

        String outputid = getIntent().getStringExtra("id");
        String outputname = getIntent().getStringExtra("name");
        String outputgender = getIntent().getStringExtra("gender");
        String outputage = getIntent().getStringExtra("age");
        String outputaddress = getIntent().getStringExtra("address");
        String outputphoneNum = getIntent().getStringExtra("phoNum");

        name = (TextView) findViewById(R.id.inputName);
        gender = (TextView) findViewById(R.id.inputGender);
        address = (TextView) findViewById(R.id.address);
        phoneNum = (TextView) findViewById(R.id.phoneNum);
        age = (TextView) findViewById(R.id.inputAge);
        back = (Button) findViewById(R.id.backBtn);
        edit = (Button) findViewById(R.id.editBtn);

        name.setText(outputname);
        gender.setText(outputgender);
        address.setText(outputaddress);
        phoneNum.setText(outputphoneNum);
        age.setText(outputage);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Guest_Details.this, Guest_List.class);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guest_Details.this, Edit_Details.class);
                intent.putExtra("id", outputid);
                intent.putExtra("name", outputname);
                intent.putExtra("gender", outputgender);
                intent.putExtra("age",outputage);
                intent.putExtra("address",outputaddress);
                intent.putExtra("phoNum", outputphoneNum);
                startActivity(intent);
            }
        });

        phoneNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + outputphoneNum));
                startActivity(intent);
            }
        });

    }
}