package com.thebreadiswhite.memotest.db.instance.multi;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thebreadiswhite.memotest.db.instance.firestore.FirestoreInstance;
import com.thebreadiswhite.memotest.db.instance.room.RoomInstance;

// This database holder merge all database instances and functionality.
// This class holds the instances of the server and client side and the messaging server.
public class MultiDatabase
{
    private static MultiDatabase multiInstance;
    private static RoomInstance roomInstance;
    private static FirebaseFirestore firestoreInstance;

    private MultiDatabase(Context context) {
        roomInstance = RoomInstance.getAppDatabase(context);
        firestoreInstance = FirestoreInstance.getDatabase();
    }

    public static MultiDatabase getInstance(@NonNull Context context) {
        if(multiInstance == null) {
            multiInstance = new MultiDatabase(context);
        }
        return multiInstance;
    }

    public RoomInstance getRoomInstance() {
        return roomInstance;
    }

    public FirebaseFirestore getFirestoreInstance()
    {
        return firestoreInstance;
    }
}
