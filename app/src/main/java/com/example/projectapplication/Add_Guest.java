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
    Button add, submit, back;
    MyDatabase db;
    boolean isTrue;

    public String event_name;
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
        submit = (Button) findViewById(R.id.submit_button);
        back = (Button) findViewById(R.id.returnbackButton);

        event_name = getIntent().getStringExtra("event_name");
        isTrue = true;

        db = new MyDatabase(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("") || age.getText().toString().equals("") || gender.getText().toString().equals("") || address.getText().toString().equals("")  || phoneNum.getText().toString().equals("")  ) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Add_Guest.this);
                    builder.setTitle("Warning");
                    builder.setMessage("Please insert the guest information");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                    isTrue = false;
                }else{
                    isTrue = true;
                }

                if (isTrue) {
                    Intent intent = new Intent(Add_Guest.this, ConfirmPage.class);
                    intent.putExtra("name", name.getText().toString());
                    intent.putExtra("gender", gender.getText().toString());
                    intent.putExtra("age", age.getText().toString());
                    intent.putExtra("address", address.getText().toString());
                    intent.putExtra("phoNum", phoneNum.getText().toString());
                    intent.putExtra("event_name", event_name);

                    startActivity(intent);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Add_Guest.this);
                builder.setTitle("Status");
                builder.setMessage("Are you sure you want to return back");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Add_Guest.this, Guest_List.class);
                        intent.putExtra("event_name", event_name);
                        startActivity(intent);

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
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
        builder.setTitle("User Info");

        int nameIndex = data.getColumnIndex("name");
        int addressIndex = data.getColumnIndex("address");

        if (data.getCount() == 0){
            builder.setMessage("No Data Found");

        }else {
            while (data.moveToNext()){

                builder.setMessage("Name\n" + data.getString(1) + "\n\n GENDER \n" + data.getString(2) + "\n\n AGE\n" + data.getString(3) + "\n\n ADDRESS \n" + data.getString(4) + "\n\nPHONE NUMBER \n" + data.getString(5) + "\n\n");
            }
        }

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (data.getCount() == 0){
                    Intent intent = new Intent(Add_Guest.this, Guest_List.class);
                    intent.putExtra("event_name", event_name);
                    startActivity(intent);

                }else {
                    Intent intent = new Intent(Add_Guest.this, ConfirmPage.class);

                    if (data.moveToFirst()) {

                        intent.putExtra("name", data.getString(1));
                        intent.putExtra("gender", data.getString(2));
                        intent.putExtra("age", data.getString(3));
                        intent.putExtra("address", data.getString(4));
                        intent.putExtra("phoNum", data.getString(5));
                        intent.putExtra("event_name", event_name);
                    }
                    startActivity(intent);
                }
            }
        }).show();
    });
}