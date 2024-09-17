package com.example.buoi1_bai3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String alarmTime=intent.getStringExtra("alarmTime");

        Intent alarmIntent=new Intent(context, AlarmAlertActivity.class);
        alarmIntent.putExtra("alarmTime",alarmTime);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarmIntent);
    }
}
