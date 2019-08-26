package com.thebreadiswhite.memotest.activities;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.thebreadiswhite.memotest.R;
import com.thebreadiswhite.memotest.dialogs.MemotestDialogCourseCreate;
import com.thebreadiswhite.memotest.util.MemotestConfig;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Splash extends AppCompatActivity
{

    /** Duration of wait **/
    // This need to be 2500 for best effects
    private int SPLASH_DISPLAY_LENGTH = 700;

    private Class nextActivity = MemotestFrontPage.class;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {

        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        //onBoot();


        //
        //        startService(new Intent(getApplicationContext(), FireBaseChangesWorker.class));
        //        Intent gotothere = new Intent(getApplicationContext(), DatabaseManager.class);
        //        startActivity(gotothere);
        //        if(true) return;

        // Transition moving through activities
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);



        // Enables full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen

        /* Handler to start the main activity
         * and close this Splash-Screen  without follow */

        /* Splash screen responsible of creating AppData */
        /*           AppData            */
        String uniqueId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        //Toast.makeText(getApplicationContext(), uniqueId, Toast.LENGTH_LONG).show();



        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                //Intent mainIntent = new Intent(Splash.this,Listing.class);
                Intent mainIntent = new Intent(Splash.this, nextActivity);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    // This is the on boot method
    // i use it for now as a tester
    private void onBoot()
    {
        Intent intent = new Intent(getApplicationContext(), MemotestStackDisplay.class);
        intent.putExtra(MemotestConfig.MEMOTEST_EXTRA_ID_STACK_DISPLAY_CONST,1);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + ( 10 * 1000 ), pendingIntent);
    }



    private ArrayList<Long> init(int dayDuration, int targetHour, int targetMinute)
    {
        // The list of the times of the alerts
        ArrayList<Long> alarmsDateInMills = new ArrayList<>();

        // The calendar object to process the millis
        Calendar calObject = Calendar.getInstance();

        // Getting the current time
        Date d = calObject.getTime();


        d.setMinutes(targetMinute);
        d.setHours(targetHour);
        calObject.setTime(d);

        // Checking whether to start today or tomorow
        if(d.getHours() > targetHour || (d.getHours() == targetHour && d.getMinutes() >= targetMinute)) calObject.add(Calendar.DATE, 1);


        for(int i = 0; i<= dayDuration; i++)
        {
            long timeInMillis = calObject.getTimeInMillis();
            alarmsDateInMills.add(timeInMillis);
            calObject.add(Calendar.DATE, 1);
        }

        return alarmsDateInMills;
    }
}
