package com.example.kidslearningapp;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ShapesLearning extends AppCompatActivity {
    private TextView shapeQuestionText;
    private ImageView shapeImage;
    private Button option1Button, option2Button, option3Button;
    private String childname;
    private String[] shapes = {"Circle", "Square", "Rectangle", "Hexagon", "Pentagon", "Star", "Diamond"};
    private int[] shapeImages = {R.drawable.circle, R.drawable.square, R.drawable.rectangle, R.drawable.hexagon, R.drawable.pentagon, R.drawable.star, R.drawable.diamond};
    private String currentShape;
    private Database database;
    private int currentShapeIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shapes_learning);

        childname = getIntent().getStringExtra("childname");
        database = new Database(this, "learnnplay.db", null, 3);

        shapeQuestionText = findViewById(R.id.shape_question_text);
        shapeImage = findViewById(R.id.shape_image);
        option1Button = findViewById(R.id.option1_button);
        option2Button = findViewById(R.id.option2_button);
        option3Button = findViewById(R.id.option3_button);

        generateQuestion();

        option1Button.setOnClickListener(v -> checkAnswer(option1Button.getText().toString()));
        option2Button.setOnClickListener(v -> checkAnswer(option2Button.getText().toString()));
        option3Button.setOnClickListener(v -> checkAnswer(option3Button.getText().toString()));
    }

    private void generateQuestion() {
        Random random = new Random();
        currentShapeIndex = random.nextInt(shapes.length);
        currentShape = shapes[currentShapeIndex];
        shapeImage.setImageResource(shapeImages[currentShapeIndex]);
        shapeQuestionText.setText("What shape is this?");

        List<String> options = new ArrayList<>();
        options.add(currentShape);

        while (options.size() < 3) {
            String randomShape = shapes[random.nextInt(shapes.length)];
            if (!options.contains(randomShape)) {
                options.add(randomShape);
            }
        }

        Collections.shuffle(options);

        option1Button.setText(options.get(0));
        option2Button.setText(options.get(1));
        option3Button.setText(options.get(2));

        option1Button.setBackgroundColor(Color.parseColor("#FF00C0"));
        option2Button.setBackgroundColor(Color.parseColor("#00FFEA"));
        option3Button.setBackgroundColor(Color.parseColor("#2FFF00"));
    }

    private void checkAnswer(String selectedShape) {
        if (selectedShape.equals(currentShape)) {
            database.addShapesProgress(childname, currentShape);
            Toast.makeText(ShapesLearning.this, "Congrats correct answer! Moving to next shape.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ShapesLearning.this, "Incorrect! Try again.", Toast.LENGTH_SHORT).show();
        }
        generateQuestion();
    }
}
