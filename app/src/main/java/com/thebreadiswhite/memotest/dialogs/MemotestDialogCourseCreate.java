package com.thebreadiswhite.memotest.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.thebreadiswhite.memotest.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MemotestDialogCourseCreate extends DialogFragment
{

    public static final String TAG = "MemotestDialogCourse";

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        // Get bundles
    }

    @Override
    public void onStart()
    {
        super.onStart();
        super.getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    // get the current day
    // and than figure out the time


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_memotest_course_create, container, false);

        return view;
    }

    private ArrayList<Long> init(int dayDuration, int targetHour, int targetMinute)
    {
        // The list of the times of the alerts
        ArrayList<Long> alarmsDateInMills = new ArrayList<>();

        // The calendar object to process the millis
        Calendar calObject = Calendar.getInstance();

        // Getting the current time
        Date d = calObject.getTime();

        // Getting the current day
        int baseDay = d.getDay();


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
