package com.example.buoi1_bai3;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class AlarmAlertActivity extends Activity {
    private TextView alarmTimeTextView;
    private Button btnStopAlarm;
    private Vibrator vibrator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_alert);

        alarmTimeTextView=findViewById(R.id.alarmTimeTextView);
        btnStopAlarm=findViewById(R.id.btnStopAlarm);
        vibrator=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        Intent intent=getIntent();
        String alarmTime=intent.getStringExtra("alarmTime");
        alarmTimeTextView.setText("Alarm Time: "+alarmTime);

        if(vibrator!=null&&vibrator.hasVibrator()){
            long[] pattern={0,1000,1000}; // Rung 1 giây, nghỉ 1 giây
            vibrator.vibrate(pattern,0);
        }

        btnStopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vibrator!=null&&vibrator.hasVibrator()){
                    vibrator.cancel();
                }
                finish();
            }
        });
    }
}
