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
import android.widget.TextView;
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
    Button checkIn, add, back;
    List<item> itemList1;
    List<CheckInItem> itemList2;
    List<item> itemList3;
    String name;
    RecyclerView recyclerView;
    RecyclerView checkInRecycleView;
    TextView noCheckInTextView,noCheckInTextView2; // Placeholder view when no check-in data is found

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list);

        checkIn = findViewById(R.id.checkInBtn);
        add = findViewById(R.id.addBtn3);
        back = findViewById(R.id.returnbutton);
        recyclerView = findViewById(R.id.recycleView);
        checkInRecycleView = findViewById(R.id.checkInRecycleView);
        noCheckInTextView = findViewById(R.id.noCheckInTextView);
        noCheckInTextView2 = findViewById(R.id.noCheckInTextView2);

        db = new MyDatabase(this);
        itemList1 = new ArrayList<>();
        itemList2 = new ArrayList<>();
        itemList3 = new ArrayList<>();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guest_List.this, GuestsManagement.class);
                startActivity(intent);
            }
        });

        mAdapter = new MyAdapter(this, itemList1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        mAdapter2 = new MyAdapter2(this, itemList2);
        checkInRecycleView.setLayoutManager(new LinearLayoutManager(this));
        checkInRecycleView.setAdapter(mAdapter2);

        name = getIntent().getStringExtra("event_name");

        loadDataFromDatabase(name);

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
                intent.putExtra("event_name", name);
                startActivity(intent);
            }
        });
    }

    private void loadDataFromDatabase(String name2) {
        Cursor data = db.getGuestList(name2);
        Cursor data2 = db.getCheckInList(name2);

        if (data.getCount() == 0) {
            noCheckInTextView2.setVisibility(View.VISIBLE); // Show placeholder view
            recyclerView.setVisibility(View.GONE); // Hide check-in RecyclerView
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()) {
                String name = data.getString(1);
                itemList1.add(new item(name));
            }
            noCheckInTextView2.setVisibility(View.GONE); // Show placeholder view
            recyclerView.setVisibility(View.VISIBLE); // Hide check-in RecyclerView
        }

        if (data2.getCount() == 0) {
            noCheckInTextView.setVisibility(View.VISIBLE); // Show placeholder view
            checkInRecycleView.setVisibility(View.GONE); // Hide check-in RecyclerView
            Toast.makeText(this, "No Check In Found", Toast.LENGTH_SHORT).show();
        } else {
            while (data2.moveToNext()) {
                int columnNameIndex = data2.getColumnIndex("guest_name"); // Replace "columnName" with the actual column name
                String name = data2.getString(columnNameIndex);
                itemList2.add(new CheckInItem(name));
            }
            noCheckInTextView.setVisibility(View.GONE); // Hide placeholder view
            checkInRecycleView.setVisibility(View.VISIBLE); // Show check-in RecyclerView
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
        boolean status = db.addGuestCheckIn(result.getContents(), name);
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
