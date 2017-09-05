package com.example.android.musicplayer;

import android.icu.util.TimeUnit;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.musicplayer.R.id.btnBackward;
import static com.example.android.musicplayer.R.id.btnPause;


public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button btnBackward, btnPlay, btnPause, btnForward;

    private double startTime = 0;
    private double finalTime = 0;

    private Handler myHandler = new Handler();
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekBar;
    private TextView txtTitle, txtBackwardTime, txtForwardTime;

    public static int oneTimeOnly = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBackward = (Button) findViewById(R.id.btnBackward);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnForward = (Button) findViewById(R.id.btnForward);

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtBackwardTime = (TextView) findViewById(R.id.txtBackwardTime);
        txtForwardTime = (TextView) findViewById(R.id.txtForwardTime);

        txtTitle.setText("Vangelis - Star");

        mediaPlayer = MediaPlayer.create(this, R.raw.star);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
//        seekBar.setClickable(false);
        btnPause.setEnabled(false);

        btnPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Играет музыка", Toast.LENGTH_SHORT).show();
                mediaPlayer.start();

                finalTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();

                if (oneTimeOnly == 0) {
                    seekBar.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }

                txtBackwardTime.setText(String.format("%d min, %d sec",
                        java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds((long) finalTime) - java.util.concurrent.TimeUnit.MINUTES.toSeconds((java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes((long) startTime))
                        )));

                txtForwardTime.setText(String.format("%d min, %d sec",
                        java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds((long) startTime) - java.util.concurrent.TimeUnit.MINUTES.toSeconds((java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes((long) startTime))
                        )));

                seekBar.setProgress((int) startTime);
                myHandler.postDelayed(UpdateSongTime, 100);
                btnPause.setEnabled(true);
                btnPlay.setEnabled(false);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Пауза", Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();
                btnPause.setEnabled(false);
                btnPlay.setEnabled(true);
            }
        });


    }
    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            txtForwardTime.setText(String.format("%d min, %d sec",
                    java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds((long) startTime),
                    java.util.concurrent.TimeUnit.MINUTES.toSeconds((java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes((long) startTime))
                    )));
            seekBar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };
}
