package com.example.kidslearningapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProgressTracking extends AppCompatActivity {
    private Database database;
    private TextView alphabetProgressText, shapesProgressText, mathProgressText;
    private String childname;
    private Button improveAlphabet, improveShapes, improveMath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_tracking);

        database = new Database(this, "learnnplay.db", null, 3);
        alphabetProgressText = findViewById(R.id.tv_alphabet_progress);
        shapesProgressText = findViewById(R.id.tv_shapes_progress);
        mathProgressText = findViewById(R.id.tv_math_progress);
        improveAlphabet = findViewById(R.id.improve_alphabet);
        improveShapes = findViewById(R.id.improve_shapes);
        improveMath = findViewById(R.id.improve_math);

        childname = getIntent().getStringExtra("childname");

        int alphabetProgress = database.getAlphabetProgress(childname);
        int shapesProgress = database.getShapesProgress(childname);
        int mathProgress = database.getMathProgress(childname);

        alphabetProgressText.setText("You have learned " + alphabetProgress + " letters!");
        shapesProgressText.setText("You have learned " + shapesProgress + " shapes!");
        mathProgressText.setText("You have reached level " + mathProgress + " in math!");

        // Display congratulations message if progress is maxed
        if (alphabetProgress == 26) {
            Toast.makeText(this, "Congratulations! You have learned all the letters!", Toast.LENGTH_LONG).show();
        }
        if (shapesProgress == 7) {
            Toast.makeText(this, "Congratulations! You have learned all the shapes!", Toast.LENGTH_LONG).show();
        }
        if (mathProgress == 50) {
            Toast.makeText(this, "Congratulations! You have reached the highest level in math!", Toast.LENGTH_LONG).show();
        }

        improveAlphabet.setOnClickListener(v -> {
            Intent intent = new Intent(ProgressTracking.this, AlphabetLearning.class);
            intent.putExtra("childname", childname);
            startActivity(intent);
        });

        improveShapes.setOnClickListener(v -> {
            Intent intent = new Intent(ProgressTracking.this, ShapesLearning.class);
            intent.putExtra("childname", childname);
            startActivity(intent);
        });

        improveMath.setOnClickListener(v -> {
            Intent intent = new Intent(ProgressTracking.this, MathsGame.class);
            intent.putExtra("childname", childname);
            intent.putExtra("level", mathProgress + 1); // Pass the current level
            startActivity(intent);
        });
    }
}
