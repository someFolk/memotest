package com.thebreadiswhite.memotest.util;


import com.thebreadiswhite.memotest.R;

// This class responsible to hold all
// the general constants for the memotest system.
public class MemotestConfig
{
    // This is the EXTRA_ID which we save
    // and retrieve the id of the stack to display;
    public static final String MEMOTEST_EXTRA_ID_STACK_DISPLAY_CONST = "stack_id";

    // The default color of the text on a memo
    // This helps us determine if a color has been chose for a text color.
    public static final int MEMOTEST_DEFAULT_MEMO_TEXT_COLOR = R.color.blackText;

    // The default color of the background on a memo
    // This helps us determine if a color has been chose for a background color.
    public static final int MEMOTEST_DEFAULT_MEMO_BACKGROUND_COLOR = R.color.primaryGreyBackgroundForWhite;

    // This is the default value of the description of a stack on the database.
    // When pulling the data from the database it helps us determine if to display
    // an Add Description button or to show the description itself.
    public static final String MEMOTEST_DESCRIPTION_DATABASE_DEFAULT_VALUE = "0";


}
