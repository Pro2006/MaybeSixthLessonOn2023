package com.example.maybesixthlessonon2023;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btnStart;
    private Button btnPause;
    private Button btnStop;
    private TextView timeOut;
    private Button btnTimer;

    private long startTime = 0L; // стартовое время
    private long timeInMilliseconds = 0L; // время в миллисекундах
    private long timePause = 0L; // время в состоянии паузы
    private long updateTime = 0L; // обновлённое время

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);
        timeOut = findViewById(R.id.timeOut);
        btnTimer = findViewById(R.id.btnTimer);

        btnStart.setOnClickListener(listener);
        btnPause.setOnClickListener(listener);
        btnStop.setOnClickListener(listener);
        btnTimer.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnStart:
                    startTime = SystemClock.uptimeMillis();
                    handler.postDelayed(updateTimerThread, 0);
                    break;
                case R.id.btnPause:
                    timePause += timeInMilliseconds;
                    handler.removeCallbacks(updateTimerThread);
                    break;
                case R.id.btnStop:
                    startTime = 0L;
                    timePause = 0L;
                    handler.removeCallbacks(updateTimerThread);
                    timeOut.setText("Время остановилось!");
                case R.id.btnTimer:
                    startTime = -2 * SystemClock.uptimeMillis() + startTime;
                    handler.removeCallbacks(updateTimerThread);
                    handler.postDelayed(updateTimerThread, 0);
                    break;

            }
        }
    };

    private Runnable updateTimerThread = new Runnable() {
        @SuppressLint({"SetTextI18n", "DefaultLocale"})
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updateTime = timePause + timeInMilliseconds;

            int milliseconds = (int) (updateTime % 1000);
            int second = (int) (updateTime / 1000);
            int minute = second / 60;
            int hour = minute / 60;
            int day = hour / 24;

            second = second % 60;
            minute = minute % 60;
            hour = hour % 24;

            timeOut.setText("" + String.format("%03d", day) + ":" + String.format("%02d", hour)
                    + ":" + String.format("%02d", minute) + String.format("%02d", second)
                    + "." + String.format("%03d", milliseconds));
            handler.postDelayed(this, 0);
        }
    };


}