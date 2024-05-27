package com.example.budgetapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RachelSplashScreen extends AppCompatActivity {

    Animation topRachelAnim, BottomRachelAnim;
    ImageView imageView21F21817;
    TextView textV121F21817, textV221F21817;

    private static final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rachel_splash_screen);

        topRachelAnim = AnimationUtils.loadAnimation(this, R.anim.top_rachelanimation);
        BottomRachelAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_rachelanimation);

        imageView21F21817 = findViewById(R.id.logo_id);
        textV121F21817 = findViewById(R.id.BudgetS);
        textV221F21817 = findViewById(R.id.below);

        imageView21F21817.startAnimation(topRachelAnim);
        textV121F21817.startAnimation(BottomRachelAnim);
        textV221F21817.startAnimation(BottomRachelAnim);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(RachelSplashScreen.this, LoginLoginScreen.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
