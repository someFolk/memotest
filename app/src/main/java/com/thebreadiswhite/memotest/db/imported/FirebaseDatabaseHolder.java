package com.morsela.tel.db;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.firebase.database.*;
import com.thebreadiswhite.memotest.db.instance.firebase.FirebaseInstance;
import com.morsela.tel.db.servertolocallocaltoserver.LocalUpdateType;
import com.morsela.tel.taskpackage.TaskPackage;

public abstract class FirebaseDatabaseHolder<T extends DatabaseAdapter>
{
    /**
     * A string constant to use in calls to the "log" methods. Its
     * value is often given by the name of the class, as this will
     * allow you to easily determine where log methods are coming
     * from when you analyze your logcat output.
     */
    private static final String TAG = "FirebaseDatabaseHolder";

    private Class<T> aClass;
    private DatabaseHolder<T> holder;

    public FirebaseDatabaseHolder()
    {
    }

    // This method gets called once the DatabaseHolder has been initialized;
    protected void init(DatabaseHolder holder, Class aClass)
    {
        // The child that implements this class
        this.holder = holder;

        // The class that firebase need to call
        // when initializing new objects from the server.
        this.aClass = aClass;
    }

    // Pushing new object onto the table
    public String firebaseInsert(T object)
    {
        // Getting root reference of the specifi table
        DatabaseReference rootRef = FirebaseInstance.getDatabase().getReference(holder.getTable().getName());
        //DatabaseReference rootRef = FirebaseInstance.getDatabase().getReference("installation").child(holder.getTable().getName());


        // Creating new user node, which returns the unique key value
        String key = rootRef.push().getKey();

        // pusing
        rootRef.child(key).setValue(object);

        return key;
        // Returning the String key on FireBase Database
    }

    // Get a single object by key
    public void firebasePullByKeyAndInsertLocally(final String key , final LocalUpdateType type)
    {
        // Get Firebase reference
        final DatabaseReference rootRef = FirebaseInstance.getDatabase().getReference();

        // Get root reference
        final DatabaseReference childRef = rootRef.child(holder.getTable().getName());

        // A Single Value Event. Dismiss itself after retrieving data
        // .child(key)
        Log.d(">>>>>>>> LOOKING", key);
        Log.d(">>>>>>>> LOOKING", key);
        Log.d(">>>>>>>> LOOKING", key);
        Log.d(">>>>>>>> LOOKING", key);
        Log.d(">>>>>>>> LOOKING", key);
        Log.d(">>>>>>>> LOOKING", key);

        @SuppressWarnings("InstanceVariableMayNotBeInitialized")
        final ValueEventListener listener = new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //Log.d(">>>>>>>>>>>>>>>>", Long.toString(dataSnapshot.getChildrenCount()));
                T single = dataSnapshot.getValue(aClass);
                if(single != null)
                {
                    Log.d(">>>>>>>>>>>>>>>>", Long.toString(single.getTimestamp()));
                    single.setFirebaseKey(key);

                    if(type == LocalUpdateType.ADDED)
                    {
                        holder.insertLocally(single);
                    }
                    else if(type == LocalUpdateType.UPDATE)
                    {
                        holder.updateLocally(single);
                    }
                }

                childRef.removeEventListener(this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Log.e(TAG, " Crashed.. " + databaseError.getMessage());
            }
        };

        childRef.child(key).addValueEventListener(listener);
    }

    // Updating specific object on the firebase db
    public void firebaseUpdate(T object)
    {
        // Getting reference to Server
        DatabaseReference rootRef = FirebaseInstance.getDatabase().getReference();

        // Pointing to the specific node
        DatabaseReference childRef = rootRef.child(holder.getTable().getName());

        // Setting the value on the server for specific child
        childRef.child(object.getFirebaseKey()).setValue(object);
    }

    // This method run once for each Application install.
    // It pulls all the data from the server for the specific user.
    public void firebaseGetAllDataOnce(boolean installation)
    {

        // Initializing listener, not shooting it yet.
        final ChildEventListener eventListener = new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                // This is where we receive all the data from the server one-by-one
                T single = dataSnapshot.getValue(aClass);
                String key = dataSnapshot.getKey();
                single.setFirebaseKey(key);
                holder.insertLocally(single);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                // Not important as we need only to load all the data
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
            {
                // Not important as we need only to load all the data
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                // Not important as we need only to load all the data
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Log.e(TAG, databaseError.getMessage());
            }
        };

        // Getting firebase reference
        DatabaseReference rootRef = FirebaseInstance.getDatabase().getReference();

        if(installation)
        {
            rootRef = rootRef.child("installation");
        }
hithere
        // Getting child reference
        final DatabaseReference childRef = rootRef.child(holder.getTable().getName());

        // Start getting data
        childRef.addChildEventListener(eventListener);

        // This shoots when the Child Event Listener is done loading
        // That way we know when to detach the listener
        ValueEventListener valueListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                // Removing the OnChildEventListener
                // which consumes maximum amount of data
                childRef.removeEventListener(eventListener);

                Log.d(TAG, aClass.getName() + " Data has been fully loaded");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Log.e(TAG, databaseError.getMessage());
            }
        };

        // Adding a stopper for ChildEventListener
        childRef.addListenerForSingleValueEvent(valueListener);
    }
}
