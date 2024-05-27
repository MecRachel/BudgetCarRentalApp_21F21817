package com.example.budgetapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotRachelScreen extends AppCompatActivity {

    private EditText R21F21817ForgotPasswordEmail;
    private FirebaseAuth RachiemAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_rachel_screen);

        RachiemAuth = FirebaseAuth.getInstance();

        R21F21817ForgotPasswordEmail = findViewById(R.id.etForgotPasswordEmail);
        Button R21F21817btnResetPassword = findViewById(R.id.btnResetPassword);

        R21F21817btnResetPassword.setOnClickListener(view -> {
            String email = R21F21817ForgotPasswordEmail.getText().toString().trim();
            if (email.isEmpty()) {
                Toast.makeText(ForgotRachelScreen.this, "Please enter your valid email", Toast.LENGTH_SHORT).show();
            } else {
                resetPassword(email);
            }
        });
    }

    private void resetPassword(String email) {
        RachiemAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotRachelScreen.this, "Reset link has been sent to your email", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(ForgotRachelScreen.this, "Failed to send the reset email", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
