package com.example.kidslearningapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MathsGame extends AppCompatActivity {
    private TextView questionText, answerText;
    private Button submitButton;
    private String childname;
    private int currentLevel;
    private int correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths_game);

        childname = getIntent().getStringExtra("childname");
        currentLevel = getIntent().getIntExtra("level", 1); // Start from level 1

        if (childname == null) {
            throw new IllegalArgumentException("Child name is required to track progress.");
        }

        questionText = findViewById(R.id.question_text);
        answerText = findViewById(R.id.answer_text);
        submitButton = findViewById(R.id.submit_button);

        generateQuestion();

        submitButton.setOnClickListener(v -> checkAnswer());
    }

    private void generateQuestion() {
        Random random = new Random();
        int num1 = random.nextInt(currentLevel * 10) + 1; // Generate number between 1 and currentLevel*10
        int num2 = random.nextInt(currentLevel * 10) + 1;
        boolean isAddition = random.nextBoolean(); // Randomly choose between addition and subtraction

        if (isAddition) {
            correctAnswer = num1 + num2;
            questionText.setText("What is " + num1 + " + " + num2 + "?");
        } else {
            // Ensure no negative answers for simplicity
            if (num1 < num2) {
                int temp = num1;
                num1 = num2;
                num2 = temp;
            }
            correctAnswer = num1 - num2;
            questionText.setText("What is " + num1 + " - " + num2 + "?");
        }
    }

    private void checkAnswer() {
        String answer = answerText.getText().toString().trim();
        if (!answer.isEmpty() && Integer.parseInt(answer) == correctAnswer) {
            // Correct answer
            currentLevel++;
            trackProgress(currentLevel);
            generateQuestion();
            answerText.setText("");
            Toast.makeText(MathsGame.this, "Congrats correct answer! Moving to next level.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MathsGame.this, "Try again!", Toast.LENGTH_SHORT).show();
        }
    }

    private void trackProgress(int level) {
        Database db = new Database(this, "learnnplay.db", null, 3);
        db.addMathProgress(childname, String.valueOf(level));
    }
}
