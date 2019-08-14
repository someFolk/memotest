package com.thebreadiswhite.memotest.activities;


import android.app.AlarmManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.thebreadiswhite.memotest.R;

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

        onBoot();

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

    // This function will handle all the stuff that
    // required when the app first booting for example:
    // 1. Setting alarms
    // 2. Fetch data from DB
    private void onBoot()
    {
        AlarmManager alarmManager= (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
    }
}
