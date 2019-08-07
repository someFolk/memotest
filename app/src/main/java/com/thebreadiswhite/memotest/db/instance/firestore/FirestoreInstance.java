package com.thebreadiswhite.memotest.db.instance.firestore;

import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreInstance
{
    // This will be used when ever the system won't
    // need a firestore backup or server side support.
    public static final String DEFAULT_FIRESTORE_KEY = "0";

    public static FirebaseFirestore getDatabase() {
            return FirebaseFirestore.getInstance();
    }

}