package com.example.projectapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class MainPage extends AppCompatActivity implements View.OnClickListener {

    ConstraintLayout mMainPage;
    Button getstarted;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        mMainPage=findViewById(R.id.main_page);
        getstarted=findViewById(R.id.getstarted);

    }
}










