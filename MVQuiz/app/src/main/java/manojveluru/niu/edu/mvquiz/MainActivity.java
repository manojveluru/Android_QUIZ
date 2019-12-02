package manojveluru.niu.edu.mvquiz;
/********************************************************************
 CSCI 522 - Graduate Student Project - Semester (Spring) Year - 2019

 Programmer(s): Manoj Veluru (Z1840907), Leela Krishna Vasista Gutta(Z1839489)
 Section: 1
 TA: Harshith Desamsetti
 Date Due: 05/06/2019

 Purpose: A Simple Quiz App

 *********************************************************************/
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button buttonStartQuiz;
    private static final int REQUEST_CODE_QUIZ = 1;

    public static final String EXTRA_DIFFICULTY = "extraDifficulty";

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";

    private TextView textViewHighScore;
    private Spinner spinnerDifficulty;

    private int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewHighScore = findViewById(R.id.text_view_highscore);
        spinnerDifficulty = findViewById(R.id.spinner_difficulty);

        String[] difficultyLevels = Question.getAllDifficultyLevels();

        ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, difficultyLevels);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);

        loadHighscore();

        buttonStartQuiz = findViewById(R.id.button_start_quiz);
        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });


        int score = getIntent().getIntExtra(StatsActivity.EXTRA_SCORE_SCORED, 0);
        if (score > highScore){
            updateHighscore(score); //To Update the highest score
        }

    }//end onCreate

    private void startQuiz()
    {
        String difficulty = spinnerDifficulty.getSelectedItem().toString();
        Intent intent = new Intent(MainActivity.this,QuizActivity.class);
        intent.putExtra(EXTRA_DIFFICULTY, difficulty);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }//end startQuiz

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ)
        {
            if (resultCode == RESULT_OK)
            {
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
                if (score > highScore){
                    updateHighscore(score);
                }
            }
        }
    }

    private void loadHighscore(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highScore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighScore.setText("Highscore: " +highScore);
    }//end loadHighScore

    private void updateHighscore(int highScoreNew)
    {
        highScore = highScoreNew;
        textViewHighScore.setText("Highscore: " +highScore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highScore);
        editor.apply();
    }// end updateHighScore
}
