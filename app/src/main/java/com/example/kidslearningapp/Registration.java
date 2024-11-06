package com.example.kidslearningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Registration extends AppCompatActivity {

    EditText stdName, Age, pass, conf_pass;
    TextView registrationText;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        stdName = findViewById(R.id.std_name);
        Age = findViewById(R.id.age);
        pass = findViewById(R.id.password);
        conf_pass = findViewById(R.id.confirm_password);

        register = findViewById(R.id.register_btn);

        registrationText = findViewById(R.id.regiteration_title);
        Animation move = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        registrationText.startAnimation(move);

        Intent intent = new Intent(Registration.this, LoginActivity.class);

        register.setOnClickListener(v -> {
            String studentName = stdName.getText().toString();
            String age = Age.getText().toString();
            String password = pass.getText().toString();
            String confirmPassword = conf_pass.getText().toString();

            if (studentName.isEmpty() || age.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(Registration.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
            } else if (studentName.length() < 3 || password.length() < 3 || confirmPassword.length() < 3) {
                Toast.makeText(Registration.this, "Username, mother name, and password must be at least 3 characters long", Toast.LENGTH_SHORT).show();
            } else {
                if (password.equals(confirmPassword)) {
                    Database db = new Database(getApplicationContext(), "learnnplay.db", null, 3);
                    db.register(studentName, age, password);
                    Toast.makeText(Registration.this, "Account created. Please login to continue.", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Registration.this, "Password and Re-type Password didn't match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}