package com.example.projectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Edit_Details extends AppCompatActivity {

    EditText name, gender, age, address, phoneNum;
    Button back, edit;
    MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        String outputid = getIntent().getStringExtra("id");
        String outputname = getIntent().getStringExtra("name");
        String outputgender = getIntent().getStringExtra("gender");
        String outputage = getIntent().getStringExtra("age");
        String outputaddress = getIntent().getStringExtra("address");
        String outputphoneNum = getIntent().getStringExtra("phoNum");

        db = new MyDatabase(this);
        Log.d("demo", "---------" + outputid);
        name = (EditText) findViewById(R.id.name);
        gender = (EditText) findViewById(R.id.gender_input);
        address = (EditText) findViewById(R.id.address_input);
        phoneNum = (EditText) findViewById(R.id.phone_input);
        age = (EditText) findViewById(R.id.age_input);
        back = (Button) findViewById(R.id.returnBackBtn);
        edit = (Button) findViewById(R.id.updateBtn);

        name.setHint(outputname);
        gender.setHint(outputgender);
        address.setHint(outputaddress);
        phoneNum.setHint(outputphoneNum);
        age.setHint(outputage);
        SessionHandler sessionManager = new SessionHandler(getApplicationContext());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("")){

                    db.edit(outputname, "name", outputid);

                }else{

                    db.edit(name.getText().toString(), "name", outputid);
                }

                if (address.getText().toString().equals("")){

                    db.edit(outputaddress, "address" ,outputid);

                }else{

                    db.edit(address.getText().toString(), "address" ,outputid);
                }

                if (age.getText().toString().equals("")){

                    db.edit(outputage, "age" ,outputid);

                }else{

                    db.edit(age.getText().toString(), "age", outputid);
                }

                if (phoneNum.getText().toString().equals("")){

                    db.edit(outputphoneNum, "number" ,outputid);

                }else{

                    db.edit(phoneNum.getText().toString(), "number", outputid);
                }

                if (gender.getText().toString().equals("")){

                    db.edit(outputgender, "gender" ,outputid);

                }else{
                    db.edit(gender.getText().toString(), "gender", outputid);
                }

                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(Edit_Details.this);
                builder.setTitle("Status");
                builder.setMessage("CHANGES UPDATED");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Edit_Details.this, Guest_List.class);
                        String userId = sessionManager.getEventName();
                        intent.putExtra("event_name", userId);
                        startActivity(intent);
                    }
                }).show();
            }
        });




    }
}