package com.thebreadiswhite.memotest.db;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.firebase.database.FirebaseDatabase;
import com.thebreadiswhite.memotest.db.instance.firebase.FirebaseInstance;
import com.thebreadiswhite.memotest.db.instance.room.RoomInstance;

// This database holder merge all database instances and functionality.
// This class holds the instances of the server and client side and the messaging server.
public class MultiDatabase
{
    private static MultiDatabase multiInstance;
    private static RoomInstance roomInstance;
    private static FirebaseDatabase firebaseInstance;

    public static MultiDatabase getInstance(@NonNull Context context)
    {
        if(multiInstance == null){
            roomInstance = RoomInstance.getAppDatabase(context);
            firebaseInstance = FirebaseInstance.getDatabase();
        }
        return multiInstance;
    }
}
