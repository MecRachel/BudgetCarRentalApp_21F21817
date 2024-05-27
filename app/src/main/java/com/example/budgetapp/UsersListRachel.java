package com.example.budgetapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersListRachel extends AppCompatActivity {

    private static final String TAG = "UsersListRachel";

    private RecyclerView recycler21F21817ViewUsers;
    private RachelUsersAdap users21F21817Adapter;
    private List<RachelJava> user21F21817List;
    private DatabaseReference users21F21817Database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_users_list_rachel);

        recycler21F21817ViewUsers = findViewById(R.id.recyclerViewUsers);
        recycler21F21817ViewUsers.setLayoutManager(new LinearLayoutManager(this));

        user21F21817List = new ArrayList<>();
        users21F21817Adapter = new RachelUsersAdap(this, user21F21817List);
        recycler21F21817ViewUsers.setAdapter(users21F21817Adapter);

        users21F21817Database = FirebaseDatabase.getInstance().getReference("Users");

        loadUsers();
    }

    private void loadUsers() {
        users21F21817Database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user21F21817List.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RachelJava user = snapshot.getValue(RachelJava.class);
                    if (user != null) {
                        user21F21817List.add(user);
                    }
                }
                users21F21817Adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UsersListRachel.this, "Failed to load the users: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });
    }
}