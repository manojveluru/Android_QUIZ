package manojveluru.niu.edu.mvquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //create the TimerTask
        TimerTask task = new TimerTask() {
            @Override
            public void run()
            {
                //finish the activity
                finish();

                //Go to the MainActivity
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        };//end TimerTask

        //Create Timer object
        Timer timer = new Timer();
        //create the schedule for the timer
        timer.schedule(task, 5000);
    }//end onCreate
}
