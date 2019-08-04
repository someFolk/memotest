package com.thebreadiswhite.memotest.ui.classefied;

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


}
