package com.thebreadiswhite.memotest.activities.classefied;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import androidx.annotation.NonNull;

public abstract class Ui
{

    public enum Visibility{SHOWING,NOT_SHOWING}

    // This is the tag that is going to represent
    // the state that is currently showing
    public final String TAG;

    public Ui(@NonNull String tag)
    {
        TAG = tag;
    }

    // Here goes everything that is having a connection with the UI
    // methods to find a view width and height, etc


    public static boolean canScroll(HorizontalScrollView horizontalScrollView) {
        View child = (View) horizontalScrollView.getChildAt(0);
        if (child != null) {
            int childWidth = (child).getWidth();
            return horizontalScrollView.getWidth() < childWidth + horizontalScrollView.getPaddingLeft() + horizontalScrollView.getPaddingRight();
        }
        return false;

    }

    public static boolean isPortrait(Context context)
    {
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean canScroll(ScrollView scrollView) {
        View child = (View) scrollView.getChildAt(0);
        if (child != null) {
            int childHeight = (child).getHeight();
            return scrollView.getHeight() < childHeight + scrollView.getPaddingTop() + scrollView.getPaddingBottom();
        }
        return false;
    }
}
