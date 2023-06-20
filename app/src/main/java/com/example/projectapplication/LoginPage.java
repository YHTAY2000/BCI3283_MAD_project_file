package com.example.projectapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    EditText loginEmail, loginPassword;

    Button buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), HomePage.class);
            startActivity(intent);
            finish();
        }
    }

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();
        loginEmail = findViewById(R.id.register_Email);
        loginPassword = findViewById(R.id.register_Password);
        buttonLogin = findViewById(R.id.user_Button_Login);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.user_Login);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent);
                finish();
            }
        });

            buttonLogin.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressBar.setVisibility(View.VISIBLE);
                    String Email , Password;
                    Email = String.valueOf(loginEmail.getText());
                    Password = String.valueOf(loginPassword.getText());

                    if (TextUtils.isEmpty(Email)) {
                        Toast.makeText(LoginPage.this,"Enter email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(Password)) {
                        Toast.makeText(LoginPage.this,"Enter password", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mAuth.createUserWithEmailAndPassword(Email ,Password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task){
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {

                                        Toast.makeText(getApplicationContext(), "Login Successful",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginPage.this, "Authentication Failed",
                                                Toast.LENGTH_SHORT).show();

                                    }

                }
            });

    }
});
    }
}
