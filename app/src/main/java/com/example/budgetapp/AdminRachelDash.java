package com.example.budgetapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminRachelDash extends AppCompatActivity {

    private FirebaseAuth m21F21817Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_admin_rachel_dash);

        m21F21817Auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser21F21817 = m21F21817Auth.getCurrentUser();

        boolean isAdminLoggedIn = getSharedPreferences("AdminPrefs", MODE_PRIVATE).getBoolean("isAdminLoggedIn", false);
        if (!isAdminLoggedIn && currentUser21F21817 == null) {
            Intent intent = new Intent(AdminRachelDash.this, LoginLoginScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return;
        }

        Button b21F21817tnViewBookings = findViewById(R.id.RachiebtnViewBookings);
        Button b21F21817tnViewUsers = findViewById(R.id.RachiebtnViewUsers);
        Button b21F21817tnLogout = findViewById(R.id.RachiebtnLogout);

        b21F21817tnViewBookings.setOnClickListener(v -> {
            Intent intent = new Intent(AdminRachelDash.this, RachelViewBookingScreen.class);
            intent.putExtra("isAdmin", true);
            startActivity(intent);
        });

        b21F21817tnViewUsers.setOnClickListener(v -> startActivity(new Intent(AdminRachelDash.this, UsersListRachel.class)));

        b21F21817tnLogout.setOnClickListener(v -> {
            m21F21817Auth.signOut();
            getSharedPreferences("AdminPrefs", MODE_PRIVATE).edit().putBoolean("isAdminLoggedIn", false).apply();
            Toast.makeText(AdminRachelDash.this, "Logged out from the app successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AdminRachelDash.this, LoginLoginScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
