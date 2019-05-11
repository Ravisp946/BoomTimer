package com.example.asus.boomtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    Button alarmButton;
    boolean countactive=false;
    MediaPlayer mediaPlayer;
    CountDownTimer countDownTimer;
    public void resetTimer()
    {
        timerTextView.setText("00:30");
        timerSeekBar.setEnabled(true);
        timerSeekBar.setProgress(30);
        alarmButton.setText("GO!");
        countactive=false;
        mediaPlayer.pause();
        countDownTimer.cancel();
    }
    public void alarmButton(View view) {
        if (countactive) {
            resetTimer();

        }
//        Log.i("Info!","Button Pressed!");
        else {
            timerSeekBar.setEnabled(false);
            alarmButton.setText("STOP!");
            countactive=true;
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeupdate((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    Log.i("Finished!!", "0:0");
                    final MediaPlayer mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.boom);
                    mediaPlayer.start();
                    final CountDownTimer countDownTimer2 = new CountDownTimer(5000, 5) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            
                        }

                        @Override
                        public void onFinish() {
                            mediaPlayer.stop();
                        }


                        }.start();
                    resetTimer();

                }
            }.start();
        }
    }
    public void timeupdate(int i)
    {
        int minutes=i/60;
        int seconds=i-(minutes*60);

        String secondstring=Integer.toString(seconds),minutestring=Integer.toString(minutes);
        if(seconds<=9)
        {
            secondstring="0"+secondstring;
        }
        if(minutes<10)
            minutestring="0"+minutestring;

        timerTextView.setText(minutestring+":"+secondstring);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmButton=(Button)findViewById(R.id.goButton);
        timerSeekBar=(SeekBar)findViewById(R.id.timerSeekBar);
        mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.boom);
        timerTextView=(TextView)findViewById(R.id.timerTextView);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Value of Seekbar!",Integer.toString(progress));

                timeupdate(progress);

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
