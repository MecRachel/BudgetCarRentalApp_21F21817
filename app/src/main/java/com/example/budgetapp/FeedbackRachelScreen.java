package com.example.budgetapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackRachelScreen extends AppCompatActivity {

    private EditText f21F21817eedbackEditText;
    private Button s21F21817ubmitFeedbackButton;
    private DatabaseReference f21F21817eedbackDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_feedback_rachel_screen);

        Toolbar tool21F21817 = findViewById(R.id.toolbar);
        setSupportActionBar(tool21F21817);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbackbutton);

        f21F21817eedbackEditText = findViewById(R.id.feedbackEditText);
        s21F21817ubmitFeedbackButton = findViewById(R.id.submitFeedbackButton);
        f21F21817eedbackDatabase = FirebaseDatabase.getInstance().getReference("feedback");

        s21F21817ubmitFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
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

    private void submitFeedback() {
        String Rachiefeedback = f21F21817eedbackEditText.getText().toString().trim();

        if (TextUtils.isEmpty(Rachiefeedback)) {
            Toast.makeText(this, "Please enter your feedback", Toast.LENGTH_SHORT).show();
            return;
        }

        String feedbackId21F21817 = f21F21817eedbackDatabase.push().getKey();
        Feedback feedbackEntry21F21817 = new Feedback(feedbackId21F21817, Rachiefeedback);

        f21F21817eedbackDatabase.child(feedbackId21F21817).setValue(feedbackEntry21F21817)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(FeedbackRachelScreen.this, "Feedback  has been submitted successfully", Toast.LENGTH_SHORT).show();
                        f21F21817eedbackEditText.setText("");
                    } else {
                        Toast.makeText(FeedbackRachelScreen.this, "Failed to submit your feedback", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static class Feedback {
        public String f21F21817eedbackId;
        public String f21F21817eedbackText;

        public Feedback() {
        }

        public Feedback(String feedbackId, String feedbackText) {
            this.f21F21817eedbackId = feedbackId;
            this.f21F21817eedbackText = feedbackText;
        }

        public String getFeedbackId() {
            return f21F21817eedbackId;
        }

        public void setFeedbackId(String feedbackId) {
            this.f21F21817eedbackId = feedbackId;
        }

        public String getFeedbackText() {
            return f21F21817eedbackText;
        }

        public void setFeedbackText(String feedbackText) {
            this.f21F21817eedbackText = feedbackText;
        }
    }
}
