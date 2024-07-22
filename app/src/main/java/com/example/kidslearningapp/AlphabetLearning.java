package com.example.kidslearningapp;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.HashMap;
import java.util.Map;

public class AlphabetLearning extends AppCompatActivity {

    private ImageView displayImage;
    private Map<Character, Integer> letterToSound;
    private Map<Character, Integer> letterToImage;
    private LinearLayout buttonContainer;
    private String childname; // Variable to store child's name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet_learning);

        childname = getIntent().getStringExtra("childname");


        if (childname == null) {
            throw new IllegalArgumentException("Child name is required to track progress.");
        }

        displayImage = findViewById(R.id.iv_display_image);
        buttonContainer = findViewById(R.id.linear_layout_buttons);


        letterToSound = new HashMap<>();
        letterToImage = new HashMap<>();
        initializeResources();

        createAlphabetButtons();

        displayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayImage.setVisibility(View.GONE);
            }
        });
    }
    private void initializeResources() {

        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            letterToSound.put(alphabet, getResources().getIdentifier(String.valueOf(alphabet).toLowerCase(), "raw", getPackageName()));
            letterToImage.put(alphabet, getResources().getIdentifier(String.valueOf(alphabet).toLowerCase(), "drawable", getPackageName()));
        }
    }
    private void createAlphabetButtons() {
        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            final char currentAlphabet = alphabet;
            Button button = new Button(this);
            button.setText(String.valueOf(alphabet));
            button.setTextSize(24f);
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            button.setBackgroundColor(Color.TRANSPARENT);

            button.setOnClickListener(v -> {
                char letter = currentAlphabet;
                playSound(letterToSound.get(letter));
                displayImage.setImageResource(letterToImage.get(letter));
                displayImage.setVisibility(View.VISIBLE);
                Toast.makeText(AlphabetLearning.this, "Congrats! You learned an alphabet", Toast.LENGTH_SHORT).show();
                trackProgress(letter);
            });
            buttonContainer.addView(button);
        }
    }
    private void trackProgress(char letter) {
        Database db = new Database(this, "learnnplay.db", null, 3);
        db.addAlphabetProgress(childname, letter);
    }
    private void playSound(int soundResource) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, soundResource);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }
}
