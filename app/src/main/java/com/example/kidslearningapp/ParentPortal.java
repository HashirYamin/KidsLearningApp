package com.example.kidslearningapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ParentPortal extends AppCompatActivity {
    private TextView childNameText, alphabetProgressText, shapesProgressText, mathProgressText;
    private ProgressBar alphabetProgressBar, shapesProgressBar, mathProgressBar;
    private Database database;
    private String childname;
    Button alphabetPdfButton, shapesPdfButton, mathPdfButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_portal);
        database = new Database(this, "learnnplay.db", null, 3);

        // Retrieve child name from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("child_name", Context.MODE_PRIVATE);
        childname = sharedPreferences.getString("childname", "");

        childNameText = findViewById(R.id.child_name);
        alphabetProgressText = findViewById(R.id.alphabet_progress_text);
        shapesProgressText = findViewById(R.id.shapes_progress_text);
        mathProgressText = findViewById(R.id.math_progress_text);
        alphabetProgressBar = findViewById(R.id.alphabet_progress_bar);
        shapesProgressBar = findViewById(R.id.shapes_progress_bar);
        mathProgressBar = findViewById(R.id.math_progress_bar);
        alphabetPdfButton = findViewById(R.id.pdf_alphabet_learning);
        shapesPdfButton = findViewById(R.id.pdf_shapes_learning);
        mathPdfButton = findViewById(R.id.pdf_maths_learning);

        // Set child name
        childNameText.setText("Child Name: " + childname);

        // Display progress
        displayProgress();
    }

    private void displayProgress() {
        int alphabetProgress = database.getAlphabetProgress(childname);
        int shapesProgress = database.getShapesProgress(childname);
        int mathProgress = database.getMathProgress(childname);

        // Assuming the total number of alphabets is 26, shapes is 10, and math levels is 10
        alphabetProgressBar.setMax(26);
        shapesProgressBar.setMax(7);
        mathProgressBar.setMax(50);

        alphabetProgressBar.setProgress(alphabetProgress);
        shapesProgressBar.setProgress(shapesProgress);
        mathProgressBar.setProgress(mathProgress);

        alphabetProgressText.setText("Alphabet Progress: " + alphabetProgress + " letters learned");
        shapesProgressText.setText("Shapes Progress: " + shapesProgress + " shapes learned");
        mathProgressText.setText("Math Progress: Level " + mathProgress + " reached");

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
    }
}
