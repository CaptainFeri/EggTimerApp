package com.example.a5eggtimerapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.a5eggtimerapp.R.raw.analog_watch_alarm_daniel;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    Button goButton;
    TextView timerTextView;
    MediaPlayer mediaPlayer;
    CountDownTimer timer;

    boolean timerIsActive = false;

    private void fetchView() {
        timerSeekBar = findViewById(R.id.timerSeekBar);
        goButton = findViewById(R.id.gobutton);
        timerTextView = findViewById(R.id.timerTextView);

    }

    public void goButtonPressed(View view) {
        if (timerIsActive) {
                goButton.setText("GO!");
                timerIsActive = false;
                timer.cancel();
                timerSeekBar.setEnabled(true);
                timerSeekBar.setProgress(30);
                mediaPlayer.stop();
        } else {
            goButton.setText("STOP!");
            timerIsActive = true;
            timerSeekBar.setEnabled(false);

            mediaPlayer = MediaPlayer.create(this, R.raw.analog_watch_alarm_daniel);

            timer = new CountDownTimer(timerSeekBar.getProgress()*1000,1000){

                @Override
                public void onTick(long millisUntilFinished) {
                    setTimer(millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {
                    mediaPlayer.start();
                }
            }.start();
        }
    }

    public void setTimer(long l){
        int minutes = (int) (l / 60);
        int seconds = (int) (l - (minutes) * 60);
        String secondString;
        String minuteString = Integer.toString(minutes);
        if (seconds < 10)
            secondString = "0" + Integer.toString(seconds);
        else
            secondString = Integer.toString(seconds);
        timerTextView.setText(minuteString + ":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchView();

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        setTimer(30);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
