package com.example.budgetapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginLoginScreen extends AppCompatActivity {

    private EditText V21F21817Email, V21F21817Password;
    private Button V21F21817btnLogin, V21F21817btnAdminLogin, V21F21817btnGoToReg;
    private TextView V21F21817tvForgotPass;
    private FirebaseAuth RachAuth;

    private static final String ADMIN_EMAIL = "mecAdmin@gmail.com";
    private static final String ADMIN_PASSWORD = "mec1234";

    private static final String TAG = "LoginLoginScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_login_screen);

        RachAuth = FirebaseAuth.getInstance();

        V21F21817Email = findViewById(R.id.rachelEmail);
        V21F21817Password = findViewById(R.id.rachelPassword);
        V21F21817btnLogin = findViewById(R.id.rachelbtnLogin);
        V21F21817btnAdminLogin = findViewById(R.id.rachelbtnAdminLogin);
        V21F21817btnGoToReg = findViewById(R.id.rachelbtnGoToRegister);
        V21F21817tvForgotPass = findViewById(R.id.racheltvForgotPassword);

        V21F21817btnLogin.setOnClickListener(view -> loginUser());

        V21F21817btnAdminLogin.setOnClickListener(view -> adminLogin());

        V21F21817btnGoToReg.setOnClickListener(view -> startActivity(new Intent(LoginLoginScreen.this, RegisterRachelScreen.class)));

        V21F21817tvForgotPass.setOnClickListener(view -> startActivity(new Intent(LoginLoginScreen.this, ForgotRachelScreen.class)));
    }

    private void loginUser() {
        String email = V21F21817Email.getText().toString().trim();
        String password = V21F21817Password.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            V21F21817Email.setError("Email is needed to login");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            V21F21817Password.setError("Password is needed to lofin");
            return;
        }

        RachAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = RachAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "Login is successful, redirecting to UserRachelDashScreen");
                    Toast.makeText(LoginLoginScreen.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginLoginScreen.this, UserRachelDashScreen.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                Log.d(TAG, "Authentication has failed.");
                Toast.makeText(LoginLoginScreen.this, "Authentication has failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void adminLogin() {
        String email = V21F21817Email.getText().toString().trim();
        String password = V21F21817Password.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            V21F21817Email.setError("Email is needed to login");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            V21F21817Password.setError("Password is needed to login");
            return;
        }

        if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
            RachAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = RachAuth.getCurrentUser();
                    if (user != null) {
                        // Save admin login state in SharedPreferences
                        getSharedPreferences("AdminPrefs", MODE_PRIVATE).edit().putBoolean("isAdminLoggedIn", true).apply();
                        Log.d(TAG, "Admin login successfully, redirecting to AdminRachelDash");
                        Toast.makeText(LoginLoginScreen.this, "Admin login ia successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginLoginScreen.this, AdminRachelDash.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Log.d(TAG, "Admin authentication has failed.");
                    Toast.makeText(LoginLoginScreen.this, "Admin authentication has failed.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d(TAG, "Admin login credentials invalid.");
            Toast.makeText(LoginLoginScreen.this, "Admin login credentials provided are invalid.", Toast.LENGTH_SHORT).show();
        }
    }
}