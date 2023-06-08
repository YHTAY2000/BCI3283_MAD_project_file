package com.example.projectapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class Add_Guest extends AppCompatActivity {

    EditText name, gender, age, address, phoneNum;
    Button add, submit;
    MyDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest);

        name = (EditText) findViewById(R.id.name_input);
        gender = (EditText) findViewById(R.id.gender_input);
        age = (EditText) findViewById(R.id.age_input);
        address = (EditText) findViewById(R.id.address_input);
        phoneNum = (EditText) findViewById(R.id.phone_input);
        add = (Button) findViewById(R.id.scanBtn);
        submit = (Button) findViewById(R.id.submit_button2);
        db = new MyDatabase(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_Guest.this, ConfirmPage.class);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("gender", gender.getText().toString());
                intent.putExtra("age", age.getText().toString());
                intent.putExtra("address", address.getText().toString());
                intent.putExtra("phoNum", phoneNum.getText().toString());
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();

            }
        });


    }
    private void scanCode() {
        ScanOptions option = new ScanOptions();
        option.setPrompt("Volume up to flash on");
        option.setBeepEnabled(true);
        option.setOrientationLocked(true);
        option.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(option);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        Cursor data =  db.searchGuestInfo(result.getContents());
        AlertDialog.Builder builder = new AlertDialog.Builder(Add_Guest.this);
        builder.setTitle("Status");

        int idIndex = data.getColumnIndex("id");
        int nameIndex = data.getColumnIndex("name");
        int addressIndex = data.getColumnIndex("address");

        if (data.moveToFirst()) {
            do {
                if (idIndex != -1) {
                    int id = data.getInt(idIndex);
                }

                if (nameIndex != -1) {
                    String name = data.getString(nameIndex);

                }

                if (addressIndex != -1) {
                    String address = data.getString(addressIndex);

                }

            }while (data.moveToNext());

        } else {
            builder.setMessage("Something Went Wrong!");
        }

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    });
}