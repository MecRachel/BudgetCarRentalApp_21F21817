package com.example.budgetapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TransactionRachelScreen extends AppCompatActivity {

    private static final String TAG = "TransactionRachelScreen";

    private TextView R2024TotalPrice;
    private Button btnConfirm;

    private FirebaseAuth mRachelAuth;
    private DatabaseReference databaseRef21F21817;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_rachel_screen);

        Log.d(TAG, "onCreate: TransactionRachelScreen started");

        mRachelAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mRachelAuth.getCurrentUser();
        boolean isAdminLoggedIn = getSharedPreferences("AdminPrefs", MODE_PRIVATE).getBoolean("isAdminLoggedIn", false);

        if (user == null && !isAdminLoggedIn) {
            Log.e(TAG, "User is not authenticated and not an admin");
            Intent intent = new Intent(TransactionRachelScreen.this, LoginLoginScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return;
        }

        Toolbar toolbar = findViewById(R.id.toolb33);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbackbutton);
        } else {
            Log.e(TAG, "getSupportActionBar() returned null");
        }

        R2024TotalPrice = findViewById(R.id.Rachel2024TotalPrice);
        btnConfirm = findViewById(R.id.Rachel2024btnConfirm);

        if (user != null) {
            databaseRef21F21817 = FirebaseDatabase.getInstance().getReference("Bookings").child(user.getUid());
            Log.d(TAG, "User view: Fetching bookings for user ID: " + user.getUid());
        } else {
            Log.e(TAG, "User is null");
            Toast.makeText(this, "User is null", Toast.LENGTH_SHORT).show();
            return;
        }

        calculateTotalPrice();

        btnConfirm.setOnClickListener(v -> {
            Intent intent = new Intent(TransactionRachelScreen.this, PaymentRachelSuccessful.class);
            startActivity(intent);
        });
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

    private void calculateTotalPrice() {
        Log.d(TAG, "calculateTotalPrice: Started");
        databaseRef21F21817.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double totalPrice = 0.0;
                Log.d(TAG, "onDataChange: Data snapshot received with children count: " + snapshot.getChildrenCount());
                try {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Log.d(TAG, "DataSnapshot key: " + dataSnapshot.getKey() + ", value: " + dataSnapshot.getValue());
                        try {
                            RachelBookingClass booking = dataSnapshot.getValue(RachelBookingClass.class);
                            if (booking != null) {
                                Log.d(TAG, "Booking fetched: " + booking);
                                long days = getDaysBetweenDates(booking.getStartDate(), booking.getEndDate());
                                Log.d(TAG, "Days between " + booking.getStartDate() + " and " + booking.getEndDate() + ": " + days);
                                totalPrice += days * booking.getPricePerDay();
                                Log.d(TAG, "Current total price: " + totalPrice);
                            } else {
                                Log.e(TAG, "Booking is null");
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Error converting to RachelBookingClass", e);
                        }
                    }
                    Log.d(TAG, "Final total price: " + totalPrice);
                    R2024TotalPrice.setText(String.format(Locale.getDefault(), "%.2f OMR", totalPrice));
                } catch (Exception e) {
                    Log.e(TAG, "Error calculating total price", e);
                    Toast.makeText(TransactionRachelScreen.this, "Error calculating total price: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Database error: " + error.getMessage());
                Toast.makeText(TransactionRachelScreen.this, "Failed to load bookings: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private long getDaysBetweenDates(String startDateStr, String endDateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date startDate = sdf.parse(startDateStr);
        Date endDate = sdf.parse(endDateStr);
        if (startDate != null && endDate != null) {
            long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
            long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            Log.d(TAG, "getDaysBetweenDates: Calculated days: " + days);
            return days;
        }
        return 0;
    }
}
