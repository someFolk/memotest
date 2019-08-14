package com.thebreadiswhite.memotest.outsystems.search;

import com.thebreadiswhite.memotest.activities.MemotestStackDisplay;

public class SearchConfig
{
    // This enum represent the details every searchable object is
    // required to supply inorder to collaborate with the search system.
    // This include:
    // 1. Class, the activity that should open the search result.
    // 2. String, the Name representing the type of searchable supplied as DB value
    public enum Searchable
    {
        STACK("stack", MemotestStackDisplay.class);

        // TODO: inorder to
        //MEMOTEST("memo" ,MemotestDisplay.class);

        public String tag;
        public Class klass;

        Searchable(String tag, Class klass) {
            this.tag = tag;
            this.klass = klass;
        }
    }
}
