package com.example.budgetapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserRachelDashScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "UserRachelDashScreen";

    private DrawerLayout drawer21f21817;
    private FirebaseAuth mRachelAuth;
    private RecyclerView recycRachelView2024CarCateg;
    private RachelCarCategoryAdapter carCategoryAdapter;
    private List<RachelCarCategory> carCategoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started");
        setContentView(R.layout.activity_user_rachel_dash_screen);

        Toolbar tb2024 = findViewById(R.id.toolbar);
        setSupportActionBar(tb2024);

        drawer21f21817 = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer21f21817, tb2024, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer21f21817.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nav_header_username);
        TextView navEmail = headerView.findViewById(R.id.nav_header_email);

        mRachelAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mRachelAuth.getCurrentUser();
        if (currentUser != null) {
            navUsername.setText(currentUser.getDisplayName());
            navEmail.setText(currentUser.getEmail());
        } else {
            Log.d(TAG, "onCreate: currentUser is null");
        }

        recycRachelView2024CarCateg = findViewById(R.id.recycRachelView2024Car);
        recycRachelView2024CarCateg.setLayoutManager(new LinearLayoutManager(this));
        carCategoryList = new ArrayList<>();
        carCategoryAdapter = new RachelCarCategoryAdapter(this, carCategoryList);
        recycRachelView2024CarCateg.setAdapter(carCategoryAdapter);

        loadCarCategories();
        Log.d(TAG, "onCreate: completed");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.d(TAG, "onNavigationItemSelected: Item clicked ID: " + id);

        if (id == R.id.nav_user_profile) {
            startActivity(new Intent(this, UserRachelProfileScreen.class));
        } else if (id == R.id.nav_add_car) {
            startActivity(new Intent(this, ManageRachelBookingScreen.class));
        } else if (id == R.id.nav_manage_booking) {
            startActivity(new Intent(this, RachelViewBookingScreen.class));
        } else if (id == R.id.nav_transactions) {
            startActivity(new Intent(this, TransactionRachelScreen.class));
        } else if (id == R.id.nav_feedback) {
            startActivity(new Intent(this, FeedbackRachelScreen.class));
        } else if (id == R.id.nav_logout) {
            logoutUser();
        } else {
            Toast.makeText(this, "Unhandled navigation item!", Toast.LENGTH_SHORT).show();
            return false;
        }

        drawer21f21817.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(UserRachelDashScreen.this, LoginLoginScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void loadCarCategories() {
        carCategoryList.clear();
        Log.d(TAG, "loadCarCategories: started");

        // Adding sample car categories
        carCategoryList.add(new RachelCarCategory("Economy Cars", Arrays.asList(
                new RachelCar("Model: Hyundai Accent", "Registration No: 12345", "Brand: Hyundai", 20.0, R.drawable.hyundaiaccent),
                new RachelCar("Model: Toyota Yaris", "Registration No: 67890", "Brand: Toyota", 22.0, R.drawable.toyotayaris),
                new RachelCar("Model: Nissan Micra", "Registration No: 11223", "Brand: Nissan", 21.0, R.drawable.nissanmicra)
        )));

        carCategoryList.add(new RachelCarCategory("Compact Cars", Arrays.asList(
                new RachelCar("Model: Mazda 3", "Registration No: 44556", "Brand: Mazda", 25.0, R.drawable.mazda3),
                new RachelCar("Model: Hyundai Elantra", "Registration No: 77889", "Brand: Hyundai", 27.0, R.drawable.hyundaielantra),
                new RachelCar("Model: Toyota Corolla", "Registration No: 99001", "Brand: Toyota", 26.0, R.drawable.toyotacorolla)
        )));

        carCategoryList.add(new RachelCarCategory("Mid-size Sedans", Arrays.asList(
                new RachelCar("Model: Toyota Camry", "Registration No: 22334", "Brand: Toyota", 30.0, R.drawable.camry),
                new RachelCar("Model: Honda Accord", "Registration No: 55667", "Brand: Honda", 32.0, R.drawable.hondaccord),
                new RachelCar("Model: Nissan Altima", "Registration No: 88900", "Brand: Nissan", 31.0, R.drawable.nissanaltima)
        )));

        carCategoryList.add(new RachelCarCategory("SUVs", Arrays.asList(
                new RachelCar("Model: Toyota RAV4", "Registration No: 33445", "Brand: Toyota", 35.0, R.drawable.rav4),
                new RachelCar("Model: Nissan Rogue", "Registration No: 66778", "Brand: Nissan", 37.0, R.drawable.nissanrouge),
                new RachelCar("Model: Honda CR-V", "Registration No: 99012", "Brand: Honda", 36.0, R.drawable.hondacrv)
        )));

        carCategoryList.add(new RachelCarCategory("Luxury Cars", Arrays.asList(
                new RachelCar("Model: BMW 5 Series", "Registration No: 44557", "Brand: BMW", 50.0, R.drawable.bmw5series),
                new RachelCar("Model: Mercedes-Benz E-Class", "Registration No: 77880", "Brand: Mercedes-Benz", 55.0, R.drawable.mercedesbenzeclass),
                new RachelCar("Model: Audi A6", "Registration No: 99013", "Brand: Audi", 53.0, R.drawable.audia6)
        )));

        carCategoryList.add(new RachelCarCategory("Minivans", Arrays.asList(
                new RachelCar("Model: Toyota Sienna", "Registration No: 33446", "Brand: Toyota", 40.0, R.drawable.toyotasienna),
                new RachelCar("Model: Honda Odyssey", "Registration No: 66779", "Brand: Honda", 42.0, R.drawable.odessey),
                new RachelCar("Model: Chrysler Pacifica", "Registration No: 99014", "Brand: Chrysler", 41.0, R.drawable.chryslerpacifica)
        )));

        carCategoryAdapter.notifyDataSetChanged();
        Log.d(TAG, "loadCarCategories: completed");
    }
}
