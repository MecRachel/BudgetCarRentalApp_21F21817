package com.example.budgetapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterRachelScreen extends AppCompatActivity {

    private EditText RegieNamie, RegieEmail, RegiePass, RegieBirthd, RegieContNum;
    private Button RegiebtnRegister;
    private FirebaseAuth RachelAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_rachel_screen);

        RachelAuth = FirebaseAuth.getInstance();

        RegieNamie = findViewById(R.id.R21F21817RegName);
        RegieEmail = findViewById(R.id.R21F21817RegEmail);
        RegiePass = findViewById(R.id.R21F21817RegPassword);
        RegieBirthd = findViewById(R.id.R21F21817RegBirthdate);
        RegieContNum = findViewById(R.id.R21F21817RegContactNumber);
        RegiebtnRegister = findViewById(R.id.R21F21817btnRegister);

        RegiebtnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = RegieNamie.getText().toString().trim();
        String email = RegieEmail.getText().toString().trim();
        String password = RegiePass.getText().toString().trim();
        String birthdate = RegieBirthd.getText().toString().trim();
        String contactNumber = RegiebtnRegister.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || birthdate.isEmpty() || contactNumber.isEmpty()) {
            Toast.makeText(this, "All fields are required to be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            RegiePass.setError("The Min length of password should be 6");
            return;
        }

        RachelAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser currentUser = RachelAuth.getCurrentUser();
                        if (currentUser != null) {
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();
                            currentUser.updateProfile(profileUpdates);

                            RachelJava newUser = new RachelJava(name, email, birthdate, contactNumber);
                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users")
                                    .child(currentUser.getUid());
                            userRef.setValue(newUser)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Toast.makeText(RegisterRachelScreen.this, "Registration is successful", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(RegisterRachelScreen.this, UserRachelDashScreen.class));
                                            finish();
                                        } else {
                                            Toast.makeText(RegisterRachelScreen.this, "Failed to register user information", Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(RegisterRachelScreen.this, "Registration of the user failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}