package com.example.budgetapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;;
import java.util.ArrayList;

import java.util.List;

public class ManageRachelBookingScreen extends AppCompatActivity {

    private EditText e21F21817CarModel, e21F21817StartDate, e21F21817EndDate, e21F21817PricePerDay;
    private Button e21F21817btnAddBooking, e21F21817btnReset;
    private RecyclerView recycViewBook21F21817;
    private BookingAdapter bookingAdapter21F21817;
    private List<RachelBookingClass> bookingListRACHEL;
    private DatabaseReference databaseRef21F21817;
    private FirebaseAuth RachelAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_rachel_booking_screen);

        Toolbar toolbar = findViewById(R.id.toolbr22);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbackbutton);


        RachelAuth = FirebaseAuth.getInstance();
        databaseRef21F21817 = FirebaseDatabase.getInstance().getReference("Bookings").child(RachelAuth.getCurrentUser().getUid());

        e21F21817CarModel = findViewById(R.id.RachieCarModel);
        e21F21817StartDate = findViewById(R.id.RachieStartDate);
        e21F21817EndDate = findViewById(R.id.RachieEndDate);
        e21F21817PricePerDay = findViewById(R.id.RachiePricePerDay);
        e21F21817btnAddBooking = findViewById(R.id.RachiebtnAddBooking);
        e21F21817btnReset = findViewById(R.id.RachiebtnRes);
        recycViewBook21F21817 = findViewById(R.id.recyclerViewBookings);

        recycViewBook21F21817.setLayoutManager(new LinearLayoutManager(this));
        bookingListRACHEL = new ArrayList<>();
        bookingAdapter21F21817 = new BookingAdapter(this, bookingListRACHEL, databaseRef21F21817);
        recycViewBook21F21817.setAdapter(bookingAdapter21F21817);

        e21F21817btnAddBooking.setOnClickListener(v -> addBooking());
        e21F21817btnReset.setOnClickListener(v -> resetForm());


        Intent intent = getIntent();
        if (intent != null) {
            String carModel = intent.getStringExtra("carModel");
            double pricePerDay = intent.getDoubleExtra("pricePerDay", 0);
            if (carModel != null && pricePerDay != 0) {
                e21F21817CarModel.setText(carModel);
                e21F21817PricePerDay.setText(String.valueOf(pricePerDay));
            }
        }

        loadBookings();
    }

    private void addBooking() {
        String carModel =   e21F21817CarModel.getText().toString().trim();
        String startDate =   e21F21817StartDate.getText().toString().trim();
        String endDate =   e21F21817EndDate.getText().toString().trim();
        String pricePerDayStr =   e21F21817PricePerDay.getText().toString().trim();

        if (TextUtils.isEmpty(carModel) || TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate) || TextUtils.isEmpty(pricePerDayStr)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double pricePerDay = Double.parseDouble(pricePerDayStr);

        String bookingId = databaseRef21F21817.push().getKey();
        RachelBookingClass booking = new RachelBookingClass(bookingId, carModel, startDate, endDate, pricePerDay);

        if (bookingId != null) {
            databaseRef21F21817.child(bookingId).setValue(booking)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ManageRachelBookingScreen.this, "Booking Added Successfully", Toast.LENGTH_SHORT).show();
                            resetForm();
                        } else {
                            Toast.makeText(ManageRachelBookingScreen.this, "Failed to Add Booking: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Error: Booking ID is null", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetForm() {
        e21F21817CarModel.setText("");
        e21F21817StartDate.setText("");
        e21F21817EndDate.setText("");
        e21F21817PricePerDay.setText("");
    }

    private void loadBookings() {
        databaseRef21F21817.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookingListRACHEL.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    RachelBookingClass booking = dataSnapshot.getValue(RachelBookingClass.class);
                    if (booking != null) {
                        bookingListRACHEL.add(booking);
                    }
                }
                bookingAdapter21F21817.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ManageRachelBookingScreen.this, "Failed to load bookings: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void editBooking(RachelBookingClass booking) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_book, null);
        dialogBuilder.setView(dialogView);

        EditText etEditCarModel = dialogView.findViewById(R.id.E21F21817EditCarModel);
        EditText etEditStartDate = dialogView.findViewById(R.id.E21F21817EditStartDate);
        EditText etEditEndDate = dialogView.findViewById(R.id.E21F21817EditEndDate);
        EditText etEditPricePerDay = dialogView.findViewById(R.id.E21F21817EditPricePerDay);
        Button btnUpdateBooking = dialogView.findViewById(R.id.E21F21817btnUpdateBooking);

        etEditCarModel.setText(booking.getCarModel());
        etEditStartDate.setText(booking.getStartDate());
        etEditEndDate.setText(booking.getEndDate());
        etEditPricePerDay.setText(String.valueOf(booking.getPricePerDay()));

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnUpdateBooking.setOnClickListener(v -> {
            String carModel = etEditCarModel.getText().toString().trim();
            String startDate = etEditStartDate.getText().toString().trim();
            String endDate = etEditEndDate.getText().toString().trim();
            String pricePerDayStr = etEditPricePerDay.getText().toString().trim();

            if (TextUtils.isEmpty(carModel) || TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate) || TextUtils.isEmpty(pricePerDayStr)) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double pricePerDay = Double.parseDouble(pricePerDayStr);

            RachelBookingClass updatedBooking = new RachelBookingClass(booking.getBookingId(), carModel, startDate, endDate, pricePerDay);
            databaseRef21F21817.child(booking.getBookingId()).setValue(updatedBooking)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ManageRachelBookingScreen.this, "Booking Updated Successfully", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(ManageRachelBookingScreen.this, "Failed to Update Booking: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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



