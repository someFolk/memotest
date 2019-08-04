package com.thebreadiswhite.memotest.util;

/** This enum supply the constants of play state
 *
 * {@link #REGULAR} This state means playing by position order.
 * {@link #SHUFFLE} This state means to shuffle cards before play.
 * */

public enum MemotestPlayState
{
    REGULAR(0),
    SHUFFLE(1);

    public int value;

    MemotestPlayState(int value)
    {
        this.value = value;
    }

    // This method gets int
    // and return constant
    public MemotestPlayState getConstantByValue(int value) {
        // Default Value
        MemotestPlayState constant = REGULAR;

        for(MemotestPlayState temp : MemotestPlayState.values()) {
            if(temp.value == value) {
                constant = temp;
                break;
            }
        }
        return constant;
    }
}
