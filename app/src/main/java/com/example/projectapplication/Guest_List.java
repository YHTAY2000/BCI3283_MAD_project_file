package com.example.projectapplication;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;

public class Guest_List extends AppCompatActivity {

    MyDatabase db;
    MyAdapter mA;
    Button checkIn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list);

        checkIn = (Button) findViewById(R.id.checkInBtn);
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        db = new MyDatabase(this);
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = db.getGuestList();
        if (data.getCount() == 0){
            Toast.makeText(this, "Not Data Found", Toast.LENGTH_SHORT).show();
        }else {
            while (data.moveToNext()) {

                theList.add(data.getString(1));
                List<item> items = new ArrayList<>();

                for (String str : theList) {
                    items.add(new item(str));
                }

                showDetails(recyclerView, items);


                Toast.makeText(this, "Data Found", Toast.LENGTH_SHORT).show();


            }
        }

        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        barLaucher.launch(option);
    }

    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(),result -> {
        db.addGuestCheckIn(result.getContents());
    });

    public void showDetails(RecyclerView recyclerView, List<item> items){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), items));

    }
}