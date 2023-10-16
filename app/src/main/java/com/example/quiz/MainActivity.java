package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView questionTextView;
    private Button trueButton, falseButton, nextButton;
    private Question[] questions = new Question[] {
            new Question(R.string.q_activity, true),
            new Question(R.string.q_version, false),
            new Question(R.string.q_listener, true),
            new Question(R.string.q_resources, true),
            new Question(R.string.q_find_resources, false)
    };
    private int currentIndex = 0;
    private int correctAnswersCount = 0;
    private boolean answered = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionTextView = findViewById(R.id.question_text_view);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNextQuestion();
            }
        });
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }
    private void checkAnswerCorrectness(boolean userAnswer) {
        if (answered) {
            return;
        }
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int messageId;
        if (userAnswer == correctAnswer){
            messageId = R.string.correct_answer;
            correctAnswersCount++;
        } else {
            messageId = R.string.incorrect_answer;
        }
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
        answered = true;

        if (currentIndex == questions.length - 1) {
            showResult();
        }
    }
    private void setNextQuestion(){
        currentIndex = (currentIndex + 1) % questions.length;
        if (currentIndex == 0) {
            correctAnswersCount = 0;
        }
        questionTextView.setText(questions[currentIndex].getQuestionId());
        answered = false;
    }
    private void showResult() {
        String resultMessage = getString(R.string.result_message, correctAnswersCount, questions.length);
        Toast.makeText(this, resultMessage, Toast.LENGTH_LONG).show();
        if (correctAnswersCount == questions.length) {
            nextButton.setEnabled(false);
        }
    }
}