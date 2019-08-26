package com.thebreadiswhite.memotest.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import com.thebreadiswhite.memotest.R;
import com.thebreadiswhite.memotest.modules.course.MemotestCourse;
import com.thebreadiswhite.memotest.modules.course.MemotestCourseDBH;
import com.thebreadiswhite.memotest.modules.course.alert.MemotestCourseAlert;
import com.thebreadiswhite.memotest.modules.course.alert.MemotestCourseAlertDBH;
import com.thebreadiswhite.memotest.util.DAY;
import android.graphics.drawable.GradientDrawable;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MemotestDialogCourseCreate extends DialogFragment
{

    public static final String KEY_STACKID = "stackid";

    // Holder for the input
    private class Timing
    {
        public int minutes;
        public int hour;
        public ConstraintLayout xml;
    }

    private class DaySelectHolder
    {
        public ConstraintLayout xml;
        public DAY day;

    }

    private int stackid;
    private ArrayList<Timing> timeInputs;
    private ArrayList<DaySelectHolder> daysToSelect;

    public static final String TAG = "MemotestDialogCourse";

    private LinearLayout linearContainer;
    private Button submitButton;
    private Button closeButton;
    private Button addButton;
    private TimePicker timePicker;

    // this is the number of days select
    private DAY numberOfDays;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        stackid = getArguments().getInt(KEY_STACKID);
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
        linearContainer = (LinearLayout) view.findViewById(R.id.memotest_dialog_course_create_linear);
        timeInputs = new ArrayList<>();

        closeButton = (Button) view.findViewById(R.id.memotest_dialog_course_create_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getDialog().dismiss();
            }
        });

        submitButton = (Button) view.findViewById(R.id.memotest_dialog_course_create_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                submit();
            }
        });

        addButton = (Button) view.findViewById(R.id.memotest_dialog_course_create_add_time);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                makeSubmitButtonClickable(true);
                getTimeInput();
            }
        });

        timePicker = (TimePicker) view.findViewById(R.id.memotest_dialog_course_create_timepicker);
        timePicker.setIs24HourView(true);

        // Initialize day button
        daysToSelect = new ArrayList<>();
        initDayButton(linearContainer,R.id.memotest_dialog_course_create_button_day1, DAY.SUNDAY);
        initDayButton(linearContainer,R.id.memotest_dialog_course_create_button_day2, DAY.MONDAY);
        initDayButton(linearContainer,R.id.memotest_dialog_course_create_button_day3, DAY.TUESDAY);
        initDayButton(linearContainer,R.id.memotest_dialog_course_create_button_day4, DAY.WENDSDAY);
        initDayButton(linearContainer,R.id.memotest_dialog_course_create_button_day5, DAY.THURSDAY);
        initDayButton(linearContainer,R.id.memotest_dialog_course_create_button_day6, DAY.FRIDAY);
        initDayButton(linearContainer,R.id.memotest_dialog_course_create_button_day7, DAY.SATURDAY);

        return view;
    }

    private void initDayButton(View parent, int resource, final DAY day)
    {
        DaySelectHolder selector = new DaySelectHolder();
        final ConstraintLayout xml = (ConstraintLayout) parent.findViewById(resource);
        xml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                selectThatDay(day);
            }
        });

        selector.day = day;
        selector.xml = xml;
        daysToSelect.add(selector);
    }

    private void selectThatDay(DAY day)
    {
        Boolean finishedHighlight = false;
        Boolean finishedDeHighlight = false;
        for(DaySelectHolder temp : daysToSelect)
        {
            if(temp.day == day)
            {
                highlightDay(temp.xml, true);
                finishedHighlight = true;
            }
            if(temp.day == numberOfDays)
            {
                highlightDay(temp.xml, false);
                finishedDeHighlight = true;
            }
            if(finishedDeHighlight && finishedHighlight) break;
        }
        numberOfDays = day;
    }

    private void highlightDay(ConstraintLayout view, boolean bool)
    {
        GradientDrawable drawable = (GradientDrawable)view.getBackground();
        if(bool) drawable.setStroke(3, getResources().getColor(R.color.blueColorRock1));
        else drawable.setStroke(1, getResources().getColor(R.color.stackoverflowAloneBorderGrey));
    }

    private void makeSubmitButtonClickable(boolean state)
    {
        if(state) submitButton.setAlpha(1.0f);
        else submitButton.setAlpha(0.5f);
    }

    private void submit()
    {
        if(timeInputs.size() < 1)
        {
            makeSubmitButtonClickable(false);
            return;
        }

        ArrayList<ArrayList<Long>> allNotifications = new ArrayList<>();
        long deadLine = 0;

        for(Timing temp : timeInputs)
        {
            ArrayList<Long> arr = initNotificationsData(numberOfDays.value, temp.hour, temp.minutes);
            allNotifications.add(arr);
            
            for(Long tempDeadline : arr)
            {
                deadLine = deadLine < tempDeadline ? tempDeadline : deadLine;
            }
        }

        // Initializing course
        MemotestCourseDBH courseDBH = new MemotestCourseDBH(getContext());
        MemotestCourse course = new MemotestCourse(0,stackid,numberOfDays.value, deadLine);
        int courseId  = courseDBH.insert(course);
        
        // Initializing Alerts
        MemotestCourseAlertDBH alertDBH = new MemotestCourseAlertDBH(getContext());
        MemotestCourseAlert alert;

        // Printing the arr
        for(ArrayList<Long> tempArr : allNotifications)
        {
            for(Long tempVal : tempArr)
            {
                alert = new MemotestCourseAlert(tempVal, courseId);
                alertDBH.insert(alert);
            }
        }

        Toast.makeText(getContext(), "Course have been created.", Toast.LENGTH_SHORT).show();
        getDialog().dismiss();
    }

    public void getTimeInput()
    {
        int hour = timePicker.getCurrentHour();
        int minutes = timePicker.getCurrentMinute();

        for(Timing temp : timeInputs)
        {
            if(temp.hour == hour && temp.minutes == minutes)
            {
                Toast.makeText(getContext(), "You already have submitted that specific time.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Timing holder = new Timing();
        holder.hour = hour;
        holder.minutes = minutes;
        timeInputs.add(holder);
        xmlAddTimeInput(holder);
    }

    private void xmlAddTimeInput(Timing timing)
    {
        View view = getLayoutInflater().inflate(R.layout.listitem_course_timing, linearContainer, false);
        final int hour = timing.hour;
        final int minutes = timing.minutes;

        String timeString = Integer.toString(hour) + ":" + Integer.toString(minutes);
        TextView text = (TextView) view.findViewById(R.id.memotest_listitem_course_timing_time);
        text.setText(timeString);

        ConstraintLayout cancel = (ConstraintLayout) view.findViewById(R.id.memotest_listitem_course_timing_cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Timing toRemove = null;
                for(Timing temp : timeInputs)
                {
                    if(temp.hour == hour && temp.minutes == minutes) toRemove = temp;
                }

                linearContainer.removeView(toRemove.xml);
                timeInputs.remove(toRemove);
                if(timeInputs.size() < 1) makeSubmitButtonClickable(false);
            }
        });
        timing.xml = (ConstraintLayout) view;

        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(200);
        TransitionManager.beginDelayedTransition(linearContainer, autoTransition);
        linearContainer.addView(view, 5);
        makeSubmitButtonClickable(true);

    }

    private ArrayList<Long> initNotificationsData(int dayDuration, int targetHour, int targetMinute)
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
