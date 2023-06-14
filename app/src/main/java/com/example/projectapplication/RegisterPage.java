package com.example.projectapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NonNls;

public class RegisterPage extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword,editTextConfirmPassword;
            Button register;
            FirebaseAuth mAuth;
            ProgressBar progressBar;
            TextView textView;

            public void onStart() {
                super.onStart();
                // Check if user is signed in (non-null) and update UI accordingly.
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser != null){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            @SuppressLint("WrongViewCast")

            protected void onCreate(Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                setContentView(R.layout.register);
                mAuth = FirebaseAuth.getInstance();
                        editTextEmail = findViewById(R.id.editTextEmail);
                        editTextPassword = findViewById(R.id.editTextPassword);
                        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
                        register = findViewById(R.id.register);
                        progressBar = findViewById(R.id.progreeBar);
                        textView = findViewById(R.id.loginNow);
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        register.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                progressBar.setVisibility(View.VISIBLE);
                                String Email , Password, ConfirmPassword;
                                Email = String.valueOf(editTextEmail.getText());
                                Password = String.valueOf(editTextPassword.getText());
                                ConfirmPassword = String.valueOf(editTextConfirmPassword.getText());

                                if (TextUtils.isEmpty(Email)) {
                                    Toast.makeText(RegisterPage.this,"Enter email", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (TextUtils.isEmpty(Password)) {
                                    Toast.makeText(RegisterPage.this,"Enter password", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (TextUtils.isEmpty(ConfirmPassword)) {
                                    Toast.makeText(RegisterPage.this,"Confirm Password", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                String email;
                                String password;
                                mAuth.createUserWithEmailAndPassword(Email ,Password)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task){
                                            progressBar.setVisibility(View.GONE);
                                            if (task.isSuccessful()) {

                                                Toast.makeText(RegisterPage.this, "Registration Successfull",
                                                        Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    startActivity(intent);
                                                    finish();

                                            } else {
                                                Toast.makeText(RegisterPage.this, "Authentication Failed",
                                                        Toast.LENGTH_SHORT).show();

                                            }
                                    }
                                });

                            }
                });

            }

}
