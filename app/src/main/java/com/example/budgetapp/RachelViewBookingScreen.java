package com.example.budgetapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RachelViewBookingScreen extends AppCompatActivity {

    private static final String TAG = "RachelViewBookingScreen";

    private RecyclerView recycViewBookings21F2;
    private BookingAdapter Rachel21bookingAdapter;
    private List<RachelBookingClass> bookingList;
    private DatabaseReference databaseRef21F21817;
    private FirebaseAuth RachelAuth;
    private boolean RachelisAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rachel_view_booking_screen);

        Toolbar tool21F21817 = findViewById(R.id.toolb65);
        setSupportActionBar(tool21F21817);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbackbutton);
        }

        RachelAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = RachelAuth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "User is not authenticated", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        RachelisAdmin = getIntent().getBooleanExtra("isAdmin", false);

        if (RachelisAdmin) {
            // Admin view: fetch all bookings
            databaseRef21F21817 = FirebaseDatabase.getInstance().getReference("Bookings");
            Log.d(TAG, "Admin view: Fetching all bookings");
        } else {
            // User view: fetch bookings for the current user
            databaseRef21F21817 = FirebaseDatabase.getInstance().getReference("Bookings").child(currentUser.getUid());
            Log.d(TAG, "User view: Fetching bookings for user ID: " + currentUser.getUid());
        }

        recycViewBookings21F2 = findViewById(R.id.recyclerViewBookings);
        recycViewBookings21F2.setLayoutManager(new LinearLayoutManager(this));
        bookingList = new ArrayList<>();
        Rachel21bookingAdapter = new BookingAdapter(this, bookingList, databaseRef21F21817);
        recycViewBookings21F2.setAdapter(Rachel21bookingAdapter);

        loadBookings();

        Button btnCloseApp = findViewById(R.id.btnCloseApp);
        btnCloseApp.setOnClickListener(v -> {
            finishAffinity();
            Toast.makeText(RachelViewBookingScreen.this, "App closed", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadBookings() {
        Log.d(TAG, "loadBookings: Started");
        databaseRef21F21817.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookingList.clear();
                Log.d(TAG, "onDataChange: Data snapshot received with children count: " + snapshot.getChildrenCount());
                if (RachelisAdmin) {
                    // Admin: iterate over all users' bookings
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        Log.d(TAG, "Admin view: Processing user: " + userSnapshot.getKey());
                        for (DataSnapshot bookingSnapshot : userSnapshot.getChildren()) {
                            if (bookingSnapshot.getValue() instanceof Map) {
                                RachelBookingClass booking = bookingSnapshot.getValue(RachelBookingClass.class);
                                if (booking != null) {
                                    bookingList.add(booking);
                                    Log.d(TAG, "Booking added: " + booking.toString());
                                } else {
                                    Log.e(TAG, "Booking is null for some reason");
                                }
                            } else {
                                Log.e(TAG, "Skipping non-map value: " + bookingSnapshot.getValue());
                            }
                        }
                    }
                } else {
                    // User: iterate over the current user's bookings
                    for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                        RachelBookingClass booking = bookingSnapshot.getValue(RachelBookingClass.class);
                        if (booking != null) {
                            bookingList.add(booking);
                            Log.d(TAG, "Booking added: " + booking.toString());
                        } else {
                            Log.e(TAG, "Booking is null for some reason");
                        }
                    }
                }
                if (bookingList.isEmpty()) {
                    Toast.makeText(RachelViewBookingScreen.this, "No car bookings are found", Toast.LENGTH_SHORT).show();
                }
                Rachel21bookingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RachelViewBookingScreen.this, "Failed to load car bookings", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Database error: " + error.getMessage());
            }
        });
    }

    public void editBooking(RachelBookingClass booking) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_book, null);
        dialogBuilder.setView(dialogView);

        EditText C2024EditCarModel = dialogView.findViewById(R.id.E21F21817EditCarModel);
        EditText C2024EditStartDate = dialogView.findViewById(R.id.E21F21817EditStartDate);
        EditText C2024EditEndDate = dialogView.findViewById(R.id.E21F21817EditEndDate);
        EditText C2024EditPricePerDay = dialogView.findViewById(R.id.E21F21817EditPricePerDay);
        Button C2024btnUpdateBooking = dialogView.findViewById(R.id.E21F21817btnUpdateBooking);

        C2024EditCarModel.setText(booking.getCarModel());
        C2024EditStartDate.setText(booking.getStartDate());
        C2024EditEndDate.setText(booking.getEndDate());
        C2024EditPricePerDay.setText(String.valueOf(booking.getPricePerDay()));

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        C2024btnUpdateBooking.setOnClickListener(v -> {
            String carModel = C2024EditCarModel.getText().toString().trim();
            String startDate = C2024EditStartDate.getText().toString().trim();
            String endDate = C2024EditEndDate.getText().toString().trim();
            String pricePerDayStr = C2024EditPricePerDay.getText().toString().trim();

            if (carModel.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || pricePerDayStr.isEmpty()) {
                Toast.makeText(this, "Please fill in all the required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double pricePerDay = Double.parseDouble(pricePerDayStr);

            RachelBookingClass updatedBooking = new RachelBookingClass(booking.getBookingId(), carModel, startDate, endDate, pricePerDay);
            databaseRef21F21817.child(booking.getBookingId()).setValue(updatedBooking)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(RachelViewBookingScreen.this, "Booking of the car Updated Successfully", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(RachelViewBookingScreen.this, "Failed to Update the car Booking Details", Toast.LENGTH_SHORT).show();
                        }
                    });
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
}
