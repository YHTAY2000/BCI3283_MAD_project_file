package com.example.projectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmPage extends AppCompatActivity {
    TextView name, gender, age, address, phoneNum;
    Button confirmBtn;
    MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_page);

        String outputname = getIntent().getStringExtra("name");
        String outputgender = getIntent().getStringExtra("gender");
        String outputage = getIntent().getStringExtra("age");
        String outputaddress = getIntent().getStringExtra("address");
        String outputphoneNum = getIntent().getStringExtra("phoNum");
        String event_name = getIntent().getStringExtra("event_name");

        name = (TextView) findViewById(R.id.inputName);
        gender = (TextView) findViewById(R.id.inputGender);
        address = (TextView) findViewById(R.id.address);
        phoneNum = (TextView) findViewById(R.id.phoneNum);
        age = (TextView) findViewById(R.id.inputAge);
        confirmBtn = (Button) findViewById(R.id.confirmBtn);
        db = new MyDatabase(this);


        name.setText(outputname);
        gender.setText(outputgender);
        address.setText(outputaddress);
        phoneNum.setText(outputphoneNum);
        age.setText(outputage);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status = db.addGuestforGuestMgm(outputname, outputgender, Integer.parseInt(outputage), outputaddress, outputphoneNum, event_name);
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmPage.this);
                builder.setTitle("Status");

                if (status) {
                    builder.setMessage("Successful Added");
                } else {
                    builder.setMessage("Something Went Wrong!");
                }

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(ConfirmPage.this, Guest_List.class);
                        intent.putExtra("event_name", event_name);
                        startActivity(intent);
                    }
                }).show();

            }
        });

    }
}