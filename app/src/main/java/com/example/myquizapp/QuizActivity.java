package com.example.myquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.graphics.Color;


import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private Button submitButton;
    private ProgressBar progressBar;

    private String[] questions = {
            "What is the result of 2 + 2?",
            "What is the capital of France?",
            "Who is the President of the United States?",
            "What is the square root of 25?",
            "What is the capital of Japan?",
            "Who is the Prime Minister of the United Kingdom?",
            "What is 5 multiplied by 7?",
            "What is the capital of Australia?",
            "Who is the Chancellor of Germany?",
            "What is 10 divided by 2?"
    };
    private String[][] options = {
            {"3", "4", "5", "6"},
            {"London", "Paris", "Berlin", "Madrid"},
            {"Joe Biden", "Barack Obama", "Donald Trump", "George W. Bush"},
            {"3", "4", "5", "6"},
            {"Beijing", "Tokyo", "Seoul", "Singapore"},
            {"Boris Johnson", "Theresa May", "David Cameron", "Tony Blair"},
            {"25", "35", "40", "45"},
            {"Sydney", "Melbourne", "Canberra", "Brisbane"},
            {"Angela Merkel", "Franz-Walter Steinmeier", "Gerhard Schröder", "Helmut Kohl"},
            {"2", "4", "5", "6"}
    };
    private int[] correctAnswers = {1, 1, 0, 2, 1, 0, 2, 2, 0, 1};

    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionTextView = findViewById(R.id.questionTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        progressBar = findViewById(R.id.progressBar);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        loadQuestion(currentQuestionIndex);
    }

    private void loadQuestion(int index) {
        if (index < questions.length) {
            questionTextView.setText(questions[index]);
            optionsRadioGroup.removeAllViews();
            for (int i = 0; i < options[index].length; i++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(options[index][i]);
                optionsRadioGroup.addView(radioButton);
            }
            optionsRadioGroup.check(-1);
            progressBar.setProgress((index + 1) * 100 / questions.length);
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer();
                }
            });
        } else {
            showFinalScore();
        }
    }

    private void checkAnswer() {
        int selectedOptionId = optionsRadioGroup.getCheckedRadioButtonId();
        if (selectedOptionId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedOptionId);
            int selectedAnswerIndex = optionsRadioGroup.indexOfChild(selectedRadioButton);

            // Change colors of options based on correctness
            for (int i = 0; i < optionsRadioGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) optionsRadioGroup.getChildAt(i);
                if (i == correctAnswers[currentQuestionIndex]) {
                    radioButton.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                } else {
                    radioButton.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }
            }

            // Disable radio buttons to prevent further selection
            for (int i = 0; i < optionsRadioGroup.getChildCount(); i++) {
                optionsRadioGroup.getChildAt(i).setEnabled(false);
            }

            // Increment score if answer is correct
            if (selectedAnswerIndex == correctAnswers[currentQuestionIndex]) {
                score++;
            }

            // Change submit button text to "Next" and handle moving to next question
            submitButton.setText("Next");
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentQuestionIndex++;
                    if (currentQuestionIndex < questions.length) {
                        loadQuestion(currentQuestionIndex);
                        submitButton.setText("Submit");
                    } else {
                        showFinalScore();
                    }
                }
            });
        }
    }


    private void showFinalScore() {
        Intent intent = new Intent(QuizActivity.this, FinalScoreActivity.class);
        intent.putExtra("SCORE", score);
        startActivity(intent);
        finish();
    }
}

