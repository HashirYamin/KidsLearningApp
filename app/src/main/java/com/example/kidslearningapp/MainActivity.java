package com.example.kidslearningapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    CardView AlphabetLearning, MathGame, ProgressTracking, ShapesLearning, ParentPortalCard, Logout;
    TextView profile, profileText;
    BroadcastReceiver broadcastReceiver = null;
    private boolean isReceiverRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        broadcastReceiver = new InternetReceiver();
        InternetStatus();

        SharedPreferences sharedPreferences = getSharedPreferences("child_name", Context.MODE_PRIVATE);
        String childName = sharedPreferences.getString("childname", "");
        Toast.makeText(this, "Welcome " + childName, Toast.LENGTH_SHORT).show();

        profile = findViewById(R.id.profile);
        if (childName.length() >= 2) {
            profile.setText(childName.substring(0, 2));
        } else {
            profile.setText(childName);
        }
        profileText = findViewById(R.id.profile_text);
        profileText.setText("Hi! "+childName+" Let's Get Started");

        AlphabetLearning = findViewById(R.id.alphabet_learning);
        ShapesLearning = findViewById(R.id.shape_learning);
        MathGame = findViewById(R.id.math_game);
        ProgressTracking = findViewById(R.id.progress_tracking);
        ParentPortalCard = findViewById(R.id.parent_portal);

        Logout = findViewById(R.id.logout);

        Logout.setOnClickListener(view -> {
            SharedPreferences pref = getSharedPreferences("login_check", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("flag", false);
            editor.apply();
            startActivity(new Intent(MainActivity.this, LoginAcitivity.class));
            finish();
        });

        AlphabetLearning.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AlphabetLearning.class);
            intent.putExtra("childname", childName);
            startActivity(intent);
        });
        ShapesLearning.setOnClickListener(v -> { // Add this block to set the onClickListener for ShapesLearning
            Intent intent = new Intent(MainActivity.this, ShapesLearning.class);
            intent.putExtra("childname", childName);
            startActivity(intent);
        });

        MathGame.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MathsGame.class);
            intent.putExtra("childname", childName);
            intent.putExtra("level", 1); // Starting level for the math game
            startActivity(intent);
        });

        ProgressTracking.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProgressTracking.class);
            intent.putExtra("childname", childName);
            startActivity(intent);
        });

        ParentPortalCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ParentPortal.class);
            intent.putExtra("childname", childName);
            startActivity(intent);
        });

    }

    public void InternetStatus() {
        if (!isReceiverRegistered) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
            registerReceiver(broadcastReceiver, filter);
            isReceiverRegistered = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isReceiverRegistered) {
            unregisterReceiver(broadcastReceiver);
            isReceiverRegistered = false;
        }
    }
}
