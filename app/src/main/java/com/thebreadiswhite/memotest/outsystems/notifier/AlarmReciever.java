package com.thebreadiswhite.memotest.outsystems.notifier;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.thebreadiswhite.memotest.modules.stack.MemotestStack;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class AlarmReciever extends BroadcastReceiver
{

    private final int REQUEST_CODE = 101;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_SHORT).show();

        Intent gotoIntent = new Intent(context, MemotestStack.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,REQUEST_CODE,gotoIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, System.currentTimeMillis() + AlarmManager.INTERVAL_HOUR, AlarmManager.INTERVAL_HOUR, pendingIntent);

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 5);
        calendar.set(Calendar.MINUTE, 30);

        alarmManager.setExact(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

    }
}
