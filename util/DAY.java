package com.thebreadiswhite.memotest.util;

public enum DAY
{
    SUNDAY(1, "Sunday"),
    MONDAY(2, "Monday"),
    TUESDAY(3, "Tuesday"),
    WENDSDAY(4, "Wendsday"),
    THURSDAY(5, "Thursday"),
    FRIDAY(6, "Friday"),
    SATURDAY(7, "Saturday");

    public int value;
    public String name;

    DAY(int value, String name)
    {
        this.value = value;
        this.name = name;
    }

    public DAY getDayByValue(int value)
    {
        for(DAY temp : DAY.values()) {
            if(temp.value == value) return temp;
        }
        return SUNDAY;
    }

}
