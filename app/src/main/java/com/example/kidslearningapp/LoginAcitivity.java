package com.example.kidslearningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginAcitivity extends AppCompatActivity {

    TextView notAcc;
    EditText name, pass;
    Button login, createAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivity);

        name = findViewById(R.id.child_name);
        pass = findViewById(R.id.lg_password);
        login = findViewById(R.id.Login_btn);
        notAcc = findViewById(R.id.dont_acc);
        createAcc = findViewById(R.id.create_acc);

        login.setBackgroundColor(Color.parseColor("#7A2700"));
        createAcc.setBackgroundColor(Color.parseColor("#7A2700"));

        login.setOnClickListener(v -> {
            String childName= name.getText().toString();
            String password = pass.getText().toString();
            Database db = new Database(getApplicationContext(),"learnnplay.db",null,3);
            if(childName.length() == 0 || password.length() == 0) {
                Toast.makeText(LoginAcitivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }
            else if(childName.length()<3){
                Toast.makeText(LoginAcitivity.this, "Please Enter correct Username", Toast.LENGTH_SHORT).show();
            } else if (password.length()<3) {
                Toast.makeText(LoginAcitivity.this, "Password length must be 3 or more", Toast.LENGTH_SHORT).show();
            } else {
                if(db.login(childName, password)==1){
                    SharedPreferences pref = getSharedPreferences("login_check",MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = pref.edit();
                    editor1.putBoolean("flag",true);
                    editor1.apply();

                    Toast.makeText(LoginAcitivity.this, "Login success", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("child_name",MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = sharedPreferences.edit();
                    editor2.putString("childname",childName);
                    editor2.apply();
                    startActivity(new Intent(LoginAcitivity.this, MainActivity.class));
                }else {
                    Toast.makeText(LoginAcitivity.this, "Invalid Username and Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        createAcc.setOnClickListener(view -> startActivity(new Intent(LoginAcitivity.this,Registration.class)));
        notAcc.setOnClickListener(view -> startActivity(new Intent(LoginAcitivity.this,Registration.class)));
    }
}