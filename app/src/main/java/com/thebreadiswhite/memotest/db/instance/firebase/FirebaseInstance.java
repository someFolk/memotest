package com.thebreadiswhite.memotest.db.instance.firebase;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseInstance
{
    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;

    }

}
