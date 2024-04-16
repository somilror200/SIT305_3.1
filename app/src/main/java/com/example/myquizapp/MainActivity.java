package com.example.myquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText;
    private Button startQuizButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        startQuizButton = findViewById(R.id.startQuizButton);

        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Get the entered name
                    String name = nameEditText.getText().toString().trim();

                    // Check if the name is not empty
                    if (!name.isEmpty()) {
                        // Start the QuizActivity and pass the name to it
                        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                        intent.putExtra("NAME", name);
                        startActivity(intent);
                    } else {
                        // Show a message to enter the name
                        nameEditText.setError("Please enter your name");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
