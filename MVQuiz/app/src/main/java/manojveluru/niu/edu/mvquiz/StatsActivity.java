package manojveluru.niu.edu.mvquiz;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;

public class StatsActivity extends AppCompatActivity {

    private Button quitButton, tryAgainButton;
    public static final String EXTRA_SCORE_SCORED = "extraScores";
    private static final int REQUEST_CODE_QUIZ = 1;
    public int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        quitButton = findViewById(R.id.quit);


        score = getIntent().getIntExtra(QuizActivity.EXTRA_SCORE,0);
        int count = getIntent().getIntExtra(QuizActivity.EXTRA_Q,0);

        //BigDecimal percentageCal = new BigDecimal(score*100).divide(new BigDecimal(count), MathContext.UNLIMITED);
        int percentageCal = (score*100)/count;
        TextView percentageDisplay = findViewById(R.id.progresspercentage);
        percentageDisplay.setText(percentageCal+"%");

        ProgressBar progressBar = findViewById(R.id.resultprogressBar);
            progressBar.setMax(count);
            progressBar.setProgress(score);


        if(score == count){
            TextView textView1 =  findViewById(R.id.trytext);
            textView1.setVisibility(View.INVISIBLE);
        }

        tryAgainButton = findViewById(R.id.tryagain);


        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finishAffinity();
            }
        });


        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent(StatsActivity.this,MainActivity.class);
                resultIntent.putExtra(EXTRA_SCORE_SCORED, score);
                setResult(RESULT_OK, resultIntent);
                startActivity(resultIntent);

            }
        });
    }//end onCreate

}
