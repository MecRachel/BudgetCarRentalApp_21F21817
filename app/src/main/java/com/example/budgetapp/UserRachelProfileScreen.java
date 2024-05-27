package com.example.budgetapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class UserRachelProfileScreen extends AppCompatActivity {

    private EditText edttNam, edttEma, edttBirt, edttConNumber;
    private Button butsav;
    private DatabaseReference databaseRachelReference2024;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_rachel_profile_screen);

        Toolbar tool2024bar = findViewById(R.id.toolbr22);
        setSupportActionBar(tool2024bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbackbutton);


        mAuth = FirebaseAuth.getInstance();
        databaseRachelReference2024 = FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());

        edttNam = findViewById(R.id.PP20Name);
        edttEma = findViewById(R.id.PP20Email);
        edttBirt = findViewById(R.id.PP20Birthd);
        edttConNumber = findViewById(R.id.PP20ContactNum);
        butsav = findViewById(R.id.PP20butSave);

        butsav.setOnClickListener(v -> saveUserProfile());
        loadUserProfile();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, UserRachelDashScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void saveUserProfile() {
        String name = edttNam.getText().toString().trim();
        String email = edttEma.getText().toString().trim();
        String birthdate = edttBirt.getText().toString().trim();
        String contactNumber = edttConNumber.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || birthdate.isEmpty() || contactNumber.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        RachelJava user = new RachelJava(name, email, birthdate, contactNumber);
        databaseRachelReference2024.setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(UserRachelProfileScreen.this, "Your Profile has been Updated Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserRachelProfileScreen.this, "Failed to Update your Profile: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void loadUserProfile() {
        databaseRachelReference2024.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RachelJava user = dataSnapshot.getValue(RachelJava.class);
                if (user != null) {
                    edttNam.setText(user.getName());
                    edttEma.setText(user.getEmail());
                    edttBirt.setText(user.getBirthdate());
                    edttConNumber.setText(user.getContactNumber());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserRachelProfileScreen.this, "The user data could not load: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

