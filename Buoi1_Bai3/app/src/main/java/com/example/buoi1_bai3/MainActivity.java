package com.example.buoi1_bai3;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private Spinner spinner;
    private Button btnSetAlarm, btnStopAlarm;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private int durations;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker=findViewById(R.id.timePicker);
        spinner=findViewById(R.id.spinner);
        btnSetAlarm=findViewById(R.id.btnSetAlarm);
        btnStopAlarm=findViewById(R.id.btnStopAlarm);
        alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                switch (selectedItem) {
                    case "1 minute":
                        durations = 1;
                        break;
                    case "5 minutes":
                        durations = 5;
                        break;
                    case "10 minutes":
                        durations = 10;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                durations = 0;
            }
        });

        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour=timePicker.getCurrentHour();
                int minute=timePicker.getCurrentMinute();

                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,hour);
                calendar.set(Calendar.MINUTE,minute);
                calendar.set(Calendar.SECOND,0);

                Intent intent=new Intent(MainActivity.this,AlarmReceiver.class);
                intent.putExtra("alarmTime",hour+":"+(minute<10?"0"+minute:minute));
                pendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                long triggerAtMillis=calendar.getTimeInMillis()+durations*60*1000;
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,triggerAtMillis,pendingIntent);
                Toast.makeText(MainActivity.this,"Alarm set for "+hour+":"+(minute<10?"0"+minute:minute)+"in"+durations+"minutes",Toast.LENGTH_LONG).show();
            }
        });

        btnStopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pendingIntent!=null){
                    alarmManager.cancel(pendingIntent);
                }
                Toast.makeText(MainActivity.this, "Alarm stopped",Toast.LENGTH_LONG).show();
            }
        });
    }
}
