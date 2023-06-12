package com.example.projectapplication;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;

public class Guest_List extends AppCompatActivity {

    MyDatabase db;
    MyAdapter mAdapter;

    MyAdapter2 mAdapter2;
    MyAdapter3 mAdapter3;
    Button checkIn, add;
    List<item> itemList1;
    List<CheckInItem> itemList2;
    List<item> itemList3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list);

        checkIn = (Button) findViewById(R.id.checkInBtn);
        add = (Button) findViewById(R.id.addBtn3);
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        RecyclerView checkInRecycleView = findViewById(R.id.checkInRecycleView);
        db = new MyDatabase(this);
        itemList1 = new ArrayList<>();
        itemList2 = new ArrayList<>();
        itemList3 = new ArrayList<>();

        loadDataFromDatabase();

        mAdapter = new MyAdapter(this, itemList1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        mAdapter2 = new MyAdapter2(this, itemList2);
        checkInRecycleView.setLayoutManager(new LinearLayoutManager(this));
        checkInRecycleView.setAdapter(mAdapter2);



        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                scanCode();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guest_List.this, Add_Guest.class);
                startActivity(intent);
            }
        });
    }

    private void loadDataFromDatabase() {
        Cursor data = db.getGuestList();
        Cursor data2 = db.getCheckInList();

        if (data.getCount() == 0) {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()) {
                String name = data.getString(1);
                itemList1.add(new item(name));
            }
        }

        if (data2.getCount() == 0) {
            itemList2.add(new CheckInItem("Not Data Found"));
            Toast.makeText(this, "No Check In Found", Toast.LENGTH_SHORT).show();
        } else {
            while (data2.moveToNext()) {
                String name = data2.getString(1);
                itemList2.add(new CheckInItem(name));
            }
        }
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
        boolean status = db.addGuestCheckIn(result.getContents());
        AlertDialog.Builder builder = new AlertDialog.Builder(Guest_List.this);
        builder.setTitle("Status");

        if (status) {
            builder.setMessage("Successful Added");
            // Refresh the check-in list
            mAdapter2.addItem(new CheckInItem(result.getContents()));
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