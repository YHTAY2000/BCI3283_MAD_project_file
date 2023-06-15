package com.example.projectapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainPage extends AppCompatActivity {
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    Button getStarted,adminLogin;

    LinearLayout mMainPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        mMainPage=findViewById(R.id.main_page);
        getStarted=findViewById(R.id.getStarted);
        adminLogin=findViewById(R.id.adminLogin);

        getStarted.setOnClickListener(this);
        adminLogin.setOnClickListener(this);


        BiometricManager biometricManager=BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(getApplicationContext(), "Device Doesn't have fingerprint", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(getApplicationContext(), "Not Working", Toast.LENGTH_SHORT).show();

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(getApplicationContext(), "No fingerprint Assigned", Toast.LENGTH_SHORT).show();
            case BiometricManager.BIOMETRIC_SUCCESS:
                break;
        }
        Executor executor= ContextCompat.getMainExecutor(this);

        biometricPrompt=new BiometricPrompt(MainPage.this,executor,new BiometricPrompt.AuthenticationCallback(){
            public void onAuthenticationError(int errorcode, @NonNull CharSequence errString){
                super.onAuthenticationError(errorcode,errString);
            }
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                mMainPage.setVisibility(View.VISIBLE);
            }
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo=new BiometricPrompt.PromptInfo.Builder().setTitle("MAD1C")
                .setDescription("Use Fingerprint to login").setDeviceCredentialAllowed(true).build();

        biometricPrompt.authenticate(promptInfo);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getStarted:
                Toast.makeText(this, "login", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainPage.this, LoginPage.class));
                break;
            case R.id.adminLogin:
                Toast.makeText(this, "login", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainPage.this, AdminPage.class));
                break;

        }
    }
}





