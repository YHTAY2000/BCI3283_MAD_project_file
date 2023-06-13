package com.example.projectapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPage extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword,editTextConfirmPassword;
            Button register;
            FirebaseAuth mAuth;


            @SuppressLint("WrongViewCast")

            protected void onCreate(Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                setContentView(R.layout.register);
                mAuth = FirebaseAuth.getInstance();
                        editTextEmail = findViewById(R.id.editTextEmail);
                        editTextPassword = findViewById(R.id.editTextPassword);
                        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
                        register = findViewById(R.id.register);

                        register.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String Email , Password, ConfirmPassword;
                                Email = String.valueOf(editTextEmail.getText());
                                Password = String.valueOf(editTextPassword.getText());
                                ConfirmPassword = String.valueOf(editTextConfirmPassword.getText());

                                if (TextUtils.isEmpty(Email)) {
                                    Toast.makeText(RegisterPage.this."Enter email", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (TextUtils.isEmpty(Password)) {
                                    Toast.makeText(RegisterPage.this."Enter password", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (TextUtils.isEmpty(ConfirmPassword)) {
                                    Toast.makeText(RegisterPage.this."Confirm Password", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                mAuth.createUserWithEmailAndPassword(email>
                                });


                    }


                    }
                });



            }

}
